package mx.edu.uacm.ws.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uacm.ws.Entity.ChatbotPregunta;
import mx.edu.uacm.ws.Repository.ChatbotPreguntaRepository;

@Service
public class ChatbotService {
    
    @Autowired
    private ChatbotPreguntaRepository preguntaRepository;
    
    public String obtenerRespuesta(String pregunta, String categoria) {
        String preguntaLower = pregunta.toLowerCase().trim();
        
        ChatbotPregunta.CategoriaPregunta categoriaEnum;
        try {
            categoriaEnum = ChatbotPregunta.CategoriaPregunta.valueOf(categoria.toUpperCase());
        } catch (IllegalArgumentException e) {
            categoriaEnum = ChatbotPregunta.CategoriaPregunta.GENERAL;
        }
        
        List<ChatbotPregunta> preguntasCategoria = preguntaRepository.findByCategoriaAndActivoTrue(categoriaEnum);
        Optional<ChatbotPregunta> coincidencia = encontrarMejorCoincidencia(preguntaLower, preguntasCategoria);
        
        if (coincidencia.isPresent()) {
            return coincidencia.get().getRespuesta();
        }
        
        List<ChatbotPregunta> todasPreguntas = preguntaRepository.findByActivoTrue();
        coincidencia = encontrarMejorCoincidencia(preguntaLower, todasPreguntas);
        
        return coincidencia.map(ChatbotPregunta::getRespuesta)
                .orElse("Gracias por tu consulta. Para información específica, te recomiendo consultar con un profesional de la salud.");
    }
    
    private Optional<ChatbotPregunta> encontrarMejorCoincidencia(String pregunta, List<ChatbotPregunta> preguntas) {
        return preguntas.stream()
                .filter(p -> {
                    if (pregunta.contains(p.getPreguntaPatron().toLowerCase())) {
                        return true;
                    }
                    
                    if (p.getPalabrasClave() != null) {
                        String[] palabras = pregunta.split("\\s+");
                        for (String palabra : palabras) {
                            if (palabra.length() > 3 && 
                                p.getPalabrasClave().toLowerCase().contains(palabra.toLowerCase())) {
                                return true;
                            }
                        }
                    }
                    
                    return false;
                })
                .findFirst();
    }
    
    public List<ChatbotPregunta> obtenerTodasPreguntasActivas() {
        return preguntaRepository.findByActivoTrue();
    }
}