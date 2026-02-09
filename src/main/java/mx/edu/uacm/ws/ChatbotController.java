package mx.edu.uacm.ws;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import mx.edu.uacm.ws.Service.ChatbotService;

@RestController
public class ChatbotController {
    
    @Autowired
    private ChatbotService chatbotService;
    
    @GetMapping("/chatbot")
    public String mostrarChatbot(Model model) {
        return "chatbot/chatbot";
    }
    
    @PostMapping("/api/chatbot/responder")
    @ResponseBody
    public Map<String, String> responderPregunta(
            @RequestParam String pregunta,
            @RequestParam String categoria) {
        
        String respuesta = chatbotService.obtenerRespuesta(pregunta, categoria);
        
        Map<String, String> response = new HashMap<>();
        response.put("respuesta", respuesta);
        
        return response;
    }
}
