package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen_biometrica")
public class ExamenBiometricaEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_Biometrica")
	private Long idBiometrica;
	
    
    @Column(name = "fecha_biometrica")
    private LocalDate fechaBiometrica;
    
    @Column(name = "resultado_biometrica")
    private String resultadoBiometrica;
    
    @Column(name = "id_User")
    private Long idUser;
    
    public ExamenBiometricaEntity() {}

	public Long getIdBiometrica() {
		return idBiometrica;
	}

	public void setIdBiometrica(Long idBiometrica) {
		this.idBiometrica = idBiometrica;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getFechaBiometrica() {
		return fechaBiometrica;
	}

	public void setFechaBiometrica(LocalDate fechaBiometrica) {
		this.fechaBiometrica = fechaBiometrica;
	}

	public String getResultadoBiometrica() {
		return resultadoBiometrica;
	}

	public void setResultadoBiometrica(String resultadoBiometrica) {
		this.resultadoBiometrica = resultadoBiometrica;
	}
    
    

}
