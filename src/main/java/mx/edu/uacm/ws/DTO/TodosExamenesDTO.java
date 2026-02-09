package mx.edu.uacm.ws.DTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class TodosExamenesDTO {
    private Long idUser;
    
    // Listas de registros para cada tipo de examen
    private List<ExamenRegistroDTO> glucosas;
    private List<ExamenRegistroDTO> biometricas;
    private List<ExamenRegistroDTO> egos;
    private List<ExamenRegistroDTO> urocultivos;
    private List<ExamenRegistroDTO> vdrls;
    private List<ExamenRegistroDTO> vihs;
    private List<ExamenRegistroDTO> creatininas;
    private List<ExamenRegistroDTO> acidos;
    private List<ExamenRegistroDTO> citologias;
    private List<OtroExamenDTO> otrosExamenes;
    
    // Clase interna para registro simple
    public static class ExamenRegistroDTO {
        private Long id;
        
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate fecha;
        private String resultado;
        
        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public LocalDate getFecha() { return fecha; }
        public void setFecha(LocalDate fecha) { this.fecha = fecha; }
        
        public String getResultado() { return resultado; }
        public void setResultado(String resultado) { this.resultado = resultado; }
    }
    
    public static class OtroExamenDTO {
        private Long id;
        private String tipoExamen;
        
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate fecha;
        private String resultado;
        
        // Getters y Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        
        public String getTipoExamen() { return tipoExamen; }
        public void setTipoExamen(String tipoExamen) { this.tipoExamen = tipoExamen; }
        
        public LocalDate getFecha() { return fecha; }
        public void setFecha(LocalDate fecha) { this.fecha = fecha; }
        
        public String getResultado() { return resultado; }
        public void setResultado(String resultado) { this.resultado = resultado; }
    }
    
    // Getters y Setters para todas las listas
    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }
    
    public List<ExamenRegistroDTO> getGlucosas() { return glucosas; }
    public void setGlucosas(List<ExamenRegistroDTO> glucosas) { this.glucosas = glucosas; }
    
    public List<ExamenRegistroDTO> getBiometricas() { return biometricas; }
    public void setBiometricas(List<ExamenRegistroDTO> biometricas) { this.biometricas = biometricas; }
    
    public List<ExamenRegistroDTO> getEgos() { return egos; }
    public void setEgos(List<ExamenRegistroDTO> egos) { this.egos = egos; }
    
    public List<ExamenRegistroDTO> getUrocultivos() { return urocultivos; }
    public void setUrocultivos(List<ExamenRegistroDTO> urocultivos) { this.urocultivos = urocultivos; }
    
    public List<ExamenRegistroDTO> getVdrls() { return vdrls; }
    public void setVdrls(List<ExamenRegistroDTO> vdrls) { this.vdrls = vdrls; }
    
    public List<ExamenRegistroDTO> getVihs() { return vihs; }
    public void setVihs(List<ExamenRegistroDTO> vihs) { this.vihs = vihs; }
    
    public List<ExamenRegistroDTO> getCreatininas() { return creatininas; }
    public void setCreatininas(List<ExamenRegistroDTO> creatininas) { this.creatininas = creatininas; }
    
    public List<ExamenRegistroDTO> getAcidos() { return acidos; }
    public void setAcidos(List<ExamenRegistroDTO> acidos) { this.acidos = acidos; }
    
    public List<ExamenRegistroDTO> getCitologias() { return citologias; }
    public void setCitologias(List<ExamenRegistroDTO> citologias) { this.citologias = citologias; }
    
    public List<OtroExamenDTO> getOtrosExamenes() { return otrosExamenes; }
    public void setOtrosExamenes(List<OtroExamenDTO> otrosExamenes) { this.otrosExamenes = otrosExamenes; }
}