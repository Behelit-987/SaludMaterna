package mx.edu.uacm.ws;

import mx.edu.uacm.ws.Entity.CuestionarioSalud;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.CuestionarioSaludService;
import mx.edu.uacm.ws.Service.UsuariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuestionario")
//@CrossOrigin(origins = "*")
public class CuestionarioSaludController {
    
    @Autowired
    private CuestionarioSaludService cuestionarioSaludService;
    
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
    
    @PostMapping("/guardar")
    public ResponseEntity<?> guardarCuestionario(@RequestBody CuestionarioSalud cuestionario) {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            cuestionario.setIdUser(idUser);
            
            System.out.println("Guardando cuestionario para usuario: " + idUser);
            System.out.println("Primer embarazo: " + cuestionario.getPrimerEmbarazo());
            
            CuestionarioSalud cuestionarioGuardado = cuestionarioSaludService.guardarCuestionario(cuestionario);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            //response.put("status", true);            
            response.put("message", "Cuestionario guardado correctamente");
            /*
            response.put("data", Map.of(
            		"idCuestionario", cuestionarioGuardado.getIdCuestionario(),
            		"primerEmbarazo", cuestionarioGuardado.getPrimerEmbarazo()
            		));
            */
            response.put("idCuestionario", cuestionarioGuardado.getIdCuestionario());
            response.put("primerEmbarazo", cuestionarioGuardado.getPrimerEmbarazo());
            
            
            return ResponseEntity.ok().body(response);
            
        } catch (Exception e) {
            System.err.println("Error al guardar cuestionario: " + e.getMessage());
            e.printStackTrace();
            
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "Error al guardar cuestionario: " + e.getMessage()
            ));
        }
    }
    
    @GetMapping("/obtener")
    public ResponseEntity<?> obtenerCuestionario() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            Optional<CuestionarioSalud> cuestionario = cuestionarioSaludService.obtenerCuestionarioPorUsuario(idUser);
            
            if (cuestionario.isPresent()) {
                //System.out.println("Cuestionario encontrado para usuario: " + idUser);
                return ResponseEntity.ok(cuestionario.get());
            } else {
                System.out.println("No hay cuestionario para usuario: " + idUser);
                return ResponseEntity.ok().body(Map.of("message", "No hay cuestionario registrado"));
            }
            
        } catch (Exception e) {
            System.err.println("Error al obtener cuestionario: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Error al obtener cuestionario: " + e.getMessage()
            ));
        }
    }
    
    @GetMapping("/existe")
    public ResponseEntity<?> existeCuestionario() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            boolean existe = cuestionarioSaludService.existeCuestionarioPorUsuario(idUser);
            
            System.out.println("Verificando cuestionario para usuario " + idUser + ": " + existe);
            
            return ResponseEntity.ok(Map.of("existe", existe));
            
        } catch (Exception e) {
            System.err.println("Error al verificar cuestionario: " + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Error al verificar cuestionario: " + e.getMessage()
            ));
        }
    }
    
    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminarCuestionario() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            Optional<CuestionarioSalud> cuestionario = cuestionarioSaludService.obtenerCuestionarioPorUsuario(idUser);
            
            if (cuestionario.isPresent()) {
                cuestionarioSaludService.eliminarCuestionario(cuestionario.get().getIdCuestionario());
                return ResponseEntity.ok().body(Map.of("status", "success", "message", "Cuestionario eliminado correctamente"));
            } else {
                return ResponseEntity.badRequest().body(Map.of("status", "error", "message", "No existe cuestionario para eliminar"));
            }            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "Error al eliminar cuestionario: " + e.getMessage()
            ));
        }
    }
    
    @GetMapping("/resumen")
    public ResponseEntity<?> obtenerResumenCuestionario() {
        try {
            Long idUser = obtenerIdUsuarioAutenticado();
            Optional<CuestionarioSalud> cuestionario = cuestionarioSaludService.obtenerCuestionarioPorUsuario(idUser);
            
            if (cuestionario.isPresent()) {
                CuestionarioSalud c = cuestionario.get();
                Map<String, Object> resumen = new HashMap<>();
                
                resumen.put("primerEmbarazo", c.getPrimerEmbarazo());
                resumen.put("tieneCondiciones", tieneCondiciones(c));
                resumen.put("numeroEmbarazos", c.getNumeroEmbarazos());
                resumen.put("fechaRegistro", c.getFechaRegistro());
                
                return ResponseEntity.ok(resumen);
            } else {
                return ResponseEntity.ok().body(Map.of("message", "No hay cuestionario registrado"));
            }
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", "Error al obtener resumen: " + e.getMessage()
            ));
        }
    }
    
    private boolean tieneCondiciones(CuestionarioSalud c) {
        
        return c.getObesidad() != null && c.getObesidad() ||
               c.getSobrepeso() != null && c.getSobrepeso() ||
               c.getPresionArterialAlta() != null && c.getPresionArterialAlta() ||
               c.getDiabetesMellitus() != null && c.getDiabetesMellitus() ||
               c.getEnfermedadesPulmon() != null && c.getEnfermedadesPulmon() ||
               c.getEnfermedadesCorazon() != null && c.getEnfermedadesCorazon() ||
               c.getEnfermedadesHematologicas() != null && c.getEnfermedadesHematologicas() ||
               c.getEpilepsia() != null && c.getEpilepsia() ||
               c.getNefropatia() != null && c.getNefropatia() ||
               c.getEnfermedadesTransmisionSexual() != null && c.getEnfermedadesTransmisionSexual() ||
               c.getLupus() != null && c.getLupus() ||
               c.getDesnutricion() != null && c.getDesnutricion() ||
               c.getEnfermedadTiroidea() != null && c.getEnfermedadTiroidea() ||
               c.getOtrasEnfermedades() != null && c.getOtrasEnfermedades();
    }
}