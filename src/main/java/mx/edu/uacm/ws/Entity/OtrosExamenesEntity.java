package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "otros_examenes")
public class OtrosExamenesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Otros")
	private Long idOtros;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "tipo_examen")
    private String tipoExamen;
    
    @Column(name = "fecha_examen")
    private LocalDate fechaExamen;
    
    @Column(name = "resultado_examen")
    private String resultadoExamen;
    
    public OtrosExamenesEntity() {}

	public Long getIdOtros() {
		return idOtros;
	}

	public void setIdOtros(Long idOtros) {
		this.idOtros = idOtros;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getTipoExamen() {
		return tipoExamen;
	}

	public void setTipoExamen(String tipoExamen) {
		this.tipoExamen = tipoExamen;
	}

	public LocalDate getFechaExamen() {
		return fechaExamen;
	}

	public void setFechaExamen(LocalDate fechaExamen) {
		this.fechaExamen = fechaExamen;
	}

	public String getResultadoExamen() {
		return resultadoExamen;
	}

	public void setResultadoExamen(String resultadoExamen) {
		this.resultadoExamen = resultadoExamen;
	}
    
    
}
