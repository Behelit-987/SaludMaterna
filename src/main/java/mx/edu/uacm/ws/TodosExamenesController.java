package mx.edu.uacm.ws;

import mx.edu.uacm.ws.DTO.TodosExamenesDTO;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Service.TodosExamenesService;
import mx.edu.uacm.ws.Service.UsuariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/app-salud-materna")
public class TodosExamenesController {
    
    @Autowired
    private TodosExamenesService todosExamenesService;
    
    @Autowired
    private UsuariaService usuariaService;
    
    @GetMapping("/todos-examenes")
    public String mostrarTodosExamenes(Model model, Principal principal) {
        try {
            String email = principal.getName();
            Usuaria usuaria = usuariaService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            TodosExamenesDTO todosExamenes = todosExamenesService.obtenerTodosExamenesPorUsuario(usuaria.getIdUser());
            
            asegurarRegistroVacio(todosExamenes);
            
            model.addAttribute("usuaria", usuaria);
            model.addAttribute("todosExamenes", todosExamenes);
            
            return "app-salud-materna/todos-examenes";
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al cargar los exámenes: " + e.getMessage());
            return "app-salud-materna/todos-examenes";
        }
    }
    
    @PostMapping("/guardar")
    public String guardarTodosExamenes(
            @ModelAttribute TodosExamenesDTO todosExamenes,
            Principal principal,
            Model model) {
    	limpiarIdsInvalidos(todosExamenes);
        
        try {
            String email = principal.getName();
            Usuaria usuaria = usuariaService.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
            
            todosExamenes.setIdUser(usuaria.getIdUser());
            
            Map<String, Object> resultado = todosExamenesService.guardarTodosExamenes(todosExamenes);
            
            if ((Boolean) resultado.get("success")) {
                model.addAttribute("success", resultado.get("message"));
            } else {
                model.addAttribute("error", resultado.get("message"));
            }
            
            TodosExamenesDTO examenesActualizados = todosExamenesService.obtenerTodosExamenesPorUsuario(usuaria.getIdUser());
            asegurarRegistroVacio(examenesActualizados);
            
            model.addAttribute("usuaria", usuaria);
            model.addAttribute("todosExamenes", examenesActualizados);
            
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Error al guardar: " + e.getMessage());
        }
        
        return "app-salud-materna/todos-examenes";
    }
    
    @PostMapping("/eliminar/{tipo}/{id}")
    @ResponseBody
    public Map<String, Object> eliminarExamen(
            @PathVariable String tipo,
            @PathVariable Long id,
            Principal principal) {
        
        Map<String, Object> respuesta = new HashMap<>();
        
        try {
            todosExamenesService.eliminarRegistro(tipo, id);
            respuesta.put("success", true);
            respuesta.put("message", "Registro eliminado correctamente");
        } catch (Exception e) {
            respuesta.put("success", false);
            respuesta.put("message", "Error al eliminar: " + e.getMessage());
        }
        
        return respuesta;
    }
    
    private void asegurarRegistroVacio(TodosExamenesDTO dto) {
        if (dto.getGlucosas() == null || dto.getGlucosas().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setGlucosas(java.util.Arrays.asList(registro));
        }
        
        if (dto.getBiometricas() == null || dto.getBiometricas().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setBiometricas(java.util.Arrays.asList(registro));
        }
        
        if (dto.getEgos() == null || dto.getEgos().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setEgos(java.util.Arrays.asList(registro));
        }
        
        if (dto.getUrocultivos() == null || dto.getUrocultivos().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setUrocultivos(java.util.Arrays.asList(registro));
        }
        
        if (dto.getVdrls() == null || dto.getVdrls().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setVdrls(java.util.Arrays.asList(registro));
        }
        
        if (dto.getVihs() == null || dto.getVihs().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setVihs(java.util.Arrays.asList(registro));
        }
        
        if (dto.getCreatininas() == null || dto.getCreatininas().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setCreatininas(java.util.Arrays.asList(registro));
        }
        
        if (dto.getAcidos() == null || dto.getAcidos().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setAcidos(java.util.Arrays.asList(registro));
        }
        
        if (dto.getCitologias() == null || dto.getCitologias().isEmpty()) {
            TodosExamenesDTO.ExamenRegistroDTO registro = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setCitologias(java.util.Arrays.asList(registro));
        }
        
        if (dto.getOtrosExamenes() == null || dto.getOtrosExamenes().isEmpty()) {
            TodosExamenesDTO.OtroExamenDTO registro = new TodosExamenesDTO.OtroExamenDTO();
            dto.setOtrosExamenes(java.util.Arrays.asList(registro));
        }
    }
    
    private void limpiarIdsInvalidos(TodosExamenesDTO dto) {
        if (dto.getGlucosas() != null) {
            dto.getGlucosas().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        
        if (dto.getBiometricas() != null) {
            dto.getBiometricas().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getAcidos() != null) {
            dto.getAcidos().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getCitologias() != null) {
            dto.getCitologias().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getCreatininas() != null) {
            dto.getCreatininas().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getEgos() != null) {
            dto.getEgos().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getUrocultivos() != null) {
            dto.getUrocultivos().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getVdrls() != null) {
            dto.getVdrls().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getVihs() != null) {
            dto.getVihs().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
        if (dto.getOtrosExamenes() != null) {
            dto.getOtrosExamenes().forEach(registro -> {
                if (registro.getId() != null && registro.getId() <= 0) {
                    registro.setId(null);
                }
            });
        }
    }
}