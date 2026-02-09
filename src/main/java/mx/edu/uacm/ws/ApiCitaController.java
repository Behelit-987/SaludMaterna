package mx.edu.uacm.ws;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import mx.edu.uacm.ws.Entity.ConsultaPrenatal;
import mx.edu.uacm.ws.Entity.LastMestruacion;
import mx.edu.uacm.ws.Entity.Ultrasonido;
import mx.edu.uacm.ws.Service.UsuariaService;

@RestController
@RequestMapping("/api/v1/citas")
@CrossOrigin(origins = "*")
public class ApiCitaController {
    
    @Autowired
    private UsuariaService usuariaService;
    
    @PostMapping("/registrar-mestruacion")
    public ResponseEntity<?> registrarMestruacion(@RequestBody MestruacionRequest request) {
        try {
            usuariaService.registrarUltimaMestruacionYCalcularCitas(
                request.getUserId(), 
                request.getFechaMestruacion()
            );
            
            return ResponseEntity.ok(
                Map.of("success", true, "message", "Fecha registrada y citas calculadas")
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    
    @GetMapping("/mestruacion/{userId}")
    public ResponseEntity<?> obtenerMestruacion(@PathVariable Long userId) {
        try {
            var lastMestOpt = usuariaService.obtenerUltimaMestruacion(userId);
            
            if (lastMestOpt.isPresent()) {
                LastMestruacion lastMest = lastMestOpt.get();
                Map<String, Object> response = new HashMap<>();
                response.put("success", true);
                response.put("fechaMestruacion", lastMest.getUltMest());
                response.put("id", lastMest.getId_last());
                return ResponseEntity.ok(response);
            }
            
            return ResponseEntity.ok(
                Map.of("success", true, "message", "No hay fecha registrada")
            );
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    
    @GetMapping("/consultas/{userId}")
    public ResponseEntity<?> obtenerConsultas(@PathVariable Long userId) {
        try {
            List<ConsultaPrenatal> consultas = usuariaService.obtenerConsultasPrenatales(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("consultas", consultas);
            response.put("total", consultas.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    
    @GetMapping("/ultrasonidos/{userId}")
    public ResponseEntity<?> obtenerUltrasonidos(@PathVariable Long userId) {
        try {
            List<Ultrasonido> ultrasonidos = usuariaService.obtenerUltrasonidos(userId);
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("ultrasonidos", ultrasonidos);
            response.put("total", ultrasonidos.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    
    @PostMapping("/confirmar")
    public ResponseEntity<?> confirmarCita(@RequestBody ConfirmarCitaRequest request) {
        try {
            var resultado = usuariaService.confirmarCita(
                request.getUserId(),
                request.getTipoCita(),
                request.getRangoSemanas(),
                request.getFechaSeleccionada()
            );
            
            resultado.put("success", true);
            return ResponseEntity.ok(resultado);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    
    @GetMapping("/estado/{userId}")
    public ResponseEntity<?> obtenerEstadoCitas(@PathVariable Long userId) {
        try {
            var estadoCitas = usuariaService.obtenerEstadoCitasConfirmadas(userId);
            estadoCitas.put("success", true);
            return ResponseEntity.ok(estadoCitas);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                Map.of("success", false, "message", e.getMessage())
            );
        }
    }
    
    
    public static class MestruacionRequest {
        private Long userId;
        private LocalDate fechaMestruacion;
        
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public LocalDate getFechaMestruacion() { return fechaMestruacion; }
        public void setFechaMestruacion(LocalDate fechaMestruacion) { 
            this.fechaMestruacion = fechaMestruacion; 
        }
    }
    
    public static class ConfirmarCitaRequest {
        private Long userId;
        private String tipoCita; 
        private String rangoSemanas;
        private LocalDate fechaSeleccionada;
        
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getTipoCita() { return tipoCita; }
        public void setTipoCita(String tipoCita) { this.tipoCita = tipoCita; }
        public String getRangoSemanas() { return rangoSemanas; }
        public void setRangoSemanas(String rangoSemanas) { this.rangoSemanas = rangoSemanas; }
        public LocalDate getFechaSeleccionada() { return fechaSeleccionada; }
        public void setFechaSeleccionada(LocalDate fechaSeleccionada) { 
            this.fechaSeleccionada = fechaSeleccionada; 
        }
    }
}