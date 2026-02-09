package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ultrasonido")
public class Ultrasonido {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Ultrasonido")
    private Long idUltrasonido;

    @Column(name = "cita_ultrasonido")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate citaUltrasonido;

    @Column(name = "id_last")
    private Long idLast;
    
    public Ultrasonido() {}

	public Ultrasonido(Long idUltrasonido, LocalDate citaUltrasonido, Long idLast) {
		super();
		this.idUltrasonido = idUltrasonido;
		this.citaUltrasonido = citaUltrasonido;
		this.idLast = idLast;
	}

	public Long getIdUltrasonido() {
		return idUltrasonido;
	}

	public void setIdUltrasonido(Long idUltrasonido) {
		this.idUltrasonido = idUltrasonido;
	}

	public LocalDate getCitaUltrasonido() {
		return citaUltrasonido;
	}

	public void setCitaUltrasonido(LocalDate citaUltrasonido) {
		this.citaUltrasonido = citaUltrasonido;
	}

	public Long getIdLast() {
		return idLast;
	}

	public void setIdLast(Long idLast) {
		this.idLast = idLast;
	}

	@Override
	public String toString() {
		return "Ultrasonido [idUltrasonido=" + idUltrasonido + ", citaUltrasonido=" + citaUltrasonido + ", idLast="
				+ idLast + "]";
	}
    
    
}
