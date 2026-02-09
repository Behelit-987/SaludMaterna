package mx.edu.uacm.ws.Segurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/", 
                    "/chatbot", 
                    "/api/chatbot/**",
                    "/css/**", 
                    "/js/**", 
                    "/images/**", 
                    "/webjars/**",
                    "/login", 
                    "/login/**", 
                    "/registro", 
                    "/actualizar-password"
                ).permitAll()
                
                .requestMatchers(
                    "/api/v1/login",        // Login para Flutter
                    "/api/v1/registro",     // Registro para Flutter
                    "/api/v1/auth/**"       // Otros endpoints de auth para Flutter
                ).permitAll()
                .requestMatchers(
                        "/app-salud-materna/eliminar/**",
                        "/app-salud-materna/guardar",
                        "/app-salud-materna/todos-examenes"
                    ).authenticated()
                
                .requestMatchers("/app-infancia", "/app-infancia/**").authenticated()
                .requestMatchers("/app-edad-fertil", "/app-edad-fertil/**").authenticated()
                .requestMatchers("/app-pregestacional", "/app-pregestacional/**").authenticated()
                .requestMatchers(
                    "/app-salud-materna", 
                    "/app-salud-materna/**", 
                    "/api/citas/**", 
                    "/api/cuestionario/**",
                    "/api/examenes-laboratorio/**"
                ).authenticated()
                
                .requestMatchers(
                    "/api/v1/citas/**",                    // Mapeo par citas en Flutter
                    "/api/v1/cuestionario/**",             // Cuestionario para Flutter
                    "/api/v1/reporte-citas/**",            // Reportes para Flutter
                    "/api/v1/examenes-laboratorio/**",     // Exámenes para Flutter
                    "/api/v1/usuaria/**",                   // Datos de usuaria para Flutter
                    "/app-salud-materna/eliminar/**",  // ← AQUÍ ESTÁ EL FIX
                    "/app-salud-materna/guardar/**",   // ← También para guardar si es POST
                    "/app-salud-materna/todos-examenes/**" 
                ).authenticated()
                
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
            )
            
            .csrf(csrf -> csrf
            	    .ignoringRequestMatchers(
            	        "/api/chatbot/**",
            	        "/api/citas/**",
            	        "/api/cuestionario/**",
            	        "/api/reporte-citas/**",
            	        "/api/auth/**",
            	        "/api/v1/**", 
            	        "/app-salud-materna/eliminar/**",
            	        "/app-salud-materna/guardar/**",
            	        "/app-salud-materna/todos-examenes/**"
            	    )
            	);

        return http.build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(Arrays.asList(
            "http://localhost:8080",      // Spring Boot local
            "http://localhost:8081",      // Flutter Web
            "http://10.0.2.2:8080",      // Emulador Android
            "http://10.0.2.2:8081",      // Emulador Android Flutter Web
            "capacitor://localhost",      // Capacitor (Ionic/Flutter)
            "ionic://localhost"           // Ionic
        ));
        
        configuration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        configuration.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Content-Type",
            "Accept",
            "X-Requested-With",
            "Cache-Control",
            "X-CSRF-TOKEN",  
            "X-XSRF-TOKEN"
        ));
        configuration.setExposedHeaders(Arrays.asList(
            "Authorization",
            "Content-Disposition"
        ));
        
        configuration.setAllowCredentials(true);
        
        configuration.setMaxAge(3600L); // 1 hora
        
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(false); 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

/*

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        	.headers(headers -> headers.frameOptions(frame -> frame.sameOrigin()))
        	
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/chatbot", "/api/chatbot/**", 
                               "/css/**", "/js/**", "/images/**", "/webjars/**",
                               "/login", "/login/**", "/registro", "/actualizar-password"
                               //, "/EducacionMaterna/**"
                               ).permitAll()
                .requestMatchers("/app-infancia", "/app-infancia/**").authenticated()
                .requestMatchers("/app-edad-fertil", "/app-edad-fertil/**").authenticated()
                .requestMatchers("/app-pregestacional", "/app-pregestacional/**").authenticated()
                .requestMatchers("/app-salud-materna", "/app-salud-materna/**", "/api/citas/**", "/api/cuestionario/**"
                		, "/api/examenes-laboratorio/**").authenticated()
                .requestMatchers(
                        "/api/citas/**",
                        "/api/cuestionario/**",
                        "/api/reporte-citas/**",
                        "/api/examenes-laboratorio/**",
                        "/api/auth/**"
                    ).authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                //.defaultSuccessUrl("/App-mujer")
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // Solo esta línea de logout
            )
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/chatbot/**",
                        "/api/citas/**",
                        "/api/cuestionario/**",
                        "/api/reporte-citas/**"
                        )
            );

        return http.build();
    }
 */   
    
//}

