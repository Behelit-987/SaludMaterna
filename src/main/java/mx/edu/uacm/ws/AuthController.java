package mx.edu.uacm.ws;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.UsuariaService;

@Controller
public class AuthController {

    @Autowired
    private UsuariaService usuariaService;
    
    /*
    @GetMapping("/")
    public String home() {
        return "redirect:/app-salud-materna";
    }
    */
    
    @GetMapping("/")
    public String home(Authentication authentication) {
    	if(authentication != null && authentication.isAuthenticated()) {
    		return determinarRedireccionPorEdad(authentication.getName());
    	}
    	return "redirect:/login";
    }
    
    //determinarRedireccionPorEdad

    @GetMapping("/login")
    public String showLoginPage() {
        return "login/login";
    }

    @GetMapping("/login/registro")
    public String showRegisterPage(Model model) {
        model.addAttribute("usuaria", new Usuaria());
        return "login/registro";
    }

    @PostMapping("/registro")
    public String registerUser(@ModelAttribute Usuaria usuaria, Model model) {
        try {
            if (usuariaService.existeEmail(usuaria.getEmail())) {
                model.addAttribute("error", "El email ya está registrado");
                return "login/registro";
            }
            
            usuariaService.registrarUsuaria(usuaria);
            return "redirect:/login?registro=true";
            
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "login/registro";
        }
    }
    
    /*
    @GetMapping("/app-salud-materna")
    public String showDashboard(Authentication authentication, Model model) {
        String email = authentication.getName();
        Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
        model.addAttribute("usuaria", usuaria);
        return "app-salud-materna/index";
    }
    */
    
    private int calcularEdad(LocalDate fechaNacimiento) {
    	return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    private String determinarRedireccionPorEdad(String email) {
    	Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
    	if(usuaria != null) {
    		int edad = calcularEdad(usuaria.getFechaNacimiento());
    		
    		if(edad >= 4 && edad <= 11) {
    			return "redirect:/app-infancia";
    		} else if (edad >= 12 && edad <= 48) {
    			return "redirect:/app-salud-materna";
    		}
    	}
    	return "redirect:/login";
    }
    
    @GetMapping("/app-infancia")
    public String showAppInfancia(Authentication authetication, Model model) {
    	String email = authetication.getName();
    	Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
    	model.addAttribute("usuaria", usuaria);
    	
    	int edad = calcularEdad(usuaria.getFechaNacimiento());
    	if (edad < 4 || edad > 11) {
    		return determinarRedireccionPorEdad(email);
    	}
    	return "app-infancia/index";
    }
    
    @GetMapping("/app-edad-fertil")
    public String showAppEdadFertil(Authentication authentication, Model model) {
    	String email = authentication.getName();
    	Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
    	model.addAttribute("usuaria", usuaria);
    	
    	int edad = calcularEdad(usuaria.getFechaNacimiento());
    	if (edad < 12 || edad > 48) {
    		return determinarRedireccionPorEdad(email);
    	}
    	return "app-edad-fertil/index";
    }
    
    @GetMapping("/app-salud-materna")
    public String showAppSaludMaterna(Authentication authentication, Model model) {
    	String email = authentication.getName();
    	Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
    	model.addAttribute("usuaria", usuaria);
    	
    	int edad = calcularEdad(usuaria.getFechaNacimiento());
    	if (edad < 12 || edad > 48) {
    		return determinarRedireccionPorEdad(email);
    	}
    	return "app-salud-materna/index";
    }
    
    @GetMapping("/login/actualizar-password")
    public String showUpdatePasswordPage() {
        return "login/actualizar-password";
    }

    @PostMapping("/actualizar-password")
    public String updatePassword(
            @RequestParam String email,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento,
            @RequestParam String nuevaPassword,
            @RequestParam String confirmarPassword,
            Model model) {
        
        try {
            if (!nuevaPassword.equals(confirmarPassword)) {
                model.addAttribute("error", "Las nuevas contraseñas no coinciden");
                return "login/actualizar-password";
            }

            boolean actualizado = usuariaService.actualizarPasswordConFechaNacimiento(
                email, fechaNacimiento, nuevaPassword);
            
            if (actualizado) {
                return "redirect:/login?passwordUpdated=true";
            } else {
                model.addAttribute("error", "Email o fecha de nacimiento incorrectos");
                return "login/actualizar-password";
            }
            
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "login/actualizar-password";
        }
    }
    
    /*
    @GetMapping("/app-salud-materna/citas")
    public String showCitasPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
        model.addAttribute("usuaria", usuaria);
        return "app-salud-materna/citas";
    }
    */
    
    /*
    @GetMapping("/app-salud-materna/citas")
    public String showCitasPage(Model model) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal())) {
            
            String email = authentication.getName();
            Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
            model.addAttribute("usuaria", usuaria);
        } else {
            // Si no está autenticado, redirigir al login
            return "redirect:/login";
        }
        
        return "app-salud-materna/citas";
    }
    */
    
    @GetMapping("/app-salud-materna/citas")
    public String showCitasPage(Model model) {
        System.out.println("Intentando cargar citas.html");
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated() && 
            !"anonymousUser".equals(authentication.getPrincipal())) {
            
            String email = authentication.getName();
            Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
            model.addAttribute("usuaria", usuaria);
            System.out.println("Usuario autenticado: " + email);
        } else {
            System.out.println("Usuario no autenticado, redirigiendo a login");
            return "redirect:/login";
        }
        
        System.out.println("Retornando: app-salud-materna/citas");
        return "app-salud-materna/citas";
    }
    
    
    
    @GetMapping("/reporte-citas")
    public String mostrarReporteCitas(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            model.addAttribute("userEmail", email);
            
            Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
            usuariaOpt.ifPresent(usuaria -> {
                model.addAttribute("usuaria", usuaria);
                model.addAttribute("userId", usuaria.getIdUser());
            });
        }
        
        return "app-salud-materna/reporte-citas";
    }
    
    @GetMapping("/app-salud-materna/examenes-laboratorios")
    public String examenesLaboratorio(Model model, Authentication authentication) {
    	if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            model.addAttribute("userEmail", email);
            
            Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
            usuariaOpt.ifPresent(usuaria -> {
                model.addAttribute("usuaria", usuaria);
                model.addAttribute("userId", usuaria.getIdUser());
            });
        }
    	return "app-salud-materna/examenes-laboratorio";
    }
    
    @GetMapping("/conocimiento-general")
    public String conocimientoGeneral(Model model, Authentication authentication) {
    	if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            model.addAttribute("userEmail", email);
            
            Optional<Usuaria> usuariaOpt = usuariaService.findByEmail(email);
            usuariaOpt.ifPresent(usuaria -> {
                model.addAttribute("usuaria", usuaria);
                model.addAttribute("userId", usuaria.getIdUser());
            });
        }
    	return "app-salud-materna/Perfil";
    }
    
}


