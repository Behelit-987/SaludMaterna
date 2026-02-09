package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuaria")
public class Usuaria {
	@Id
	/*
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_User", columnDefinition = "INT UNSIGNED")
    private Long idUser;
    */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_User")
    private Long idUser;
	
	@Column(name = "nombres", nullable = false)
    private String nombres;
    
    @Column(name = "apellidos", nullable = false)
    private String apellidos;
    
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;
    
    @Column(name = "domicilio", nullable = false)
    private String domicilio;
    
    @Column(name = "curp", nullable = false)
    private String curp;

	@Column(name = "Num_celular", nullable = false)
    private String numCelular;
    
    @Column(name = "Num_emergencia", nullable = false)
    private String numEmergencia;
    
    @Column(name = "talla", nullable = false)
    private Double talla;
    
    @Column(name = "peso", nullable = false)
    private Double peso;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "activo")
    private Boolean activo = true;
    
    public Usuaria() {}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	
	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNumCelular() {
		return numCelular;
	}

	public void setNumCelular(String numCelular) {
		this.numCelular = numCelular;
	}

	public String getNumEmergencia() {
		return numEmergencia;
	}

	public void setNumEmergencia(String numEmergencia) {
		this.numEmergencia = numEmergencia;
	}

	public Double getTalla() {
		return talla;
	}

	public void setTalla(Double talla) {
		this.talla = talla;
	}

	public Double getPeso() {
		return peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	public String getNombreCompleto() {
        if (this.nombres == null && this.apellidos == null) {
            return "Nombre no disponible";
        }
        return (this.nombres != null ? this.nombres : "") + 
               (this.apellidos != null ? " " + this.apellidos : "");
    }

	@Override
	public String toString() {
		return "Usuaria [idUser=" + idUser + ", nombres=" + nombres + ", apellidos=" + apellidos + ", fechaNacimiento="
				+ fechaNacimiento + ", domicilio=" + domicilio + ", curp=" + curp + ", numCelular=" + numCelular
				+ ", numEmergencia=" + numEmergencia + ", talla=" + talla + ", peso=" + peso + ", email=" + email
				+ ", passwordHash=" + passwordHash + ", fechaRegistro=" + fechaRegistro + ", activo=" + activo + "]";
	}

	
    
}
