package mx.edu.uacm.ws.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "datos_consultaprenatal")
public class DatosConsultaPrenatal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_DC")
    private Long idDC;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_last", nullable = false)
    private LastMestruacion idLast;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_Prenatal")
    private ConsultaPrenatal idPrenatal;
    
    @Column(name = "Peso", nullable = false)
    private Double peso;
    
    @Column(name = "IMC", nullable = false)
    private Double imc;
    
    @Column(name = "Presion_Arterial")
    private String presionArterial;
    
    @Column(name = "fondo_uterino")
    private String fondoUterino;
    
    @Column(name = "Frecuencia_fetal")
    private String frecuenciaFetal;
    
    @Column(name = "medicamentos", columnDefinition = "TEXT")
    private String medicamentos;
    
    @Column(name = "datos_generales", columnDefinition = "TEXT")
    private String datosGenerales;
    
    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;
    
    public DatosConsultaPrenatal() {}
    
    
    
    public DatosConsultaPrenatal(Long idDC, LastMestruacion idLast, ConsultaPrenatal idPrenatal, Double peso,
			Double imc, String presionArterial, String fondoUterino, String frecuenciaFetal, String medicamentos,
			String datosGenerales, LocalDate fechaRegistro) {
		super();
		this.idDC = idDC;
		this.idLast = idLast;
		this.idPrenatal = idPrenatal;
		this.peso = peso;
		this.imc = imc;
		this.presionArterial = presionArterial;
		this.fondoUterino = fondoUterino;
		this.frecuenciaFetal = frecuenciaFetal;
		this.medicamentos = medicamentos;
		this.datosGenerales = datosGenerales;
		this.fechaRegistro = fechaRegistro;
	}


    public Long getIdDC() {
        return idDC;
    }

    public void setIdDC(Long idDC) {
        this.idDC = idDC;
    }
    
    public void setIdLast(LastMestruacion idLast) {
		this.idLast = idLast;
	}

	public void setIdPrenatal(ConsultaPrenatal idPrenatal) {
		this.idPrenatal = idPrenatal;
	}

	public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public String getPresionArterial() {
        return presionArterial;
    }

    public void setPresionArterial(String presionArterial) {
        this.presionArterial = presionArterial;
    }

    public String getFondoUterino() {
        return fondoUterino;
    }

    public void setFondoUterino(String fondoUterino) {
        this.fondoUterino = fondoUterino;
    }

    public String getFrecuenciaFetal() {
        return frecuenciaFetal;
    }

    public void setFrecuenciaFetal(String frecuenciaFetal) {
        this.frecuenciaFetal = frecuenciaFetal;
    }

    public String getMedicamentos() {
        return medicamentos;
    }

    public void setMedicamentos(String medicamentos) {
        this.medicamentos = medicamentos;
    }

    public String getDatosGenerales() {
        return datosGenerales;
    }

    public void setDatosGenerales(String datosGenerales) {
        this.datosGenerales = datosGenerales;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
    // Métodos auxiliares para acceder a IDs directamente
    public Long getIdLast() {
        return this.idLast != null ? this.idLast.getId_last() : null;
    }
    
    public Long getIdPrenatal() {
        return this.idPrenatal != null ? this.idPrenatal.getIdPrenatal() : null;
    }
    
    public void setIdLast(Long idLast) {
        // Este método solo para compatibilidad, idealmente usar setLastMestruacion()
        if (this.idLast == null) {
            this.idLast = new LastMestruacion();
        }
        this.idLast.setId_last(idLast);
    }
    
    public void setIdPrenatal(Long idPrenatal) {
        // Este método solo para compatibilidad, idealmente usar setConsultaPrenatal()
        if (this.idPrenatal == null) {
            this.idPrenatal = new ConsultaPrenatal();
        }
        this.idPrenatal.setIdPrenatal(idPrenatal);
    }
}