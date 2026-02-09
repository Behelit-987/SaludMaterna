package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_acido")
public class ExamenAcidoEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Acido")
	private Long idAcido;
    
    @Column(name = "fecha_acido")
    private LocalDate fechaAcido;
    
    @Column(name = "resultado_acido")
    private String resultadoAcido;
    
    @Column(name = "id_User")
    private Long idUser;
    
    public ExamenAcidoEntity() {}

	public Long getIdAcido() {
		return idAcido;
	}

	public void setIdAcido(Long idAcido) {
		this.idAcido = idAcido;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaAcido() {
		return fechaAcido;
	}

	public void setFechaAcido(LocalDate fechaAcido) {
		this.fechaAcido = fechaAcido;
	}

	public String getResultadoAcido() {
		return resultadoAcido;
	}

	public void setResultadoAcido(String resultadoAcido) {
		this.resultadoAcido = resultadoAcido;
	}
    
    
}
