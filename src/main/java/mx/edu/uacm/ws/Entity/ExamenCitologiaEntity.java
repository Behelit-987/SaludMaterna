package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_citologia")
public class ExamenCitologiaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Citologia")
	private Long idCitologia;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_citologia")
    private LocalDate fechaCitologia;
    
    @Column(name = "resultado_citologia")
    private String resultadoCitologia;
    
    public ExamenCitologiaEntity() {}

	public Long getIdCitologia() {
		return idCitologia;
	}

	public void setIdCitologia(Long idCitologia) {
		this.idCitologia = idCitologia;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaCitologia() {
		return fechaCitologia;
	}

	public void setFechaCitologia(LocalDate fechaCitologia) {
		this.fechaCitologia = fechaCitologia;
	}

	public String getResultadoCitologia() {
		return resultadoCitologia;
	}

	public void setResultadoCitologia(String resultadoCitologia) {
		this.resultadoCitologia = resultadoCitologia;
	}
    
    
}
