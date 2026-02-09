package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_urocultivo")
public class ExamenUrocultivoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Urocultivo")
	private Long idUrocultivo;
	
    @Column(name = "id_User")
    private Long idUser;
    
    @Column(name = "fecha_urocultivo")
    private LocalDate fechaUrocultivo;
    
    @Column(name = "resultado_urocultivo")
    private String resultadoUrocultivo;
    
    public ExamenUrocultivoEntity() {}

	public Long getIdUrocultivo() {
		return idUrocultivo;
	}

	public void setIdUrocultivo(Long idUrocultivo) {
		this.idUrocultivo = idUrocultivo;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaUrocultivo() {
		return fechaUrocultivo;
	}

	public void setFechaUrocultivo(LocalDate fechaUrocultivo) {
		this.fechaUrocultivo = fechaUrocultivo;
	}

	public String getResultadoUrocultivo() {
		return resultadoUrocultivo;
	}

	public void setResultadoUrocultivo(String resultadoUrocultivo) {
		this.resultadoUrocultivo = resultadoUrocultivo;
	}
    
    

}
