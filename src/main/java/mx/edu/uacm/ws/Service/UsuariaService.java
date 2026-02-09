package mx.edu.uacm.ws.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import mx.edu.uacm.ws.Entity.ConsultaPrenatal;
import mx.edu.uacm.ws.Entity.LastMestruacion;
import mx.edu.uacm.ws.Entity.Ultrasonido;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Repository.ConsultaPrenatalRepository;
import mx.edu.uacm.ws.Repository.LastMestruacionRepository;
import mx.edu.uacm.ws.Repository.UltrasonidoRepository;
import mx.edu.uacm.ws.Repository.UsuariaRepository;


@Service
public class UsuariaService {
    
    @Autowired
    private UsuariaRepository usuariaRepository;
    
    @Autowired
    private LastMestruacionRepository lastMestruacionRepository;

    @Autowired
    private ConsultaPrenatalRepository consultaPrenatalRepository;
    
    @Autowired
    private UltrasonidoRepository ultrasonidoRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private EntityManager entityManager;
    
    public Usuaria registrarUsuaria(Usuaria usuaria) {
        if (usuariaRepository.existsByEmail(usuaria.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }
        
        String passwordHash = passwordEncoder.encode(usuaria.getPasswordHash());
        usuaria.setPasswordHash(passwordHash);
        usuaria.setFechaRegistro(LocalDateTime.now());
        usuaria.setActivo(true);
        
        return usuariaRepository.save(usuaria);
    }
    
    public boolean actualizarPasswordConFechaNacimiento(String email, LocalDate fechaNacimiento, String nuevaPassword) {
        Optional<Usuaria> usuariaOpt = usuariaRepository.findByEmail(email);
        
        if (usuariaOpt.isPresent()) {
            Usuaria usuaria = usuariaOpt.get();
            
            // Verificar que la fecha de nacimiento coincida
            if (usuaria.getFechaNacimiento().equals(fechaNacimiento)) {
                String nuevaPasswordHash = passwordEncoder.encode(nuevaPassword);
                usuaria.setPasswordHash(nuevaPasswordHash);
                usuariaRepository.save(usuaria);
                return true;
            }
        }
        
        return false;
    }
    
    /*
    public boolean actualizarPassword(String email, String passwordActual, String nuevaPassword) {
        Optional<Usuaria> usuariaOpt = usuariaRepository.findByEmail(email);
        
        if (usuariaOpt.isPresent()) {
            Usuaria usuaria = usuariaOpt.get();
            
            if (passwordEncoder.matches(passwordActual, usuaria.getPasswordHash())) {
                String nuevaPasswordHash = passwordEncoder.encode(nuevaPassword);
                usuaria.setPasswordHash(nuevaPasswordHash);
                usuariaRepository.save(usuaria);
                return true;
            }
        }
        
        return false;
    }
    */
    
    public Optional<Usuaria> findByEmail(String email) {
        return usuariaRepository.findByEmail(email);
    }
    
    public Optional<Usuaria> findById(Long id) {
        return usuariaRepository.findById(id);
    }
    
    public boolean existeEmail(String email) {
        return usuariaRepository.existsByEmail(email);
    }
    
    public boolean validarCredenciales(String email, String password) {
        Optional<Usuaria> usuariaOpt = findByEmail(email);
        
        if (usuariaOpt.isPresent()) {
            Usuaria usuaria = usuariaOpt.get();
            return passwordEncoder.matches(password, usuaria.getPasswordHash());
        }
        
        return false;
    }
    
    @Transactional
    public void registrarUltimaMestruacionYCalcularCitas(Long idUser, LocalDate fechaUltimaMestruacion) {
        try {
            LastMestruacion lastMest = lastMestruacionRepository.findByUserId(idUser).orElse(new LastMestruacion());
            
            lastMest.setIdUser(idUser);
            lastMest.setUltMest(fechaUltimaMestruacion);
            lastMestruacionRepository.save(lastMest);
                        
            StoredProcedureQuery queryPrenatal = entityManager
                .createStoredProcedureQuery("CalcularConsultasPrenatales")
                .registerStoredProcedureParameter("p_id_user", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_inicio_embarazo", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_registro", LocalDate.class, ParameterMode.IN)
                .setParameter("p_id_user", idUser)
                .setParameter("p_fecha_inicio_embarazo", fechaUltimaMestruacion)
                .setParameter("p_fecha_registro", LocalDate.now());
            
            queryPrenatal.execute();
            
            StoredProcedureQuery queryUltrasonido = entityManager
                .createStoredProcedureQuery("CalcularConsultasUltrasonido")
                .registerStoredProcedureParameter("p_id_user", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_inicio_embarazo", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_registro", LocalDate.class, ParameterMode.IN)
                .setParameter("p_id_user", idUser)
                .setParameter("p_fecha_inicio_embarazo", fechaUltimaMestruacion)
                .setParameter("p_fecha_registro", LocalDate.now());
            
            queryUltrasonido.execute();
            
            System.out.println("Procesos completados: Consultas prenatales y ultrasonidos calculados");
            
        } catch (Exception e) {
            System.err.println("Error al calcular citas: " + e.getMessage());
            throw new RuntimeException("Error al calcular las citas: " + e.getMessage(), e);
        }
    }
    
    /*
    @Transactional
    public void registrarUltimaMestruacionYCalcularCitas(Long idUser, LocalDate fechaUltimaMestruacion) {
        try {
            System.out.println(" Registrando última menstruación para usuario: " + idUser);
            
            LastMestruacion lastMest = lastMestruacionRepository.findByUserId(idUser)
                .orElse(new LastMestruacion());
            
            lastMest.setIdUser(idUser);
            lastMest.setUltMest(fechaUltimaMestruacion);
            LastMestruacion savedLastMest = lastMestruacionRepository.save(lastMest);
            
            System.out.println("Última menstruación guardada con ID: " + savedLastMest.getId_last());
            
            System.out.println("Ejecutando stored procedure...");
            StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("CalcularConsultasPrenatales")
                .registerStoredProcedureParameter("p_id_user", Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_inicio_embarazo", LocalDate.class, ParameterMode.IN)
                .registerStoredProcedureParameter("p_fecha_registro", LocalDate.class, ParameterMode.IN)
                .setParameter("p_id_user", idUser)
                .setParameter("p_fecha_inicio_embarazo", fechaUltimaMestruacion)
                .setParameter("p_fecha_registro", LocalDate.now());
            
            query.execute();
            System.out.println("Stored procedure ejecutado exitosamente");
            
            
            List<ConsultaPrenatal> citasCreadas = consultaPrenatalRepository.findByUserId(idUser);
            System.out.println("Número de citas creadas: " + citasCreadas.size());
            
        } catch (Exception e) {
            System.err.println("Error al calcular consultas prenatales: " + e.getMessage());
            throw new RuntimeException("Error al calcular las citas prenatales: " + e.getMessage(), e);
        }
    }
    */

    public Optional<LastMestruacion> obtenerUltimaMestruacion(Long idUser) {
        return lastMestruacionRepository.findByUserId(idUser);
    }

    public List<ConsultaPrenatal> obtenerConsultasPrenatales(Long idUser) {
        return consultaPrenatalRepository.findByUserId(idUser);
    }
    
    
    public List<Ultrasonido> obtenerUltrasonidos(Long idUser) {
        return ultrasonidoRepository.findByUserId(idUser);
    }
    
    public Map<String, List<Ultrasonido>> obtenerUltrasonidosPorTrimestre(Long idUser) {
	    List<Ultrasonido> todosUltrasonidos = obtenerUltrasonidos(idUser);
	    Map<String, List<Ultrasonido>> ultrasonidosPorTrimestre = new HashMap<>();
	    
	    List<Ultrasonido> primerTrimestre = new ArrayList<>();
	    List<Ultrasonido> segundoTrimestre = new ArrayList<>();
	    List<Ultrasonido> tercerTrimestre = new ArrayList<>();
	    
	    Optional<LastMestruacion> lastMestOpt = obtenerUltimaMestruacion(idUser);
	    
	    if (lastMestOpt.isPresent()) {
	        LocalDate fechaMestruacion = lastMestOpt.get().getUltMest();
	        
	        for (Ultrasonido ultrasonido : todosUltrasonidos) {
	            long semanas = ChronoUnit.WEEKS.between(fechaMestruacion, ultrasonido.getCitaUltrasonido());
	            
	            if (semanas <= 13) {
	                primerTrimestre.add(ultrasonido);
	            } else if (semanas <= 26) {
	                segundoTrimestre.add(ultrasonido);
	            } else {
	                tercerTrimestre.add(ultrasonido);
	            }
	        }
	    }
	    
	    ultrasonidosPorTrimestre.put("primerTrimestre", primerTrimestre);
	    ultrasonidosPorTrimestre.put("segundoTrimestre", segundoTrimestre);
	    ultrasonidosPorTrimestre.put("tercerTrimestre", tercerTrimestre);
	    
	    return ultrasonidosPorTrimestre;
		
		}
	
	@Transactional
	public Map<String, Object> confirmarCita(Long idUser, String tipoCita, String rangoSemanas, LocalDate fechaSeleccionada) {
	    try {
	        String procedureName = "";
	        
	        if ("prenatal".equalsIgnoreCase(tipoCita)) {
	            procedureName = "SeleccionarConsultaPrenatal";
	        } else if ("ultrasonido".equalsIgnoreCase(tipoCita)) {
	            procedureName = "SeleccionarConsultaUltrasonido";
	        } else {
	            throw new RuntimeException("Tipo de cita no válido: " + tipoCita);
	        }
	        
	        StoredProcedureQuery query = entityManager
	            .createStoredProcedureQuery(procedureName)
	            .registerStoredProcedureParameter("p_id_user", Long.class, ParameterMode.IN)
	            .registerStoredProcedureParameter("p_rango_semanas", String.class, ParameterMode.IN)
	            .registerStoredProcedureParameter("p_fecha_seleccionada", LocalDate.class, ParameterMode.IN);
	        
	        query.setParameter("p_id_user", idUser)
	             .setParameter("p_rango_semanas", rangoSemanas)
	             .setParameter("p_fecha_seleccionada", fechaSeleccionada);
	        
	        query.execute();
	        
	        List<Object[]> results = query.getResultList();
	        
	        if (!results.isEmpty()) {
	            Object[] result = results.get(0);
	            Map<String, Object> response = new HashMap<>();
	            response.put("mensaje", result[0]);
	            response.put("rango_semanas", result[1]);
	            response.put("rango_inicio", result[2]);
	            response.put("rango_fin", result[3]);
	            response.put("fecha_seleccionada", result[4]);
	            response.put("total_consultas", result[5]);
	            response.put("status", "success");
	            response.put("tipo_cita", tipoCita);
	            return response;
	        } else {
	            throw new RuntimeException("No se pudo confirmar la cita");
	        }
	        
	    } catch (Exception e) {
	        System.err.println("Error al confirmar cita " + tipoCita + ": " + e.getMessage());
	        throw new RuntimeException("Error al confirmar la cita: " + e.getMessage(), e);
	    }
	}
	
	//
	public Map<String, Object> obtenerEstadoCitasConfirmadas(Long idUser) {
	    try {
	        Map<String, Object> resultado = new HashMap<>();
	        
	        Optional<LastMestruacion> lastMestOpt = obtenerUltimaMestruacion(idUser);
	        if (lastMestOpt.isEmpty()) {
	            resultado.put("error", "No hay información de embarazo");
	            return resultado;
	        }
	        
	        LocalDate fechaMestruacion = lastMestOpt.get().getUltMest();
	        
	        List<ConsultaPrenatal> todasConsultas = consultaPrenatalRepository.findByUserId(idUser);
	        
	        List<Ultrasonido> todosUltrasonidos = ultrasonidoRepository.findByUserId(idUser);
	        
	        String[] rangosPrenatales = {"0-8", "10-14", "16-18", "22", "28", "32", "36", "38-41"};
	        String[] rangosUltrasonido = {"10-14", "18-24", "29-30"};
	        
	        List<Map<String, Object>> estadoConsultas = new ArrayList<>();
	        List<Map<String, Object>> estadoUltrasonidos = new ArrayList<>();
	        
	        int consultasConfirmadasCount = 0;
	        int ultrasonidosConfirmadosCount = 0;
	        
	        for (int i = 0; i < rangosPrenatales.length; i++) {
	            String rango = rangosPrenatales[i];
	            Map<String, Object> estado = new HashMap<>();
	            estado.put("numero", i + 1);
	            estado.put("rango", rango);
	            estado.put("nombre", getNombreConsultaPrenatal(rango));
	            
	            LocalDate inicioRango = calcularInicioRangoPrenatal(fechaMestruacion, rango);
	            LocalDate finRango = calcularFinRangoPrenatal(fechaMestruacion, rango);
	            
	            int contador = 0;
	            LocalDate fechaConfirmada = null;
	            
	            for (ConsultaPrenatal consulta : todasConsultas) {
	                LocalDate fecha = consulta.getFechaConsulta();
	                if (fecha != null && 
	                    !fecha.isBefore(inicioRango) && 
	                    !fecha.isAfter(finRango)) {
	                    contador++;
	                    fechaConfirmada = fecha;
	                }
	            }
	            
	            boolean confirmada = contador == 1;
	            
	            if (confirmada) {
	                consultasConfirmadasCount++;
	                estado.put("confirmada", true);
	                estado.put("fecha", fechaConfirmada);
	            } else {
	                estado.put("confirmada", false);
	                estado.put("fecha", null);
	            }
	            
	            estadoConsultas.add(estado);
	        }
	        
	        for (int i = 0; i < rangosUltrasonido.length; i++) {
	            String rango = rangosUltrasonido[i];
	            Map<String, Object> estado = new HashMap<>();
	            estado.put("numero", i + 1);
	            estado.put("rango", rango);
	            estado.put("nombre", getNombreUltrasonido(rango));
	            
	            LocalDate inicioRango = calcularInicioRangoUltrasonido(fechaMestruacion, rango);
	            LocalDate finRango = calcularFinRangoUltrasonido(fechaMestruacion, rango);
	            
	            int contador = 0;
	            LocalDate fechaConfirmada = null;
	            
	            for (Ultrasonido ultrasonido : todosUltrasonidos) {
	                LocalDate fecha = ultrasonido.getCitaUltrasonido();
	                if (fecha != null && 
	                    !fecha.isBefore(inicioRango) && 
	                    !fecha.isAfter(finRango)) {
	                    contador++;
	                    fechaConfirmada = fecha;
	                }
	            }
	            
	            boolean confirmado = contador == 1;
	            
	            if (confirmado) {
	                ultrasonidosConfirmadosCount++;
	                estado.put("confirmada", true);
	                estado.put("fecha", fechaConfirmada);
	            } else {
	                estado.put("confirmada", false);
	                estado.put("fecha", null);
	            }
	            
	            estadoUltrasonidos.add(estado);
	        }
	        
	        resultado.put("consultasPrenatales", estadoConsultas);
	        resultado.put("ultrasonidos", estadoUltrasonidos);
	        resultado.put("totalConsultasConfirmadas", consultasConfirmadasCount);
	        resultado.put("totalUltrasonidosConfirmados", ultrasonidosConfirmadosCount);
	        resultado.put("totalConsultasEsperadas", rangosPrenatales.length);
	        resultado.put("totalUltrasonidosEsperados", rangosUltrasonido.length);
	        
	        return resultado;
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	        Map<String, Object> error = new HashMap<>();
	        error.put("error", "Error al obtener estado de citas: " + e.getMessage());
	        return error;
	    }
	}
	
	public Usuaria actualizarUsuaria(Usuaria usuaria) {
	    if (usuaria.getIdUser() == null) {
	        throw new RuntimeException("La usuaria no tiene ID para actualizar");
	    }
	    
	    Optional<Usuaria> existenteOpt = usuariaRepository.findById(usuaria.getIdUser());
	    if (existenteOpt.isEmpty()) {
	        throw new RuntimeException("Usuaria no encontrada");
	    }
	    
	    Usuaria existente = existenteOpt.get();
	    
	    // Mantener datos que no deben cambiar
	    usuaria.setEmail(existente.getEmail());
	    usuaria.setPasswordHash(existente.getPasswordHash());
	    usuaria.setFechaRegistro(existente.getFechaRegistro());
	    usuaria.setActivo(existente.getActivo());
	    
	    return usuariaRepository.save(usuaria);
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
	
	private LocalDate calcularInicioRangoUltrasonido(LocalDate fechaMestruacion, String rango) {
	    switch(rango) {
	        case "10-14": return fechaMestruacion.plusWeeks(10);
	        case "18-24": return fechaMestruacion.plusWeeks(18);
	        case "29-30": return fechaMestruacion.plusWeeks(29);
	        default: return fechaMestruacion;
	    }
	}
	
	private LocalDate calcularFinRangoUltrasonido(LocalDate fechaMestruacion, String rango) {
	    switch(rango) {
	        case "10-14": return fechaMestruacion.plusWeeks(14);
	        case "18-24": return fechaMestruacion.plusWeeks(24);
	        case "29-30": return fechaMestruacion.plusWeeks(30);
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
	
	private String getNombreUltrasonido(String rango) {
	    switch(rango) {
	        case "10-14": return "Primer ultrasonido";
	        case "18-24": return "Segundo ultrasonido";
	        case "29-30": return "Tercer ultrasonido";
	        default: return "Ultrasonido";
	    }
	}
}
