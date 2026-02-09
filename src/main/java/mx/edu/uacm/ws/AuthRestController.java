package mx.edu.uacm.ws;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.UsuariaService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class AuthRestController {

    @Autowired
    private UsuariaService usuariaService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            boolean credencialesValidas = usuariaService.validarCredenciales(
                request.getEmail(), 
                request.getPassword()
            );
            
            if (!credencialesValidas) {
                return ResponseEntity.status(401).body(
                    Map.of("success", false, "message", "Credenciales incorrectas")
                );
            }
            
            Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(request.getEmail());
            
            if (usuariaOpt.isPresent()) {
                Usuaria usuaria = usuariaOpt.get();
                
                if (!usuaria.getActivo()) {
                    return ResponseEntity.status(403).body(
                        Map.of("success", false, "message", "Cuenta inactiva")
                    );
                }
                
                int edad = calcularEdad(usuaria.getFechaNacimiento());
                
                boolean tieneMestruacion = false;
                LocalDate fechaMestruacion = null;
                
                var mestruacionOpt = usuariaService.obtenerUltimaMestruacion(usuaria.getIdUser());
                if (mestruacionOpt.isPresent()) {
                    tieneMestruacion = true;
                    fechaMestruacion = mestruacionOpt.get().getUltMest();
                }
                
                Map<String, Object> citasInfo = new HashMap<>();
                if (tieneMestruacion) {
                    var consultas = usuariaService.obtenerConsultasPrenatales(usuaria.getIdUser());
                    var ultrasonidos = usuariaService.obtenerUltrasonidos(usuaria.getIdUser());
                    var estadoCitas = usuariaService.obtenerEstadoCitasConfirmadas(usuaria.getIdUser());
                    
                    citasInfo.put("tieneCitas", true);
                    citasInfo.put("totalConsultas", consultas.size());
                    citasInfo.put("totalUltrasonidos", ultrasonidos.size());
                    citasInfo.put("estadoCitas", estadoCitas);
                } else {
                    citasInfo.put("tieneCitas", false);
                    citasInfo.put("mensaje", "Registra tu última menstruación para ver tu calendario de citas");
                }
                
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("message", "Login exitoso");
                response.put("user", crearUserResponse(usuaria));
                response.put("edad", edad);
                response.put("token", "user_" + usuaria.getIdUser() + "_" + System.currentTimeMillis());
                response.put("tieneMestruacionRegistrada", tieneMestruacion);
                response.put("fechaMestruacion", fechaMestruacion);
                response.put("citas", citasInfo);
                
                String pantallaInicial = determinarPantallaInicial(edad, tieneMestruacion);
                response.put("pantallaInicial", pantallaInicial);
                response.put("pantallaInicialMensaje", getMensajePantallaInicial(pantallaInicial));
                
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.status(404).body(
                Map.of("success", false, "message", "Usuario no encontrado")
            );
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                Map.of("success", false, "message", "Error: " + e.getMessage())
            );
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
        	
            Optional<Usuaria> existingUser = usuariaService.findByEmail(request.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(400).body(
                    Map.of("success", false, "message", "El email ya está registrado")
                );
            }
            
            /*
            if (usuariaService.existsByCurp(request.getCurp())) {
                return ResponseEntity.status(400).body(
                    Map.of("success", false, "message", "La CURP ya está registrada")
                );
            }
            */
            
            if (request.getEmail() == null || request.getEmail().isEmpty() ||
                request.getPassword() == null || request.getPassword().isEmpty() ||
                request.getNombres() == null || request.getNombres().isEmpty() ||
                request.getApellidos() == null || request.getApellidos().isEmpty() ||
                request.getFechaNacimiento() == null ||
                request.getCurp() == null || request.getCurp().isEmpty() ||
                request.getNumCelular() == null || request.getNumCelular().isEmpty()) {
                
                return ResponseEntity.status(400).body(
                    Map.of("success", false, "message", "Todos los campos obligatorios son requeridos")
                );
            }
            
            if (!isValidEmail(request.getEmail())) {
                return ResponseEntity.status(400).body(
                    Map.of("success", false, "message", "El formato del email no es válido")
                );
            }
            
            if (request.getPassword().length() < 6) {
                return ResponseEntity.status(400).body(
                    Map.of("success", false, "message", "La contraseña debe tener al menos 6 caracteres")
                );
            }
            
            Usuaria nuevaUsuaria = new Usuaria();
            nuevaUsuaria.setEmail(request.getEmail());
            nuevaUsuaria.setPasswordHash(request.getPassword()); // El servicio debería encriptar esto
            nuevaUsuaria.setNombres(request.getNombres());
            nuevaUsuaria.setApellidos(request.getApellidos());
            nuevaUsuaria.setFechaNacimiento(request.getFechaNacimiento());
            nuevaUsuaria.setDomicilio(request.getDomicilio() != null ? request.getDomicilio() : "");
            nuevaUsuaria.setCurp(request.getCurp());
            nuevaUsuaria.setNumCelular(request.getNumCelular());
            nuevaUsuaria.setNumEmergencia(request.getNumEmergencia() != null ? request.getNumEmergencia() : "");
            if (request.getTalla() != null) {
                nuevaUsuaria.setTalla(Double.valueOf(request.getTalla())); // Convertir Float a Double
            } else {
                nuevaUsuaria.setTalla(0.0);
            }
            
            if (request.getPeso() != null) {
                nuevaUsuaria.setPeso(Double.valueOf(request.getPeso())); // Convertir Float a Double
            } else {
                nuevaUsuaria.setPeso(0.0);
            }
            
            nuevaUsuaria.setFechaRegistro(LocalDateTime.now());
            nuevaUsuaria.setActivo(true);
            
            Usuaria usuariaGuardada = usuariaService.registrarUsuaria(nuevaUsuaria);
            
            int edad = calcularEdad(usuariaGuardada.getFechaNacimiento());
            String pantallaInicial = determinarPantallaInicial(edad, false);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Registro exitoso. ¡Bienvenida a Salud Materna!");
            response.put("usuaria", crearUserResponse(usuariaGuardada));
            response.put("edad", edad);
            response.put("pantallaInicial", pantallaInicial);
            response.put("pantallaInicialMensaje", getMensajePantallaInicial(pantallaInicial));
            
            return ResponseEntity.status(201).body(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                Map.of("success", false, "message", "Error en el registro: " + e.getMessage())
            );
        }
    }
    
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && email.matches(emailRegex);
    }
    
    private String determinarPantallaInicial(int edad, boolean tieneMestruacion) {
        if (edad >= 12 && edad <= 48) {
            if (tieneMestruacion) {
                return "calendario_citas"; 
            } else {
                return "registrar_mestruacion"; 
            }
        } else if (edad >= 4 && edad <= 11) {
            return "app_infancia";
        } else {
            return "home"; 
        }
    }
    
    private String getMensajePantallaInicial(String pantalla) {
        switch(pantalla) {
            case "calendario_citas":
                return "¡Bienvenida! Aquí está tu calendario de citas prenatales";
            case "registrar_mestruacion":
                return "Para ver tu calendario de citas, primero registra tu última menstruación";
            case "app_infancia":
                return "Bienvenida a la sección de infancia";
            default:
                return "Bienvenida a Salud Materna";
        }
    }
    
    private int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    private Map<String, Object> crearUserResponse(Usuaria usuaria) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", usuaria.getIdUser());
        response.put("nombres", usuaria.getNombres());
        response.put("apellidos", usuaria.getApellidos());
        response.put("nombreCompleto", usuaria.getNombres() + " " + usuaria.getApellidos());
        response.put("email", usuaria.getEmail());
        response.put("fechaNacimiento", usuaria.getFechaNacimiento());
        response.put("domicilio", usuaria.getDomicilio());
        response.put("curp", usuaria.getCurp());
        response.put("celular", usuaria.getNumCelular());
        response.put("emergencia", usuaria.getNumEmergencia());
        response.put("talla", usuaria.getTalla());
        response.put("peso", usuaria.getPeso());
        response.put("fechaRegistro", usuaria.getFechaRegistro());
        response.put("activo", usuaria.getActivo());
        return response;
    }
    
    public static class LoginRequest {
        private String email;
        private String password;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    public static class RegisterRequest {
        private String email;
        private String password;
        private String nombres;
        private String apellidos;
        private LocalDate fechaNacimiento;
        private String domicilio;
        private String curp;
        private String numCelular;
        private String numEmergencia;
        private Float talla;
        private Float peso;
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        
        public String getNombres() { return nombres; }
        public void setNombres(String nombres) { this.nombres = nombres; }
        
        public String getApellidos() { return apellidos; }
        public void setApellidos(String apellidos) { this.apellidos = apellidos; }
        
        public LocalDate getFechaNacimiento() { return fechaNacimiento; }
        public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
        
        public String getDomicilio() { return domicilio; }
        public void setDomicilio(String domicilio) { this.domicilio = domicilio; }
        
        public String getCurp() { return curp; }
        public void setCurp(String curp) { this.curp = curp; }
        
        public String getNumCelular() { return numCelular; }
        public void setNumCelular(String numCelular) { this.numCelular = numCelular; }
        
        public String getNumEmergencia() { return numEmergencia; }
        public void setNumEmergencia(String numEmergencia) { this.numEmergencia = numEmergencia; }
        
        public Float getTalla() { return talla; }
        public void setTalla(Float talla) { this.talla = talla; }
        
        public Float getPeso() { return peso; }
        public void setPeso(Float peso) { this.peso = peso; }
    }
}