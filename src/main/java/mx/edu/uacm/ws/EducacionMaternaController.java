package mx.edu.uacm.ws;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.UsuariaService;

@Controller
@RequestMapping("/app-salud-materna")
//@CrossOrigin(origins = "*")
public class EducacionMaternaController {
    
    @Autowired
    private UsuariaService usuariaService;
    
    @GetMapping("/educacion-materna")
    public String mostrarEducacionMaterna(Model model, Authentication authentication) {
    	if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            model.addAttribute("userEmail", email);
            
            Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
            usuariaOpt.ifPresent(usuaria -> {
                model.addAttribute("usuaria", usuaria);
                model.addAttribute("userId", usuaria.getIdUser());
            });
        }
        return "app-salud-materna/educacion-materna";
    }
}