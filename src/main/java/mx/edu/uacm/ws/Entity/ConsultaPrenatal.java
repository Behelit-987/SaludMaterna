package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ConsultaPrenatales")
public class ConsultaPrenatal {
    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Prenatal", columnDefinition = "INT UNSIGNED")
    private Long idPrenatal;
    
    @Column(name = "fecha_consulta", nullable = false)
    private LocalDate fechaConsulta;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_last", nullable = false, columnDefinition = "INT UNSIGNED")
    private LastMestruacion lastMestruacion;
    */
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Prenatal")
    private Long idPrenatal;

    @Column(name = "fecha_consulta")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaConsulta;
    
    /*
    @Column(name = "id_last")
    private Long idLast;
    */
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_last", nullable = false)
    private LastMestruacion idLast;
    
    public ConsultaPrenatal() {}

	public ConsultaPrenatal(Long idPrenatal, LocalDate fechaConsulta, LastMestruacion idLast) {
		super();
		this.idPrenatal = idPrenatal;
		this.fechaConsulta = fechaConsulta;
		this.idLast = idLast;
	}

	public Long getIdPrenatal() {
		return idPrenatal;
	}

	public void setIdPrenatal(Long idPrenatal) {
		this.idPrenatal = idPrenatal;
	}

	public LocalDate getFechaConsulta() {
		return fechaConsulta;
	}

	public void setFechaConsulta(LocalDate fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}
	
	public LastMestruacion getIdLast() {
		return idLast;
	}

	public void setIdLast(LastMestruacion idLast) {
		this.idLast = idLast;
	}

	public Long getIdLastNumerico() {
        return this.idLast != null ? this.idLast.getId_last() : null;
    }

	@Override
	public String toString() {
		return "ConsultaPrenatal [idPrenatal=" + idPrenatal + ", fechaConsulta=" + fechaConsulta + ", idLast=" + idLast
				+ "]";
	}
	
	
    
}
