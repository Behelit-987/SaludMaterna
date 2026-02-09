package mx.edu.uacm.ws.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conocimiento_general")
public class ConocimientoGeneral {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conocimiento")
    private Long idConocimiento;
    
    @Column(name = "id_User")
    private Long idUser;
    
    public enum Sanguineo {
        O, A, B, AB, NO_SE  
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "grupo_sanguineo")
    private Sanguineo grupoSanguineo;
    
    public enum RH {
        Positivo, Negativo
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "rh")
    private RH rh;
    
    public enum Seguro {
        Ninguna, IMSS, ISSSTE, IMSS_Bienestar, Otra
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "seguro_social")
    private Seguro seguroSocial;
    
    @Column(name = "otro_seguro")
    private String otroSeguro;
    
    @Column(name = "num_afiliacion")
    private String numAfiliacion;
    
    public enum UnidadMedica {
        SI, NO
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medica_n1")
    private UnidadMedica unidadMedicaN1;
    
    @Column(name = "n1_lugar")
    private String n1Lugar;
    
    public ConocimientoGeneral() {}
    
    public Long getIdConocimiento() {
        return idConocimiento;
    }
    
    public void setIdConocimiento(Long idConocimiento) {
        this.idConocimiento = idConocimiento;
    }
    
    public Long getIdUser() {
        return idUser;
    }
    
    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
    
    public Sanguineo getGrupoSanguineo() {
        return grupoSanguineo;
    }
    
    public void setGrupoSanguineo(Sanguineo grupoSanguineo) {
        this.grupoSanguineo = grupoSanguineo;
    }
    
    public RH getRh() {
        return rh;
    }
    
    public void setRh(RH rh) {
        this.rh = rh;
    }
    
    public Seguro getSeguroSocial() {
        return seguroSocial;
    }
    
    public void setSeguroSocial(Seguro seguroSocial) {
        this.seguroSocial = seguroSocial;
    }
    
    public String getOtroSeguro() {
        return otroSeguro;
    }
    
    public void setOtroSeguro(String otroSeguro) {
        this.otroSeguro = otroSeguro;
    }
    
    public String getNumAfiliacion() {
        return numAfiliacion;
    }
    
    public void setNumAfiliacion(String numAfiliacion) {
        this.numAfiliacion = numAfiliacion;
    }
    
    public UnidadMedica getUnidadMedicaN1() {
        return unidadMedicaN1;
    }
    
    public void setUnidadMedicaN1(UnidadMedica unidadMedicaN1) {
        this.unidadMedicaN1 = unidadMedicaN1;
    }
    
    public String getN1Lugar() {
        return n1Lugar;
    }
    
    public void setN1Lugar(String n1Lugar) {
        this.n1Lugar = n1Lugar;
    }
    
    
}