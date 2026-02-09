package mx.edu.uacm.ws;

import mx.edu.uacm.ws.Entity.ConsultaPrenatal;
import mx.edu.uacm.ws.Entity.LastMestruacion;
import mx.edu.uacm.ws.Entity.Ultrasonido;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.UsuariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
//@CrossOrigin(origins = "*")
public class CitaController {

    @Autowired
    private UsuariaService usuariaService;

    private Long obtenerIdUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            throw new RuntimeException("Usuario no autenticado");
        }
        
        String email = authentication.getName();
        Usuaria usuaria = usuariaService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + email));
        return usuaria.getIdUser();
    }

    @PostMapping("/actualizar-mestruacion")
    public ResponseEntity<?> actualizarUltimaMestruacion(@RequestParam LocalDate fechaMestruacion) {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            
            if (fechaMestruacion.isAfter(LocalDate.now())) {
                return ResponseEntity.badRequest().body(
                    Map.of("status", "error", "message", "La fecha no puede ser futura")
                );
            }
            
            usuariaService.registrarUltimaMestruacionYCalcularCitas(idUser, fechaMestruacion);
            
            return ResponseEntity.ok().body(
                Map.of("status", "success", "message", "Citas prenatales calculadas correctamente")
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("status", "error", "message", "Error: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/calendario-completo")
    public List<Map<String, Object>> getCalendarioCompleto() {
        Long idUser = obtenerIdUsuarioAutenticado();
        
        List<Map<String, Object>> eventos = new ArrayList<>();
        
        List<ConsultaPrenatal> consultas = usuariaService.obtenerConsultasPrenatales(idUser);
        consultas.forEach(consulta -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("title", "Consulta Prenatal");
            evento.put("start", consulta.getFechaConsulta().toString());
            evento.put("color", "#1e40af"); // Azul
            evento.put("allDay", true);
            evento.put("description", "Consulta prenatal programada");
            evento.put("type", "consulta");
            eventos.add(evento);
        });
        
        List<Ultrasonido> ultrasonidos = usuariaService.obtenerUltrasonidos(idUser);
        ultrasonidos.forEach(ultrasonido -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("title", "Ultrasonido");
            evento.put("start", ultrasonido.getCitaUltrasonido().toString());
            evento.put("color", "#dc2626"); // Rojo
            evento.put("allDay", true);
            evento.put("description", "Cita de ultrasonido programada");
            evento.put("type", "ultrasonido");
            eventos.add(evento);
        });
        
        return eventos;
    }


    @GetMapping("/calendario")
    public List<Map<String, Object>> getCitasParaCalendario() {
        Long idUser = obtenerIdUsuarioAutenticado();
        List<ConsultaPrenatal> consultas = usuariaService.obtenerConsultasPrenatales(idUser);
        
        return consultas.stream().map(consulta -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("title", "Consulta Prenatal");
            evento.put("start", consulta.getFechaConsulta().toString() + "T00:00:00");
            //evento.put("start", formatDateForCalendar(consulta.getFechaConsulta()));
            evento.put("color", "#1e40af"); // Azul
            evento.put("allDay", true);
            evento.put("description", "Consulta prenatal programada");
            return evento;
        }).toList();
        
    }
    
    @GetMapping("/ultrasonidos/calendario")
    public List<Map<String, Object>> getUltrasonidosParaCalendario() {
        Long idUser = obtenerIdUsuarioAutenticado();
        List<Ultrasonido> ultrasonidos = usuariaService.obtenerUltrasonidos(idUser);
        
        return ultrasonidos.stream().map(ultrasonido -> {
            Map<String, Object> evento = new HashMap<>();
            evento.put("title", "📊 Ultrasonido");
            evento.put("start", ultrasonido.getCitaUltrasonido().toString() + "T00:00:00");
            //evento.put("start", formatDateForCalendar(ultrasonido.getCitaUltrasonido()));
            evento.put("color", "#dc2626"); // Rojo para diferenciar de consultas
            evento.put("allDay", true);
            evento.put("description", "Cita de ultrasonido programada - NOM-007");
            evento.put("type", "ultrasonido");
            return evento;
        }).toList();
    }
    
    @GetMapping("/ultrasonidos")
    public ResponseEntity<?> getUltrasonidos() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            System.out.println("Obteniendo ultrasonidos para usuario: " + idUser);
            
            List<Ultrasonido> ultrasonidos = usuariaService.obtenerUltrasonidos(idUser);
            System.out.println("Número de ultrasonidos encontrados: " + ultrasonidos.size());
            
            if (ultrasonidos.isEmpty()) {
                System.out.println("No hay ultrasonidos programados");
                return ResponseEntity.ok().body(Map.of("message", "No hay ultrasonidos programados"));
            }
            
            Map<String, List<Ultrasonido>> ultrasonidosPorTrimestre = 
                usuariaService.obtenerUltrasonidosPorTrimestre(idUser);
            
            Map<String, Object> response = new HashMap<>();
            response.put("ultrasonidos", ultrasonidos);
            response.put("porTrimestre", Map.of(
                "primerTrimestre", ultrasonidosPorTrimestre.get("primerTrimestre").size(),
                "segundoTrimestre", ultrasonidosPorTrimestre.get("segundoTrimestre").size(),
                "tercerTrimestre", ultrasonidosPorTrimestre.get("tercerTrimestre").size()
            ));
            response.put("total", ultrasonidos.size());
            
            System.out.println("Respuesta enviada con " + ultrasonidos.size() + " ultrasonidos");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.err.println("Error en /ultrasonidos: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                Map.of("error", "Error al obtener ultrasonidos: " + e.getMessage())
            );
        }
    }

    @GetMapping("/ultima-mestruacion")
    public ResponseEntity<?> getUltimaMestruacion() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            LastMestruacion lastMest = usuariaService.obtenerUltimaMestruacion(idUser).orElse(null);
            
            if (lastMest != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("ultMest", lastMest.getUltMest());
                response.put("idLast", lastMest.getId_last());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.ok().body(Map.of("message", "No hay datos registrados"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Error al obtener información: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/info-embarazo")
    public ResponseEntity<?> getInfoEmbarazo() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            LastMestruacion lastMest = usuariaService.obtenerUltimaMestruacion(idUser).orElse(null);
            
            if (lastMest == null) {
                return ResponseEntity.ok().body(Map.of("message", "No hay información de embarazo registrada"));
            }
            
            LocalDate fechaMestruacion = lastMest.getUltMest();
            LocalDate hoy = LocalDate.now();
            
            long semanas = ChronoUnit.WEEKS.between(fechaMestruacion, hoy);
            LocalDate fechaParto = fechaMestruacion.plusWeeks(40);
            String trimestre = calcularTrimestre(semanas);
            long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaParto);
            
            // Obtener información de citas
            List<ConsultaPrenatal> consultas = usuariaService.obtenerConsultasPrenatales(idUser);
            List<Ultrasonido> ultrasonidos = usuariaService.obtenerUltrasonidos(idUser);
            
            Map<String, Object> info = new HashMap<>();
            info.put("ultimaMestruacion", fechaMestruacion);
            info.put("semanasEmbarazo", semanas);
            info.put("fechaParto", fechaParto);
            info.put("trimestre", trimestre);
            info.put("diasRestantes", diasRestantes);
            info.put("totalConsultas", consultas.size());
            info.put("totalUltrasonidos", ultrasonidos.size());
            
            return ResponseEntity.ok(info);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Error al calcular información: " + e.getMessage())
            );
        }
    }
    
    /*
    @GetMapping("/info-embarazo")
    public ResponseEntity<?> getInfoEmbarazo() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            LastMestruacion lastMest = usuariaService.obtenerUltimaMestruacion(idUser).orElse(null);
            
            if (lastMest == null) {
                return ResponseEntity.ok().body(Map.of("message", "No hay información de embarazo registrada"));
            }
            
            LocalDate fechaMestruacion = lastMest.getUltMest();
            LocalDate hoy = LocalDate.now();
            
            long semanas = ChronoUnit.WEEKS.between(fechaMestruacion, hoy);
            LocalDate fechaParto = fechaMestruacion.plusWeeks(40);
            String trimestre = calcularTrimestre(semanas);
            long diasRestantes = ChronoUnit.DAYS.between(hoy, fechaParto);
            
            Map<String, Object> info = new HashMap<>();
            info.put("ultimaMestruacion", fechaMestruacion);
            info.put("semanasEmbarazo", semanas);
            info.put("fechaParto", fechaParto);
            info.put("trimestre", trimestre);
            info.put("diasRestantes", diasRestantes);
            
            return ResponseEntity.ok(info);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Error al calcular información: " + e.getMessage())
            );
        }
    }
    */

    private String calcularTrimestre(long semanas) {
        if (semanas <= 13) return "Primer trimestre";
        if (semanas <= 26) return "Segundo trimestre";
        return "Tercer trimestre";
    }
    
    @GetMapping("/rangos-semanas")
    public ResponseEntity<?> getRangosSemanas() {
        try {
            List<Map<String, String>> rangos = new ArrayList<>();
            
            String[] rangosDisponibles = {
                "0-8", "10-14", "16-18", "22", "28", "32", "36", "38-41"
            };
            
            String[] descripciones = {
                "Primeras semanas (0-8)",
                "Primer trimestre (10-14)",
                "Segundo trimestre (16-18)", 
                "Segundo trimestre (22)",
                "Tercer trimestre (28)",
                "Tercer trimestre (32)",
                "Tercer trimestre (36)",
                "Final del embarazo (38-41)"
            };
            
            for (int i = 0; i < rangosDisponibles.length; i++) {
                Map<String, String> rango = new HashMap<>();
                rango.put("rango", rangosDisponibles[i]);
                rango.put("descripcion", descripciones[i]);
                rangos.add(rango);
            }
            
            return ResponseEntity.ok(rangos);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Error al obtener rangos: " + e.getMessage())
            );
        }
    }

    @PostMapping("/confirmar-cita")
    public ResponseEntity<?> confirmarCita(
            @RequestParam String tipoCita,
            @RequestParam String rangoSemanas,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaSeleccionada) {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            
            System.out.println("Confirmando cita - Usuario: " + idUser + 
                              ", Tipo: " + tipoCita +
                              ", Rango: " + rangoSemanas + 
                              ", Fecha: " + fechaSeleccionada);
            
            Map<String, Object> resultado = usuariaService.confirmarCita(
                idUser, tipoCita, rangoSemanas, fechaSeleccionada);
            
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            System.err.println("Error al confirmar cita: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                Map.of("status", "error", "message", "Error: " + e.getMessage())
            );
        }
    }
    
    /*
    @PostMapping("/confirmar-citas")
    public ResponseEntity<?> confirmarCitas(@RequestBody ConfirmarCitasRequest request) {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            
            System.out.println("Confirmando citas - Usuario: " + idUser + 
                              ", Prenatal: " + request.confirmarPrenatal() +
                              ", Ultrasonido: " + request.confirmarUltrasonido() +
                              ", Fecha: " + request.fechaSeleccionada());
            
            List<Map<String, Object>> resultados = new ArrayList<>();
            
            if (request.confirmarPrenatal() && request.rangoSemanasPrenatal() != null) {
                try {
                    Map<String, Object> resultadoPrenatal = usuariaService.confirmarCita(
                        idUser, "prenatal", request.rangoSemanasPrenatal(), request.fechaSeleccionada());
                    resultados.add(resultadoPrenatal);
                } catch (Exception e) {
                    resultados.add(Map.of(
                        "tipo", "prenatal",
                        "status", "error",
                        "message", "Error: " + e.getMessage()
                    ));
                }
            }
            
            if (request.confirmarUltrasonido() && request.rangoSemanasUltrasonido() != null) {
                try {
                    Map<String, Object> resultadoUltrasonido = usuariaService.confirmarCita(
                        idUser, "ultrasonido", request.rangoSemanasUltrasonido(), request.fechaSeleccionada());
                    resultados.add(resultadoUltrasonido);
                } catch (Exception e) {
                    resultados.add(Map.of(
                        "tipo", "ultrasonido",
                        "status", "error",
                        "message", "Error: " + e.getMessage()
                    ));
                }
            }
            
            return ResponseEntity.ok(Map.of(
                "resultados", resultados,
                "status", "success",
                "total_confirmadas", resultados.stream()
                    .filter(r -> "success".equals(r.get("status")))
                    .count()
            ));
            
        } catch (Exception e) {
            System.err.println("Error al confirmar citas: " + e.getMessage());
            return ResponseEntity.badRequest().body(
                Map.of("status", "error", "message", "Error: " + e.getMessage())
            );
        }
    }
    */
    
        
    @GetMapping("/estado-citas")
    public ResponseEntity<?> getEstadoCitas() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            Map<String, Object> estado = usuariaService.obtenerEstadoCitasConfirmadas(idUser);
            
            if (estado.containsKey("error")) {
                return ResponseEntity.badRequest().body(
                    Map.of("error", estado.get("error"))
                );
            }
            
            return ResponseEntity.ok(estado);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("error", "Error al obtener estado de citas: " + e.getMessage())
            );
        }
    }
    
    @GetMapping("/contadores-citas")
    public ResponseEntity<?> getContadoresCitas() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            Map<String, Object> estado = usuariaService.obtenerEstadoCitasConfirmadas(idUser);
            
            if (estado.containsKey("error")) {
                return ResponseEntity.ok(Map.of(
                    "consultasConfirmadas", 0,
                    "ultrasonidosConfirmados", 0,
                    "totalConsultas", 8,
                    "totalUltrasonidos", 3,
                    "error", estado.get("error")
                ));
            }
            
            return ResponseEntity.ok(estado);
            
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "consultasConfirmadas", 0,
                "ultrasonidosConfirmados", 0,
                "totalConsultas", 8,
                "totalUltrasonidos", 3,
                "error", "Error al obtener contadores"
            ));
        }
    }
    
    private String formatDateForCalendar(LocalDate date) {
    	if (date == null) return "";
    	return date.toString();
    }

    record ConfirmarCitasRequest(
        boolean confirmarPrenatal,
        String rangoSemanasPrenatal,
        boolean confirmarUltrasonido,
        String rangoSemanasUltrasonido,
        LocalDate fechaSeleccionada
    ) {}
}