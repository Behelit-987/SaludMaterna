package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_ego")
public class ExamenEgoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Ego")
	private Long idEgo;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_ego")
    private LocalDate fechaEgo;
    
    @Column(name = "resultado_ego")
    private String resultadoEgo;
    
    public ExamenEgoEntity() {}

	public Long getIdEgo() {
		return idEgo;
	}

	public void setIdEgo(Long idEgo) {
		this.idEgo = idEgo;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaEgo() {
		return fechaEgo;
	}

	public void setFechaEgo(LocalDate fechaEgo) {
		this.fechaEgo = fechaEgo;
	}

	public String getResultadoEgo() {
		return resultadoEgo;
	}

	public void setResultadoEgo(String resultadoEgo) {
		this.resultadoEgo = resultadoEgo;
	}
    
    

}
