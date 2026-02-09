package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_vdrl")
public class ExamenVdrlEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Vdrl")
	private Long idVdrl;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_vdrl")
    private LocalDate fechaVdrl;
    
    @Column(name = "resultado_vdrl")
    private String resultadoVdrl;
    
    public ExamenVdrlEntity() {}

	public Long getIdVdrl() {
		return idVdrl;
	}

	public void setIdVdrl(Long idVdrl) {
		this.idVdrl = idVdrl;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaVdrl() {
		return fechaVdrl;
	}

	public void setFechaVdrl(LocalDate fechaVdrl) {
		this.fechaVdrl = fechaVdrl;
	}

	public String getResultadoVdrl() {
		return resultadoVdrl;
	}

	public void setResultadoVdrl(String resultadoVdrl) {
		this.resultadoVdrl = resultadoVdrl;
	}
    
    
}
