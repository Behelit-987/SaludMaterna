package mx.edu.uacm.ws;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.uacm.ws.Entity.ConocimientoGeneral;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.ConocimientoService;
import mx.edu.uacm.ws.Service.UsuariaService;

@Controller
@RequestMapping("/app-salud-materna")
public class ConocimientoController {
    
    @Autowired
    private ConocimientoService conocimientoService;
    
    @Autowired
    private UsuariaService usuariaService;
    
    @GetMapping("/perfil")
    public String mostrarPerfil(Authentication authentication, Model model) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        String email = authentication.getName();
        Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
        
        if (usuariaOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        Usuaria usuaria = usuariaOpt.get();
        
        ConocimientoService.PerfilDTO perfil = conocimientoService.getPerfilCompleto(usuaria.getIdUser());
        
        System.out.println("=== DEPURACIÓN PERFIL ===");
        System.out.println("Usuario ID: " + usuaria.getIdUser());
        System.out.println("Conocimiento encontrado: " + (perfil != null && perfil.getConocimiento() != null));
        
        if (perfil != null && perfil.getConocimiento() != null) {
            ConocimientoGeneral conocimiento = perfil.getConocimiento();
            System.out.println("Datos de Conocimiento:");
            System.out.println("  ID: " + conocimiento.getIdConocimiento());
            System.out.println("  Grupo Sanguíneo: " + conocimiento.getGrupoSanguineo());
            System.out.println("  RH: " + conocimiento.getRh());
            System.out.println("  Seguro Social: " + conocimiento.getSeguroSocial());
            System.out.println("  Otro Seguro: " + conocimiento.getOtroSeguro());
            System.out.println("  Num Afiliación: " + conocimiento.getNumAfiliacion());
            System.out.println("  Unidad Médica: " + conocimiento.getUnidadMedicaN1());
            System.out.println("  N1 Lugar: " + conocimiento.getN1Lugar());
            
            model.addAttribute("conocimiento", conocimiento);
        } else {
            System.out.println("No se encontró ConocimientoGeneral");
            model.addAttribute("conocimiento", null);
        }
        
        model.addAttribute("usuaria", usuaria);
        return "app-salud-materna/Perfil";
    }
    
    
    @PostMapping("/perfil/guardar")
    public String guardarPerfil(
            @RequestParam Long idUser,
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidos,
            @RequestParam(required = false) String fechaNacimiento,
            @RequestParam(required = false) String domicilio,
            @RequestParam(required = false) String curp,
            @RequestParam(required = false) String numCelular,
            @RequestParam(required = false) String numEmergencia,
            @RequestParam(required = false) Double talla,
            @RequestParam(required = false) Double peso,
            @RequestParam(required = false) Long conocimientoId,
            @RequestParam(required = false) String grupoSanguineo,
            @RequestParam(required = false) String rh,
            @RequestParam(required = false) String seguroSocial,
            @RequestParam(required = false) String otroSeguro,
            @RequestParam(required = false) String numAfiliacion,
            @RequestParam(required = false) String unidadMedicaN1,
            @RequestParam(required = false) String n1Lugar,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }
        
        try {
            String email = authentication.getName();
            Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
            if (usuariaOpt.isEmpty()) {
                return "redirect:/login";
            }
            
            Usuaria usuaria = usuariaOpt.get();
            
            if (!usuaria.getIdUser().equals(idUser)) {
                throw new SecurityException("No tienes permiso para modificar este perfil");
            }
            
            System.out.println("=== GUARDANDO DATOS ===");
            System.out.println("Grupo Sanguíneo recibido: " + grupoSanguineo);
            System.out.println("RH recibido: " + rh);
            System.out.println("Seguro Social recibido: " + seguroSocial);
            
            Usuaria usuariaActualizada = actualizarDatosUsuaria(usuaria, nombres, apellidos, 
                fechaNacimiento, domicilio, curp, numCelular, numEmergencia, talla, peso);
            
            usuariaService.actualizarUsuaria(usuariaActualizada);
            
            ConocimientoGeneral conocimiento = actualizarConocimientoGeneral(
                usuaria.getIdUser(), conocimientoId, grupoSanguineo, rh, 
                seguroSocial, otroSeguro, numAfiliacion, unidadMedicaN1, n1Lugar);
            
            ConocimientoGeneral conocimientoGuardado = conocimientoService.saveOrUpdate(conocimiento);
            
            System.out.println("=== DATOS GUARDADOS ===");
            System.out.println("Conocimiento ID: " + conocimientoGuardado.getIdConocimiento());
            System.out.println("Grupo Sanguíneo guardado: " + conocimientoGuardado.getGrupoSanguineo());
            System.out.println("RH guardado: " + conocimientoGuardado.getRh());
            System.out.println("Seguro Social guardado: " + conocimientoGuardado.getSeguroSocial());
            
            redirectAttributes.addFlashAttribute("success", "Perfil actualizado correctamente");
            
        } catch (Exception e) {
            e.printStackTrace(); 
            redirectAttributes.addFlashAttribute("error", "Error al guardar: " + e.getMessage());
            return "redirect:/app-salud-materna/perfil?error=true";
        }
        
        return "redirect:/app-salud-materna/perfil?success=true";
    }
    
    @GetMapping("/migrar-conocimiento")
    @ResponseBody
    public String migrarConocimiento(Authentication authentication) {
        if (authentication == null) {
            return "No autenticado";
        }
        
        String email = authentication.getName();
        Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
        
        if (usuariaOpt.isEmpty()) {
            return "Usuario no encontrado";
        }
        
        Usuaria usuaria = usuariaOpt.get();
        
        try {
            Optional<ConocimientoGeneral> conocimientoOpt = conocimientoService.findByUserId(usuaria.getIdUser());
            
            if (conocimientoOpt.isPresent()) {
                ConocimientoGeneral conocimiento = conocimientoOpt.get();
                
                conocimientoService.saveOrUpdate(conocimiento);
                
                return "Datos migrados exitosamente para el usuario: " + email;
            } else {
                return "No hay datos de conocimiento para migrar";
            }
        } catch (Exception e) {
            return "Error en migración: " + e.getMessage();
        }
    }
    
    private Usuaria actualizarDatosUsuaria(Usuaria usuaria, String nombres, String apellidos,
                                          String fechaNacimiento, String domicilio, String curp,
                                          String numCelular, String numEmergencia, 
                                          Double talla, Double peso) {
        
        if (nombres != null && !nombres.trim().isEmpty()) {
            usuaria.setNombres(nombres.trim());
        }
        
        if (apellidos != null && !apellidos.trim().isEmpty()) {
            usuaria.setApellidos(apellidos.trim());
        }
        
        if (fechaNacimiento != null && !fechaNacimiento.trim().isEmpty()) {
            usuaria.setFechaNacimiento(LocalDate.parse(fechaNacimiento));
        }
        
        if (domicilio != null && !domicilio.trim().isEmpty()) {
            usuaria.setDomicilio(domicilio.trim());
        }
        
        if (curp != null && !curp.trim().isEmpty()) {
            usuaria.setCurp(curp.trim());
        }
        
        if (numCelular != null && !numCelular.trim().isEmpty()) {
            usuaria.setNumCelular(numCelular.trim());
        }
        
        if (numEmergencia != null && !numEmergencia.trim().isEmpty()) {
            usuaria.setNumEmergencia(numEmergencia.trim());
        }
        
        if (talla != null && talla > 0) {
            usuaria.setTalla(talla);
        }
        
        if (peso != null && peso > 0) {
            usuaria.setPeso(peso);
        }
        
        return usuaria;
    }
    
    // Método auxiliar para actualizar ConocimientoGeneral
    private ConocimientoGeneral actualizarConocimientoGeneral(
            Long idUser, Long conocimientoId, String grupoSanguineo,
            String rh, String seguroSocial, String otroSeguro, 
            String numAfiliacion, String unidadMedicaN1, String n1Lugar) {
        
        ConocimientoGeneral conocimiento;
        
        // Si no hay ID o es 0, buscar por usuario o crear nuevo
        if (conocimientoId == null || conocimientoId == 0) {
            // Buscar si ya existe conocimiento para este usuario
            Optional<ConocimientoGeneral> conocimientoOpt = conocimientoService.findByUserId(idUser);
            
            if (conocimientoOpt.isPresent()) {
                conocimiento = conocimientoOpt.get();
            } else {
                conocimiento = new ConocimientoGeneral();
                conocimiento.setIdUser(idUser);
            }
        } else {
            // Buscar por ID
            Optional<ConocimientoGeneral> conocimientoOpt = conocimientoService.findById(conocimientoId);
            conocimiento = conocimientoOpt.orElse(new ConocimientoGeneral());
            conocimiento.setIdUser(idUser);
        }
        
        // Actualizar campos si tienen valor
        if (grupoSanguineo != null && !grupoSanguineo.isEmpty()) {
            try {
                conocimiento.setGrupoSanguineo(ConocimientoGeneral.Sanguineo.valueOf(grupoSanguineo));
            } catch (IllegalArgumentException e) {
                // Si el valor no es válido, dejar como null
                conocimiento.setGrupoSanguineo(null);
            }
        } else {
            conocimiento.setGrupoSanguineo(null);
        }
        
        if (rh != null && !rh.isEmpty()) {
            try {
                conocimiento.setRh(ConocimientoGeneral.RH.valueOf(rh));
            } catch (IllegalArgumentException e) {
                conocimiento.setRh(null);
            }
        } else {
            conocimiento.setRh(null);
        }
        
        if (seguroSocial != null && !seguroSocial.isEmpty()) {
            try {
                conocimiento.setSeguroSocial(ConocimientoGeneral.Seguro.valueOf(seguroSocial));
                
                // Si no es "Otra", limpiar el campo otroSeguro
                if (conocimiento.getSeguroSocial() != ConocimientoGeneral.Seguro.Otra) {
                    conocimiento.setOtroSeguro(null);
                } else if (otroSeguro != null && !otroSeguro.trim().isEmpty()) {
                    conocimiento.setOtroSeguro(otroSeguro.trim());
                }
            } catch (IllegalArgumentException e) {
                conocimiento.setSeguroSocial(null);
            }
        } else {
            conocimiento.setSeguroSocial(null);
        }
        
        if (numAfiliacion != null && !numAfiliacion.trim().isEmpty()) {
            conocimiento.setNumAfiliacion(numAfiliacion.trim());
        } else {
            conocimiento.setNumAfiliacion(null);
        }
        
        if (unidadMedicaN1 != null && !unidadMedicaN1.isEmpty()) {
            try {
                conocimiento.setUnidadMedicaN1(ConocimientoGeneral.UnidadMedica.valueOf(unidadMedicaN1));
                
                if (conocimiento.getUnidadMedicaN1() != ConocimientoGeneral.UnidadMedica.SI) {
                    conocimiento.setN1Lugar(null);
                } else if (n1Lugar != null && !n1Lugar.trim().isEmpty()) {
                    conocimiento.setN1Lugar(n1Lugar.trim());
                }
            } catch (IllegalArgumentException e) {
                conocimiento.setUnidadMedicaN1(null);
            }
        } else {
            conocimiento.setUnidadMedicaN1(null);
        }
        
        return conocimiento;
    }
}