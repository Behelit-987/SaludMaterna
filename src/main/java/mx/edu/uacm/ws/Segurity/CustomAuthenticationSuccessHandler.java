package mx.edu.uacm.ws.Segurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.UsuariaService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuariaService usuariaService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {
        
        String email = authentication.getName();
        Usuaria usuaria = usuariaService.findByEmail(email).orElse(null);
        
        if (usuaria != null) {
            int edad = calcularEdad(usuaria.getFechaNacimiento());
            System.out.println("Usuario: " + email + " - Edad: " + edad + " - Redirigiendo...");
            
            String redirectURL = determinarRedireccionPorEdad(edad);
            System.out.println("Redirigiendo a: " + redirectURL);
            
            response.sendRedirect(request.getContextPath() + redirectURL);
        } else {
            response.sendRedirect(request.getContextPath() + "/app-edad-fertil");
        }
    }
    
    private String determinarRedireccionPorEdad(int edad) {
        if (edad >= 4 && edad <= 11) {
            return "/app-infancia";
        } else if (edad >= 12 && edad <= 48) {
            return "/app-edad-fertil";  
        } else {
            return "/app-edad-fertil"; 
        }
    }
    
    private int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
}