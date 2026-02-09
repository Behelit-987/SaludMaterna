package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_vih")
public class ExamenVihEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Vih")
	private Long idVih;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_vih")
    private LocalDate fechaVih;
    
    @Column(name = "resultado_vih")
    private String resultadoVih;
    
    public ExamenVihEntity() {}

	public Long getIdVih() {
		return idVih;
	}

	public void setIdVih(Long idVih) {
		this.idVih = idVih;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaVih() {
		return fechaVih;
	}

	public void setFechaVih(LocalDate fechaVih) {
		this.fechaVih = fechaVih;
	}

	public String getResultadoVih() {
		return resultadoVih;
	}

	public void setResultadoVih(String resultadoVih) {
		this.resultadoVih = resultadoVih;
	}
    
    
}
