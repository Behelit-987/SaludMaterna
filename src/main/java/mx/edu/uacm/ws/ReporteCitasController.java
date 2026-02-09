package mx.edu.uacm.ws;

import mx.edu.uacm.ws.Entity.ConsultaPrenatal;
import mx.edu.uacm.ws.Entity.DatosConsultaPrenatal;
import mx.edu.uacm.ws.Entity.LastMestruacion;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Repository.ConsultaPrenatalRepository;
import mx.edu.uacm.ws.Repository.DatosConsultaPrenatalRepository;
import mx.edu.uacm.ws.Repository.LastMestruacionRepository;
import mx.edu.uacm.ws.Service.UsuariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reporte-citas")
//@CrossOrigin(origins = "*")
public class ReporteCitasController {

    @Autowired
    private UsuariaService usuariaService;
    
    @Autowired
    private ConsultaPrenatalRepository consultaPrenatalRepository;
    
    @Autowired
    private DatosConsultaPrenatalRepository datosConsultaRepository;
    
    @Autowired
    private LastMestruacionRepository lastMestruacionRepository;
    
    private Usuaria obtenerUsuariaAutenticada() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuariaService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    @GetMapping("/consultas")
    public ResponseEntity<?> getConsultasConEstado() {
        try {
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            System.out.println("=== OBTENIENDO CONSULTAS PARA USUARIO: " + idUser + " ===");
            
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            
            if (!lastMestOpt.isPresent()) {
                System.out.println("No hay última menstruación registrada");
                return ResponseEntity.ok().body(Map.of(
                    "message", "No hay citas calculadas. Calcula tus citas en la sección correspondiente.",
                    "citas", new ArrayList<>(),
                    "tieneCitas", false
                ));
            }
            
            LastMestruacion lastMest = lastMestOpt.get();
            LocalDate fechaMestruacion = lastMest.getUltMest();
            Long idLastUsuario = lastMest.getId_last();
            System.out.println("idLast del usuario: " + idLastUsuario);
            
            List<ConsultaPrenatal> todasConsultas = consultaPrenatalRepository.findByUserId(idUser);
            
            String[] rangosPrenatales = {"0-8", "10-14", "16-18", "22", "28", "32", "36", "38-41"};
            
            List<ConsultaPrenatal> consultasConfirmadas = new ArrayList<>();
            
            for (String rango : rangosPrenatales) {
                LocalDate inicioRango = calcularInicioRangoPrenatal(fechaMestruacion, rango);
                LocalDate finRango = calcularFinRangoPrenatal(fechaMestruacion, rango);
                
                List<ConsultaPrenatal> fechasEnRango = todasConsultas.stream()
                    .filter(c -> {
                        LocalDate fecha = c.getFechaConsulta();
                        return fecha != null && 
                               !fecha.isBefore(inicioRango) && 
                               !fecha.isAfter(finRango);
                    })
                    .collect(Collectors.toList());
                
                if (fechasEnRango.size() == 1) {
                    consultasConfirmadas.add(fechasEnRango.get(0));
                }
            }
            
            System.out.println("Consultas confirmadas encontradas: " + consultasConfirmadas.size());
            
            if (consultasConfirmadas.isEmpty()) {
                System.out.println("No hay consultas confirmadas");
                return ResponseEntity.ok().body(Map.of(
                    "message", "No tienes citas confirmadas. Por favor, confirma tus citas en el calendario.",
                    "citas", new ArrayList<>(),
                    "tieneCitas", false
                ));
            }
            
            List<DatosConsultaPrenatal> datosRegistrados = datosConsultaRepository
                    .findByIdLastId(idLastUsuario);
            
            System.out.println("Datos registrados encontrados: " + datosRegistrados.size());
            
            List<Map<String, Object>> citasConEstado = new ArrayList<>();
            
            for (ConsultaPrenatal consulta : consultasConfirmadas) {
                Map<String, Object> citaMap = new HashMap<>();
                citaMap.put("idPrenatal", consulta.getIdPrenatal());
                citaMap.put("fechaConsulta", consulta.getFechaConsulta());
                citaMap.put("idLast", consulta.getIdLast());
                
                boolean completada = false;
                Map<String, Object> datosMap = new HashMap<>();
                
                for (DatosConsultaPrenatal datos : datosRegistrados) {
                    if (datos.getIdPrenatal() != null && 
                        datos.getIdPrenatal().equals(consulta.getIdPrenatal())) {
                        completada = true;
                        datosMap.put("peso", datos.getPeso());
                        datosMap.put("imc", datos.getImc());
                        datosMap.put("presionArterial", datos.getPresionArterial());
                        datosMap.put("fondoUterino", datos.getFondoUterino());
                        datosMap.put("frecuenciaFetal", datos.getFrecuenciaFetal());
                        datosMap.put("medicamentos", datos.getMedicamentos());
                        datosMap.put("datosGenerales", datos.getDatosGenerales());
                        datosMap.put("fechaRegistro", datos.getFechaRegistro());
                        break;
                    }
                }
                
                citaMap.put("completada", completada);
                citaMap.put("datosConsulta", datosMap);
                
                calcularRangoSemanas(consulta, lastMest, citaMap);
                
                citasConEstado.add(citaMap);
            }
            
            citasConEstado.sort((c1, c2) -> {
                LocalDate fecha1 = (LocalDate) c1.get("fechaConsulta");
                LocalDate fecha2 = (LocalDate) c2.get("fechaConsulta");
                return fecha1.compareTo(fecha2);
            });
            
            long completadas = citasConEstado.stream().filter(c -> (boolean) c.get("completada")).count();
            long pendientes = citasConEstado.size() - completadas;
            
            List<Map<String, Object>> citasFaltantes = obtenerCitasFaltantes(idUser, fechaMestruacion);
            
            System.out.println("=== RESULTADO: " + citasConEstado.size() + " citas confirmadas, " + 
                              completadas + " completadas, " + pendientes + " pendientes ===");
            System.out.println("Citas faltantes: " + citasFaltantes.size());
            
            return ResponseEntity.ok(Map.of(
                "citas", citasConEstado,
                "citasFaltantes", citasFaltantes, 
                "total", citasConEstado.size(),
                "completadas", completadas,
                "pendientes", pendientes,
                "tieneCitas", true
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR en getConsultasConEstado: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al obtener consultas: " + e.getMessage())
            );
        }
    }

    private List<Map<String, Object>> obtenerCitasFaltantes(Long idUser, LocalDate fechaMestruacion) {
        List<Map<String, Object>> citasFaltantes = new ArrayList<>();
        
        try {
            List<ConsultaPrenatal> todasConsultas = consultaPrenatalRepository.findByUserId(idUser);
            
            String[] rangosPrenatales = {"0-8", "10-14", "16-18", "22", "28", "32", "36", "38-41"};
            
            for (String rango : rangosPrenatales) {
                LocalDate inicioRango = calcularInicioRangoPrenatal(fechaMestruacion, rango);
                LocalDate finRango = calcularFinRangoPrenatal(fechaMestruacion, rango);
                
                List<ConsultaPrenatal> fechasEnRango = todasConsultas.stream()
                    .filter(c -> {
                        LocalDate fecha = c.getFechaConsulta();
                        return fecha != null && 
                               !fecha.isBefore(inicioRango) && 
                               !fecha.isAfter(finRango);
                    })
                    .collect(Collectors.toList());
                
                if (fechasEnRango.size() != 1) {
                    Map<String, Object> citaFaltante = new HashMap<>();
                    citaFaltante.put("rango", rango);
                    citaFaltante.put("nombre", getNombreConsultaPrenatal(rango));
                    citaFaltante.put("inicioRango", inicioRango);
                    citaFaltante.put("finRango", finRango);
                    citaFaltante.put("estado", "Por confirmar");
                    
                    citaFaltante.put("periodo", 
                        inicioRango.getDayOfMonth() + "/" + inicioRango.getMonthValue() + " - " +
                        finRango.getDayOfMonth() + "/" + finRango.getMonthValue());
                    
                    citasFaltantes.add(citaFaltante);
                }
            }
            
            citasFaltantes.sort((c1, c2) -> {
                LocalDate fecha1 = (LocalDate) c1.get("inicioRango");
                LocalDate fecha2 = (LocalDate) c2.get("inicioRango");
                return fecha1.compareTo(fecha2);
            });
            
        } catch (Exception e) {
            System.err.println("Error al obtener citas faltantes: " + e.getMessage());
        }
        
        return citasFaltantes;
    }

    private LocalDate calcularInicioRangoPrenatal(LocalDate fechaMestruacion, String rango) {
        switch(rango) {
            case "0-8": return fechaMestruacion;
            case "10-14": return fechaMestruacion.plusWeeks(10);
            case "16-18": return fechaMestruacion.plusWeeks(16);
            case "22": return fechaMestruacion.plusWeeks(22);
            case "28": return fechaMestruacion.plusWeeks(28);
            case "32": return fechaMestruacion.plusWeeks(32);
            case "36": return fechaMestruacion.plusWeeks(36);
            case "38-41": return fechaMestruacion.plusWeeks(38);
            default: return fechaMestruacion;
        }
    }

    private LocalDate calcularFinRangoPrenatal(LocalDate fechaMestruacion, String rango) {
        switch(rango) {
            case "0-8": return fechaMestruacion.plusWeeks(8);
            case "10-14": return fechaMestruacion.plusWeeks(14);
            case "16-18": return fechaMestruacion.plusWeeks(18);
            case "22": return fechaMestruacion.plusWeeks(23);
            case "28": return fechaMestruacion.plusWeeks(29);
            case "32": return fechaMestruacion.plusWeeks(33);
            case "36": return fechaMestruacion.plusWeeks(37);
            case "38-41": return fechaMestruacion.plusWeeks(41);
            default: return fechaMestruacion;
        }
    }

    private String getNombreConsultaPrenatal(String rango) {
        switch(rango) {
            case "0-8": return "Primera cita prenatal";
            case "10-14": return "Segunda cita prenatal";
            case "16-18": return "Tercera cita prenatal";
            case "22": return "Cuarta cita prenatal";
            case "28": return "Quinta cita prenatal";
            case "32": return "Sexta cita prenatal";
            case "36": return "Séptima cita prenatal";
            case "38-41": return "Octava cita prenatal";
            default: return "Consulta prenatal";
        }
    }
    /*
    @GetMapping("/consultas")
    public ResponseEntity<?> getConsultasConEstado() {
        try {
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            System.out.println("=== OBTENIENDO CONSULTAS PARA USUARIO: " + idUser + " ===");
            
            // 1. Obtener última menstruación del usuario
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            
            if (!lastMestOpt.isPresent()) {
                System.out.println("No hay última menstruación registrada");
                return ResponseEntity.ok().body(Map.of(
                    "message", "No hay citas calculadas. Calcula tus citas en la sección correspondiente.",
                    "citas", new ArrayList<>(),
                    "tieneCitas", false
                ));
            }
            
            LastMestruacion lastMest = lastMestOpt.get();
            Long idLastUsuario = lastMest.getId_last();
            System.out.println("idLast del usuario: " + idLastUsuario);
            
            // 2. Obtener consultas del usuario
            List<ConsultaPrenatal> consultas = consultaPrenatalRepository.findByUserId(idUser);
            
            System.out.println("Consultas encontradas: " + consultas.size());
            
            if (consultas.isEmpty()) {
                System.out.println("No hay consultas programadas");
                return ResponseEntity.ok().body(Map.of(
                    "message", "No tienes citas programadas. Calcula tus citas prenatales.",
                    "citas", new ArrayList<>(),
                    "tieneCitas", false
                ));
            }
            
            // 3. Obtener datos de consulta por idLast (USANDO EL MÉTODO CORRECTO)
            List<DatosConsultaPrenatal> datosRegistrados = datosConsultaRepository
                    .findByIdLastId(idLastUsuario);
            
            System.out.println("Datos registrados encontrados: " + datosRegistrados.size());
            
            // 4. Mapear consultas con su estado
            List<Map<String, Object>> citasConEstado = new ArrayList<>();
            
            for (ConsultaPrenatal consulta : consultas) {
                Map<String, Object> citaMap = new HashMap<>();
                citaMap.put("idPrenatal", consulta.getIdPrenatal());
                citaMap.put("fechaConsulta", consulta.getFechaConsulta());
                citaMap.put("idLast", consulta.getIdLast());
                
                // Verificar si esta consulta tiene datos registrados
                boolean completada = false;
                Map<String, Object> datosMap = new HashMap<>();
                
                for (DatosConsultaPrenatal datos : datosRegistrados) {
                    if (datos.getIdPrenatal() != null && 
                        datos.getIdPrenatal().equals(consulta.getIdPrenatal())) {
                        completada = true;
                        datosMap.put("peso", datos.getPeso());
                        datosMap.put("imc", datos.getImc());
                        datosMap.put("presionArterial", datos.getPresionArterial());
                        datosMap.put("fondoUterino", datos.getFondoUterino());
                        datosMap.put("frecuenciaFetal", datos.getFrecuenciaFetal());
                        datosMap.put("medicamentos", datos.getMedicamentos());
                        datosMap.put("datosGenerales", datos.getDatosGenerales());
                        datosMap.put("fechaRegistro", datos.getFechaRegistro());
                        break;
                    }
                }
                
                citaMap.put("completada", completada);
                citaMap.put("datosConsulta", datosMap);
                
                // Calcular rango de semanas basado en la fecha de última menstruación
                calcularRangoSemanas(consulta, lastMest, citaMap);
                
                citasConEstado.add(citaMap);
            }
            
            // Ordenar por fecha
            citasConEstado.sort((c1, c2) -> {
                LocalDate fecha1 = (LocalDate) c1.get("fechaConsulta");
                LocalDate fecha2 = (LocalDate) c2.get("fechaConsulta");
                return fecha1.compareTo(fecha2);
            });
            
            long completadas = citasConEstado.stream().filter(c -> (boolean) c.get("completada")).count();
            long pendientes = citasConEstado.size() - completadas;
            
            System.out.println("=== RESULTADO: " + citasConEstado.size() + " citas, " + 
                              completadas + " completadas, " + pendientes + " pendientes ===");
            
            return ResponseEntity.ok(Map.of(
                "citas", citasConEstado,
                "total", citasConEstado.size(),
                "completadas", completadas,
                "pendientes", pendientes,
                "tieneCitas", true
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR en getConsultasConEstado: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al obtener consultas: " + e.getMessage())
            );
        }
    }
    */
    
    /*
    @PostMapping("/registrar-datos")
    public ResponseEntity<?> registrarDatosConsulta(@RequestBody RegistrarDatosRequest request) {
        try {
            System.out.println("=== REGISTRANDO DATOS ===");
            
            // 1. Usuario
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            // 2. Última menstruación
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            if (!lastMestOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Sin última menstruación"));
            }
            
            Long idLast = lastMestOpt.get().getId_last();
            
            // 3. Buscar consulta USANDO EL MÉTODO QUE SÍ FUNCIONA
            // En lugar de findById(), usa findByUserId() y busca en la lista
            List<ConsultaPrenatal> consultasUsuario = consultaPrenatalRepository.findByUserId(idUser);
            
            Optional<ConsultaPrenatal> consultaOpt = consultasUsuario.stream()
                .filter(c -> c.getIdPrenatal().equals(request.idPrenatal()))
                .findFirst();
                
            if (!consultaOpt.isPresent()) {
                System.err.println("Consulta " + request.idPrenatal() + " no encontrada para usuario " + idUser);
                return ResponseEntity.badRequest().body(Map.of("error", "Consulta no encontrada"));
            }
            
            ConsultaPrenatal consulta = consultaOpt.get();
            
            // 4. Ya sabemos que pertenece al usuario (porque la filtramos de sus consultas)
            
            // 5. Crear datos
            DatosConsultaPrenatal datos = new DatosConsultaPrenatal();
            datos.setIdLast(lastMestOpt.get());
            datos.setIdPrenatal(consulta);
            
            // Asignar datos
            datos.setPeso(request.peso());
            datos.setImc(request.imc());
            datos.setPresionArterial(request.presionArterial());
            datos.setFondoUterino(request.fondoUterino());
            datos.setFrecuenciaFetal(request.frecuenciaFetal());
            datos.setMedicamentos(request.medicamentos());
            datos.setDatosGenerales(request.datosGenerales());
            datos.setFechaRegistro(LocalDate.now());
            
            // 6. Guardar
            datosConsultaRepository.save(datos);
            
            return ResponseEntity.ok(Map.of("success", true, "message", "Datos guardados"));
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    */
    
    @PostMapping("/registrar-datos")
    public ResponseEntity<?> registrarDatosConsulta(@RequestBody RegistrarDatosRequest request) {
        try {
            System.out.println("=== REGISTRANDO DATOS ===");
            
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            if (request.peso() < 20 || request.peso() > 200) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Peso inválido. Debe estar entre 20 y 200 kg."
                ));
            }
            
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            if (!lastMestOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Sin última menstruación"));
            }
            
            Long idLast = lastMestOpt.get().getId_last();
            
            List<ConsultaPrenatal> consultasUsuario = consultaPrenatalRepository.findByUserId(idUser);
            
            Optional<ConsultaPrenatal> consultaOpt = consultasUsuario.stream()
                .filter(c -> c.getIdPrenatal().equals(request.idPrenatal()))
                .findFirst();
                
            if (!consultaOpt.isPresent()) {
                System.err.println("Consulta " + request.idPrenatal() + " no encontrada para usuario " + idUser);
                return ResponseEntity.badRequest().body(Map.of("error", "Consulta no encontrada"));
            }
            
            ConsultaPrenatal consulta = consultaOpt.get();
            
            Double talla = usuaria.getTalla();
            if (talla == null || talla <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "No tienes talla registrada. Actualiza tu perfil primero."
                ));
            }
            
            if (talla < 0.40 || talla > 2.50) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Talla registrada inválida. Contacta al administrador."
                ));
            }
            
            Double imcCalculado = request.peso() / (talla * talla);
            Double imcRedondeado = Math.round(imcCalculado * 100.0) / 100.0;
            
            String clasificacionIMC = determinarClasificacionIMC(imcCalculado);
            
            System.out.println("IMC calculado automáticamente:");
            System.out.println("  Peso: " + request.peso() + " kg");
            System.out.println("  Talla: " + talla + " m");
            System.out.println("  IMC: " + imcRedondeado);
            System.out.println("  Clasificación: " + clasificacionIMC);
            
            DatosConsultaPrenatal datos = new DatosConsultaPrenatal();
            datos.setIdLast(lastMestOpt.get());
            datos.setIdPrenatal(consulta);
            
            datos.setPeso(request.peso());
            datos.setImc(imcRedondeado); // ¡Se asigna automáticamente!
            datos.setPresionArterial(request.presionArterial());
            datos.setFondoUterino(request.fondoUterino());
            datos.setFrecuenciaFetal(request.frecuenciaFetal());
            datos.setMedicamentos(request.medicamentos());
            
            String datosGeneralesConIMC = (request.datosGenerales() != null && !request.datosGenerales().isEmpty()) 
                ? request.datosGenerales() + " | IMC: " + imcRedondeado + " (" + clasificacionIMC + ")"
                : "IMC: " + imcRedondeado + " (" + clasificacionIMC + ")";
            
            datos.setDatosGenerales(datosGeneralesConIMC);
            datos.setFechaRegistro(LocalDate.now());
            
            datosConsultaRepository.save(datos);
            
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Datos guardados exitosamente",
                "imc_calculado", imcRedondeado,
                "clasificacion_imc", clasificacionIMC,
                "datos_registrados", Map.of(
                    "id_dc", datos.getIdDC(),
                    "peso", request.peso(),
                    "imc", imcRedondeado,
                    "clasificacion", clasificacionIMC
                )
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @GetMapping("/detalles/{idPrenatal}")
    public ResponseEntity<?> getDetallesConsulta(@PathVariable Long idPrenatal) {
        try {
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            System.out.println(" OBTENIENDO DETALLES PARA CONSULTA: " + idPrenatal);
            System.out.println(" Usuario: " + idUser);
            
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            if (!lastMestOpt.isPresent()) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", "No tienes información de embarazo registrada")
                );
            }
            
            Long idLastUsuario = lastMestOpt.get().getId_last();
            
            Optional<ConsultaPrenatal> consultaOpt = consultaPrenatalRepository.findById(idPrenatal);
            if (!consultaOpt.isPresent()) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", "Consulta no encontrada")
                );
            }
            
            ConsultaPrenatal consulta = consultaOpt.get();
            
            if (!consulta.getIdLast().equals(idLastUsuario)) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", "No tienes permiso para ver esta consulta")
                );
            }
            
            Optional<DatosConsultaPrenatal> datosOpt = datosConsultaRepository
                    .findByIdPrenatalId(idPrenatal);
            
            if (!datosOpt.isPresent()) {
                return ResponseEntity.ok().body(Map.of(
                    "message", "No hay datos registrados para esta consulta",
                    "datosConsulta", new HashMap<>(),
                    "consulta", Map.of(
                        "fechaConsulta", consulta.getFechaConsulta(),
                        "idPrenatal", consulta.getIdPrenatal(),
                        "idLast", consulta.getIdLast()
                    )
                ));
            }
            
            DatosConsultaPrenatal datos = datosOpt.get();
            Map<String, Object> datosMap = new HashMap<>();
            datosMap.put("peso", datos.getPeso());
            datosMap.put("imc", datos.getImc());
            datosMap.put("presionArterial", datos.getPresionArterial());
            datosMap.put("fondoUterino", datos.getFondoUterino());
            datosMap.put("frecuenciaFetal", datos.getFrecuenciaFetal());
            datosMap.put("medicamentos", datos.getMedicamentos());
            datosMap.put("datosGenerales", datos.getDatosGenerales());
            datosMap.put("fechaRegistro", datos.getFechaRegistro());
            
            return ResponseEntity.ok(Map.of(
                "consulta", Map.of(
                    "fechaConsulta", consulta.getFechaConsulta(),
                    "idPrenatal", consulta.getIdPrenatal(),
                    "idLast", consulta.getIdLast()
                ),
                "datosConsulta", datosMap
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR en getDetallesConsulta: " + e.getMessage());
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al obtener detalles: " + e.getMessage())
            );
        }
    }
    
    
    @GetMapping("/resumen")
    public ResponseEntity<?> getResumenCitas() {
        try {
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            System.out.println(" OBTENIENDO RESUMEN PARA USUARIO: " + idUser);
            
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            
            if (!lastMestOpt.isPresent()) {
                return ResponseEntity.ok().body(Map.of(
                    "mensaje", "No hay información de embarazo registrada",
                    "tieneCitas", false
                ));
            }
            
            LastMestruacion lastMest = lastMestOpt.get();
            LocalDate fechaMestruacion = lastMest.getUltMest();
            Long idLastUsuario = lastMest.getId_last();
            
            List<ConsultaPrenatal> todasConsultas = consultaPrenatalRepository.findByUserId(idUser);
            
            List<ConsultaPrenatal> consultasConfirmadas = filtrarConsultasConfirmadas(todasConsultas, fechaMestruacion);
            
            if (consultasConfirmadas.isEmpty()) {
                return ResponseEntity.ok().body(Map.of(
                    "mensaje", "No tienes citas confirmadas. Por favor, confirma tus citas en el calendario.",
                    "tieneCitas", false
                ));
            }
            
            List<DatosConsultaPrenatal> datosRegistrados = datosConsultaRepository
                    .findByIdLastId(idLastUsuario);
            
            long citasCompletadas = 0;
            for (ConsultaPrenatal consulta : consultasConfirmadas) {
                boolean tieneDatos = datosRegistrados.stream()
                    .anyMatch(d -> {
                        if (d.getIdPrenatal() == null) return false;
                        return d.getIdPrenatal().equals(consulta.getIdPrenatal());
                    });
                
                if (tieneDatos) {
                    citasCompletadas++;
                }
            }
            
            long totalCitas = consultasConfirmadas.size();
            long citasPendientes = totalCitas - citasCompletadas;
            
            double porcentajeCompletado = totalCitas > 0 ? 
                (double) citasCompletadas / totalCitas * 100 : 0;
            
            Optional<ConsultaPrenatal> proximaCitaOpt = consultasConfirmadas.stream()
                .filter(c -> {
                    return datosRegistrados.stream()
                        .noneMatch(d -> {
                            if (d.getIdPrenatal() == null) return true;
                            return d.getIdPrenatal().equals(c.getIdPrenatal());
                        });
                })
                .filter(c -> c.getFechaConsulta() != null)
                .min(Comparator.comparing(ConsultaPrenatal::getFechaConsulta));
            
            Map<String, Object> resumen = new HashMap<>();
            resumen.put("totalCitas", totalCitas);
            resumen.put("citasCompletadas", citasCompletadas);
            resumen.put("citasPendientes", citasPendientes);
            resumen.put("porcentajeCompletado", Math.round(porcentajeCompletado));
            resumen.put("tieneCitas", true);
            
            if (proximaCitaOpt.isPresent()) {
                ConsultaPrenatal proximaCita = proximaCitaOpt.get();
                resumen.put("proximaCita", Map.of(
                    "fecha", proximaCita.getFechaConsulta(),
                    "idPrenatal", proximaCita.getIdPrenatal(),
                    "fechaFormateada", proximaCita.getFechaConsulta().toString()
                ));
            }
            
            return ResponseEntity.ok(resumen);
            
        } catch (Exception e) {
            System.err.println("ERROR en getResumenCitas: " + e.getMessage());
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al obtener resumen: " + e.getMessage())
            );
        }
    }

    private List<ConsultaPrenatal> filtrarConsultasConfirmadas(List<ConsultaPrenatal> todasConsultas, LocalDate fechaMestruacion) {
        List<ConsultaPrenatal> confirmadas = new ArrayList<>();
        
        String[] rangosPrenatales = {"0-8", "10-14", "16-18", "22", "28", "32", "36", "38-41"};
        
        for (String rango : rangosPrenatales) {
            LocalDate inicioRango = calcularInicioRangoPrenatal(fechaMestruacion, rango);
            LocalDate finRango = calcularFinRangoPrenatal(fechaMestruacion, rango);
            
            List<ConsultaPrenatal> fechasEnRango = todasConsultas.stream()
                .filter(c -> {
                    LocalDate fecha = c.getFechaConsulta();
                    return fecha != null && 
                           !fecha.isBefore(inicioRango) && 
                           !fecha.isAfter(finRango);
                })
                .collect(Collectors.toList());
            
            if (fechasEnRango.size() == 1) {
                confirmadas.add(fechasEnRango.get(0));
            }
        }
        
        return confirmadas;
    }
    
    @DeleteMapping("/eliminar-datos/{idPrenatal}")
    public ResponseEntity<?> eliminarDatosConsulta(@PathVariable Long idPrenatal) {
        try {
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            System.out.println("SOLICITUD PARA ELIMINAR DATOS - Consulta: " + idPrenatal);
            
            Optional<DatosConsultaPrenatal> datosOpt = datosConsultaRepository
                    .findByIdPrenatalId(idPrenatal);
            
            if (!datosOpt.isPresent()) {
                return ResponseEntity.badRequest().body(
                    Map.of("status", "error", "message", "No hay datos para eliminar")
                );
            }
            
            DatosConsultaPrenatal datos = datosOpt.get();
            
            if (!datos.getIdLast().equals(idUser)) {
                return ResponseEntity.badRequest().body(
                    Map.of("status", "error", "message", "No tienes permiso para eliminar estos datos")
                );
            }
            
            datosConsultaRepository.delete(datos);
            
            System.out.println("✅ Datos eliminados exitosamente - ID Datos: " + datos.getIdDC());
            
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Datos eliminados exitosamente"
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR en eliminarDatosConsulta: " + e.getMessage());
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al eliminar datos: " + e.getMessage())
            );
        }
    }
        
    private void calcularRangoSemanas(ConsultaPrenatal consulta, LastMestruacion lastMest, Map<String, Object> citaMap) {
        try {
            LocalDate fechaMestruacion = lastMest.getUltMest();
            LocalDate fechaConsulta = consulta.getFechaConsulta();
            
            if (fechaMestruacion == null || fechaConsulta == null) {
                citaMap.put("rangoSemanas", "N/A");
                citaMap.put("rangoInicio", null);
                citaMap.put("rangoFin", null);
                return;
            }
            
            long semanas = java.time.temporal.ChronoUnit.WEEKS.between(
                fechaMestruacion, fechaConsulta
            );
            
            String rangoSemanas = determinarRangoSemanas(semanas);
            citaMap.put("rangoSemanas", rangoSemanas);
            
            Map<String, LocalDate> rangos = calcularRangoFechas(fechaMestruacion, rangoSemanas);
            citaMap.put("rangoInicio", rangos.get("inicio"));
            citaMap.put("rangoFin", rangos.get("fin"));
            
        } catch (Exception e) {
            citaMap.put("rangoSemanas", "N/A");
            citaMap.put("rangoInicio", null);
            citaMap.put("rangoFin", null);
        }
    }
    
    private String determinarRangoSemanas(long semanas) {
        if (semanas <= 8) return "0-8";
        if (semanas >= 10 && semanas <= 14) return "10-14";
        if (semanas >= 16 && semanas <= 18) return "16-18";
        if (semanas >= 21 && semanas <= 23) return "22";
        if (semanas >= 27 && semanas <= 29) return "28";
        if (semanas >= 31 && semanas <= 33) return "32";
        if (semanas >= 35 && semanas <= 37) return "36";
        if (semanas >= 38 && semanas <= 41) return "38-41";
        return "N/A";
    }
    
    private Map<String, LocalDate> calcularRangoFechas(LocalDate fechaMestruacion, String rangoSemanas) {
        Map<String, LocalDate> rangos = new HashMap<>();
        
        try {
            switch (rangoSemanas) {
                case "0-8":
                    rangos.put("inicio", fechaMestruacion);
                    rangos.put("fin", fechaMestruacion.plusWeeks(8));
                    break;
                case "10-14":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(10));
                    rangos.put("fin", fechaMestruacion.plusWeeks(14));
                    break;
                case "16-18":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(16));
                    rangos.put("fin", fechaMestruacion.plusWeeks(18));
                    break;
                case "22":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(21));
                    rangos.put("fin", fechaMestruacion.plusWeeks(23));
                    break;
                case "28":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(27));
                    rangos.put("fin", fechaMestruacion.plusWeeks(29));
                    break;
                case "32":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(31));
                    rangos.put("fin", fechaMestruacion.plusWeeks(33));
                    break;
                case "36":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(35));
                    rangos.put("fin", fechaMestruacion.plusWeeks(37));
                    break;
                case "38-41":
                    rangos.put("inicio", fechaMestruacion.plusWeeks(38));
                    rangos.put("fin", fechaMestruacion.plusWeeks(41));
                    break;
                default:
                    rangos.put("inicio", null);
                    rangos.put("fin", null);
            }
        } catch (Exception e) {
            rangos.put("inicio", null);
            rangos.put("fin", null);
        }
        
        return rangos;
    }
    
    @PostMapping("/actualizar-imc")
    public ResponseEntity<?> actualizarIMC(@RequestBody ActualizarIMCRequest request) {
        try {
            Usuaria usuaria = obtenerUsuariaAutenticada();
            
            if (request.pesoActual() < 20 || request.pesoActual() > 200) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Peso inválido. Debe estar entre 20 y 200 kg."
                ));
            }
            
            Optional<DatosConsultaPrenatal> datosOpt = datosConsultaRepository.findById(request.idDc());
            if (!datosOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Registro de consulta no encontrado"
                ));
            }
            
            DatosConsultaPrenatal datos = datosOpt.get();
            
            Double talla = usuaria.getTalla();
            if (talla == null || talla <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "No tienes talla registrada. Actualiza tu perfil primero."
                ));
            }
            
            Double imc = request.pesoActual() / (talla * talla);
            Double imcRedondeado = Math.round(imc * 100.0) / 100.0;
            
            datos.setPeso(request.pesoActual());
            datos.setImc(imcRedondeado);
            datos.setFechaRegistro(LocalDate.now());
            
            datosConsultaRepository.save(datos);
            
            String clasificacion = determinarClasificacionIMC(imc);
            String colorClase = determinarColorClasificacion(clasificacion);
            
            return ResponseEntity.ok(Map.of(
                "success", true,
                "mensaje", "IMC calculado y actualizado exitosamente",
                "datos_actualizados", Map.of(
                    "id_dc", datos.getIdDC(),
                    "peso_actual", request.pesoActual(),
                    "imc_calculado", imcRedondeado,
                    "altura_usada", talla,
                    "clasificacion", clasificacion,
                    "color_clase", colorClase
                )
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR en actualizarIMC: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of(
                "error", "Error al calcular IMC: " + e.getMessage()
            ));
        }
    }
    

    @PutMapping("/actualizar-datos/{idPrenatal}")
    public ResponseEntity<?> actualizarDatosConsulta(
            @PathVariable Long idPrenatal,
            @RequestBody ActualizarDatosRequest request) {
        try {
            System.out.println("=== ACTUALIZANDO DATOS PARA CONSULTA: " + idPrenatal + " ===");
            
            Usuaria usuaria = obtenerUsuariaAutenticada();
            Long idUser = usuaria.getIdUser();
            
            if (request.peso() < 20 || request.peso() > 200) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "Peso inválido. Debe estar entre 20 y 200 kg."
                ));
            }
            
            Optional<DatosConsultaPrenatal> datosOpt = datosConsultaRepository.findByIdPrenatalId(idPrenatal);
            
            if (!datosOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "No se encontraron datos para esta consulta. Primero debes registrar los datos."
                ));
            }
            
            DatosConsultaPrenatal datos = datosOpt.get();
            
            Optional<LastMestruacion> lastMestOpt = lastMestruacionRepository.findByUserId(idUser);
            
            if (!lastMestOpt.isPresent()) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "No tienes información de embarazo registrada."
                ));
            }
            
            Long idLastUsuario = lastMestOpt.get().getId_last();
            
            if (!datos.getIdLast().equals(idLastUsuario)) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "No tienes permiso para actualizar estos datos."
                ));
            }
            
            Double talla = usuaria.getTalla();
            if (talla == null || talla <= 0) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "No tienes talla registrada. Actualiza tu perfil primero."
                ));
            }
            
            Double imcCalculado = request.peso() / (talla * talla);
            Double imcRedondeado = Math.round(imcCalculado * 100.0) / 100.0;
            
            String clasificacionIMC = determinarClasificacionIMC(imcCalculado);
            
            System.out.println("IMC actualizado automáticamente:");
            System.out.println("  Peso: " + request.peso() + " kg");
            System.out.println("  Talla: " + talla + " m");
            System.out.println("  IMC: " + imcRedondeado);
            System.out.println("  Clasificación: " + clasificacionIMC);
            
            datos.setPeso(request.peso());
            datos.setImc(imcRedondeado);
            datos.setPresionArterial(request.presionArterial());
            datos.setFondoUterino(request.fondoUterino());
            datos.setFrecuenciaFetal(request.frecuenciaFetal());
            datos.setMedicamentos(request.medicamentos());
            
            String datosGeneralesConIMC = (request.datosGenerales() != null && !request.datosGenerales().isEmpty()) 
                ? request.datosGenerales() + " | IMC: " + imcRedondeado + " (" + clasificacionIMC + ")"
                : "IMC: " + imcRedondeado + " (" + clasificacionIMC + ")";
            
            datos.setDatosGenerales(datosGeneralesConIMC);
            datos.setFechaRegistro(LocalDate.now()); 
            datosConsultaRepository.save(datos);
            
            return ResponseEntity.ok(Map.of(
                "success", true, 
                "message", "Datos actualizados exitosamente",
                "imc_calculado", imcRedondeado,
                "clasificacion_imc", clasificacionIMC,
                "datos_actualizados", Map.of(
                    "id_dc", datos.getIdDC(),
                    "peso", request.peso(),
                    "imc", imcRedondeado,
                    "clasificacion", clasificacionIMC,
                    "fecha_actualizacion", datos.getFechaRegistro()
                )
            ));
            
        } catch (Exception e) {
            System.err.println("ERROR en actualizarDatosConsulta: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                Map.of("error", "Error al actualizar datos: " + e.getMessage())
            );
        }
    }

    record ActualizarDatosRequest(
        Double peso,
        String presionArterial,
        String fondoUterino,
        String frecuenciaFetal,
        String medicamentos,
        String datosGenerales
    ) {}

    private String determinarClasificacionIMC(Double imc) {
        if (imc < 18.5) return "Bajo peso";
        if (imc < 25) return "Peso normal";
        if (imc < 30) return "Sobrepeso";
        return "Obesidad";
    }

    private String determinarColorClasificacion(String clasificacion) {
        switch (clasificacion) {
            case "Peso normal": return "success";
            case "Bajo peso":
            case "Sobrepeso": return "warning";
            case "Obesidad": return "danger";
            default: return "secondary";
        }
    }

    record ActualizarIMCRequest(
        Long idDc,
        Long idPrenatal,
        Double pesoActual
    ) {}
    
    record RegistrarDatosRequest(
        Long idPrenatal,
        Double peso,
        String presionArterial,
        String fondoUterino,
        String frecuenciaFetal,
        String medicamentos,
        String datosGenerales
    ) {}
    
    
}