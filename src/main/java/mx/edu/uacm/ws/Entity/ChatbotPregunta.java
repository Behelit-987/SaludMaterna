package mx.edu.uacm.ws.Entity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "chatbot_preguntas")
public class ChatbotPregunta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaPregunta categoria;
    
    @Column(name = "pregunta_patron", nullable = false, length = 500)
    private String preguntaPatron;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String respuesta;
    
    @Column(name = "palabras_clave", length = 1000)
    private String palabrasClave; 
    
    @Column(nullable = false)
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    public enum CategoriaPregunta {
        EMBARAZO, PUERPERIO, GENERAL
    }
    
    public List<String> getPalabrasClaveList() {
        if (palabrasClave == null || palabrasClave.trim().isEmpty()) {
            return List.of();
        }
        return Arrays.asList(palabrasClave.split(","));
    }
    
    public void setPalabrasClaveList(List<String> palabras) {
        if (palabras == null || palabras.isEmpty()) {
            this.palabrasClave = null;
        } else {
            this.palabrasClave = String.join(",", palabras);
        }
    }
    
    public ChatbotPregunta() {}
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CategoriaPregunta getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaPregunta categoria) {
		this.categoria = categoria;
	}

	public String getPreguntaPatron() {
		return preguntaPatron;
	}

	public void setPreguntaPatron(String preguntaPatron) {
		this.preguntaPatron = preguntaPatron;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getPalabrasClave() {
		return palabrasClave;
	}

	public void setPalabrasClave(String palabrasClave) {
		this.palabrasClave = palabrasClave;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
}
