package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "last_mestruacion")
public class LastMestruacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_last;

    @Column(name = "id_User")
    private Long idUser;

    @Column(name = "ult_mest")
    private LocalDate ultMest;

    public LastMestruacion() {}

    public LastMestruacion(Long idUser, LocalDate ultMest) {
        this.idUser = idUser;
        this.ultMest = ultMest;
    }

	public Long getId_last() {
		return id_last;
	}

	public void setId_last(Long id_last) {
		this.id_last = id_last;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public LocalDate getUltMest() {
		return ultMest;
	}

	public void setUltMest(LocalDate ultMest) {
		this.ultMest = ultMest;
	}

	@Override
	public String toString() {
		return "LastMestruacion [id_last=" + id_last + ", idUser=" + idUser + ", ultMest=" + ultMest + "]";
	}
    
    
    
}