package mx.edu.uacm.ws;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.edu.uacm.ws.Entity.ExamenesLaboratorio;
import mx.edu.uacm.ws.Entity.ExamenesLaboratorio.Respuesta;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.ExamenesLaboratorioService;
import mx.edu.uacm.ws.Service.UsuariaService;

@Controller
@RequestMapping("/app-salud-materna")
//@RequestMapping("/api/examenes-laboratorio/")
//@CrossOrigin(origins = "*")
public class ExamenesLaboratorioController {
    
    @Autowired
    private ExamenesLaboratorioService examenesService;
    
    @Autowired
    private UsuariaService usuariaService;
    
    @GetMapping("/examenes-laboratorio")
    public String mostrarExamenesLaboratorio(Model model, Principal principal) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            System.out.println("DEBUG: Email autenticado: " + email);
            
            Usuaria usuaria = usuariaService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            //System.out.println("DEBUG: Usuaria encontrada - ID: " + usuaria.getIdUser());
            
            List<ExamenesLaboratorio> examenesList = examenesService.obtenerExamenesPorUsuariaId(usuaria.getIdUser());
            System.out.println("DEBUG: Número de exámenes encontrados: " + examenesList.size());
            
            ExamenesLaboratorio examenes;
            
            if (!examenesList.isEmpty()) {
                examenes = examenesList.get(0);
                /*
                System.out.println("DEBUG: Examen encontrado - ID: " + examenes.getIdExamenes());
                System.out.println("DEBUG: Glucosa: " + examenes.getGlucosaAyuno());
                System.out.println("DEBUG: Fecha Glucosa: " + examenes.getFechaGlucosa());
                System.out.println("DEBUG: Resultado Glucosa: " + examenes.getResultadoGlucosa());
                */
            } else {
                System.out.println("DEBUG: No se encontraron exámenes, creando nuevo");
                examenes = examenesService.crearExamenesDefault(usuaria.getIdUser());
            }
            
            model.addAttribute("usuaria", usuaria);
            model.addAttribute("examenes", examenes);
            
            return "app-salud-materna/examenes-laboratorio";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar los exámenes: " + e.getMessage());
            return "app-salud-materna/examenes-laboratorio";
        }
    }
    
    @PostMapping("/examenes-laboratorio/guardar")
    public String guardarExamenesLaboratorio(
            @ModelAttribute("examenes") ExamenesLaboratorio examenes,
            @RequestParam("idUsuaria") Long idUsuaria,
            Principal principal,
            RedirectAttributes redirectAttributes) {
        
        try {
        	/*
            System.out.println("DEBUG: Guardando exámenes - ID Examen: " + examenes.getIdExamenes());
            System.out.println("DEBUG: Para usuaria ID: " + idUsuaria);
            */
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Usuaria usuaria = usuariaService.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            if (!usuaria.getIdUser().equals(idUsuaria)) {
                redirectAttributes.addFlashAttribute("error", "Acceso no autorizado");
                return "redirect:/app-salud-materna/examenes-laboratorio";
            }
            
            examenes.setIdUser(idUsuaria);
            procesarFechas(examenes);
            ExamenesLaboratorio guardado = examenesService.guardarExamenes(examenes);
            //System.out.println("DEBUG: Examen guardado con ID: " + guardado.getIdExamenes());
            
            redirectAttributes.addFlashAttribute("success", "Exámenes guardados correctamente");
            redirectAttributes.addFlashAttribute("examenes", guardado);
            
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al guardar los exámenes: " + e.getMessage());
        }
        
        return "redirect:/app-salud-materna/examenes-laboratorio";
    }
    
    private void procesarFechas(ExamenesLaboratorio examenes) {
        if (examenes.getGlucosaAyuno() == Respuesta.no) {
            examenes.setFechaGlucosa(null);
            examenes.setResultadoGlucosa(null);
        }
        if (examenes.getBiometricaHematica() == Respuesta.no) {
            examenes.setFechaBiometrica(null);
            examenes.setResultadoBiometrica(null);
        }
        if (examenes.getEgo() == Respuesta.no) {
            examenes.setFechaEgo(null);
            examenes.setResultadoEgo(null);
        }
        if (examenes.getUrocultivo() == Respuesta.no) {
            examenes.setFechaUrocultivo(null);
            examenes.setResultadoUrocultivo(null);
        }
        if (examenes.getVdrl() == Respuesta.no) {
            examenes.setFechaVdrl(null);
            examenes.setResultadoVdrl(null);
        }
        if (examenes.getVihHepatitisB() == Respuesta.no) {
            examenes.setFechaVih(null);
            examenes.setResultadoVih(null);
        }
        if (examenes.getCreatinina() == Respuesta.no) {
            examenes.setFechaCreatinina(null);
            examenes.setResultadoCreatinina(null);
        }
        if (examenes.getAcidoUrico() == Respuesta.no) {
            examenes.setFechaAcido(null);
            examenes.setResultadoAcido(null);
        }
        if (examenes.getCitologiaCervicovaginal() == Respuesta.no) {
            examenes.setFechaCitologia(null);
            examenes.setResultadoCitologia(null);
        }
    }
}

