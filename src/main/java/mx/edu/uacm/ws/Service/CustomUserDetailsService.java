package mx.edu.uacm.ws.Service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mx.edu.uacm.ws.Entity.Usuaria;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuariaService usuariaService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuaria usuaria = usuariaService.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
        
        int edad = calcularEdad(usuaria.getFechaNacimiento());
        String rol = determinarRolPorEdad(edad);

        return User.builder()
                .username(usuaria.getEmail())
                .password(usuaria.getPasswordHash())
                .roles(rol)
                .build();
    }
    
    private int calcularEdad (LocalDate fechaNacimiento) {
    	return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    private String determinarRolPorEdad(int edad) {
    	if(edad >= 4 && edad <= 11) {
    		return "INFANCIA";
    	} else if (edad >= 12 && edad <= 48) {
    		return "EDAD FERTIL";
    	} else {
    		return "USUARIA";
    	}
    }
}