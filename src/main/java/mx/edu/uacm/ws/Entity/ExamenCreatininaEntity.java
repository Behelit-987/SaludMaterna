package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_creatinina")
public class ExamenCreatininaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Creatinina")
	private Long idCretinina;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_creatinina")
    private LocalDate fechaCreatinina;
    
    @Column(name = "resultado_creatinina")
    private String resultadoCreatinina;
    
    public ExamenCreatininaEntity() {}

	public Long getIdCretinina() {
		return idCretinina;
	}

	public void setIdCretinina(Long idCretinina) {
		this.idCretinina = idCretinina;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaCreatinina() {
		return fechaCreatinina;
	}

	public void setFechaCreatinina(LocalDate fechaCreatinina) {
		this.fechaCreatinina = fechaCreatinina;
	}

	public String getResultadoCreatinina() {
		return resultadoCreatinina;
	}

	public void setResultadoCreatinina(String resultadoCreatinina) {
		this.resultadoCreatinina = resultadoCreatinina;
	}
    
    
}
