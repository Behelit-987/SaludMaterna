package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_glucosa")
public class ExamenGlucosaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Glucosa")
	private Long idGlucosa;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_glucosa")
    private LocalDate fechaGlucosa;
    
    @Column(name = "resultado_glucosa")
    private String resultadoGlucosa;
    
    public ExamenGlucosaEntity() {}

	public Long getIdGlucosa() {
		return idGlucosa;
	}

	public void setIdGlucosa(Long idGlucosa) {
		this.idGlucosa = idGlucosa;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaGlucosa() {
		return fechaGlucosa;
	}

	public void setFechaGlucosa(LocalDate fechaGlucosa) {
		this.fechaGlucosa = fechaGlucosa;
	}

	public String getResultadoGlucosa() {
		return resultadoGlucosa;
	}

	public void setResultadoGlucosa(String resultadoGlucosa) {
		this.resultadoGlucosa = resultadoGlucosa;
	}
    
    
    
}
