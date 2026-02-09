package mx.edu.uacm.ws.Entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "examenes_laboratorio")
public class ExamenesLaboratorio {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_examenes")
    private Integer idExamenes;
	
	@Column(name = "id_User", nullable = false)
	private Long idUser;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "glucosa_ayuno")
    private Respuesta glucosaAyuno;
	
	@Column(name = "fecha_glucosa")
	private LocalDate fechaGlucosa;
	
	@Column(name = "resultado_glucosa")
	private String resultadoGlucosa;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "biometrica_hematica")
    private Respuesta biometricaHematica;
	
	@Column(name = "fecha_biometrica")
	private LocalDate fechaBiometrica;
	
	@Column(name = "resultado_biometrica")
	private String resultadoBiometrica;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "ego")
    private Respuesta ego;
	
	@Column(name = "fecha_ego")
	private LocalDate fechaEgo;
	
	@Column(name = "resultado_ego")
	private String resultadoEgo;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "urocultivo")
    private Respuesta urocultivo;
	
	@Column(name = "fecha_urocultivo")
	private LocalDate fechaUrocultivo;
	
	@Column(name = "resultado_urocultivo")
	private String resultadoUrocultivo;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "vdrl")
    private Respuesta vdrl;
	
	@Column(name = "fecha_vdrl")
	private LocalDate fechaVdrl;
	
	@Column(name = "resultado_vdrl")
	private String resultadoVdrl;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "vih_hepatitisB")
    private Respuesta vihHepatitisB;
	
	@Column(name = "fecha_vih")
	private LocalDate fechaVih;
	
	@Column(name = "resultado_vih")
	private String resultadoVih;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "creatinina")
    private Respuesta creatinina;
	
	@Column(name = "fecha_creatinina")
	private LocalDate fechaCreatinina;
	
	@Column(name = "resultado_creatinina")
	private String resultadoCreatinina;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "acido_urico")
    private Respuesta acidoUrico;
	
	@Column(name = "fecha_acido")
	private LocalDate fechaAcido;
	
	@Column(name = "resultado_acido")
	private String resultadoAcido;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "citologia_cervicovaginal")
    private Respuesta citologiaCervicovaginal;
	
	@Column(name = "fecha_citologia")
	private LocalDate fechaCitologia;
	
	@Column(name = "resultado_citologia")
	private String resultadoCitologia;
	
	public enum Respuesta{
		si, no
	}
	
	public ExamenesLaboratorio() {}

	public ExamenesLaboratorio(Integer idExamenes, Long idUser, Respuesta glucosaAyuno, LocalDate fechaGlucosa,
			String resultadoGlucosa, Respuesta biometricaHematica, LocalDate fechaBiometrica,
			String resultadoBiometrica, Respuesta ego, LocalDate fechaEgo, String resultadoEgo, Respuesta urocultivo,
			LocalDate fechaUrocultivo, String resultadoUrocultivo, Respuesta vdrl, LocalDate fechaVdrl,
			String resultadoVdrl, Respuesta vihHepatitisB, LocalDate fechaVih, String resultadoVih,
			Respuesta creatinina, LocalDate fechaCreatinina, String resultadoCreatinina, Respuesta acidoUrico,
			LocalDate fechaAcido, String resultadoAcido, Respuesta citologiaCervicovaginal, LocalDate fechaCitologia,
			String resultadoCitologia) {
		super();
		this.idExamenes = idExamenes;
		this.idUser = idUser;
		this.glucosaAyuno = glucosaAyuno;
		this.fechaGlucosa = fechaGlucosa;
		this.resultadoGlucosa = resultadoGlucosa;
		this.biometricaHematica = biometricaHematica;
		this.fechaBiometrica = fechaBiometrica;
		this.resultadoBiometrica = resultadoBiometrica;
		this.ego = ego;
		this.fechaEgo = fechaEgo;
		this.resultadoEgo = resultadoEgo;
		this.urocultivo = urocultivo;
		this.fechaUrocultivo = fechaUrocultivo;
		this.resultadoUrocultivo = resultadoUrocultivo;
		this.vdrl = vdrl;
		this.fechaVdrl = fechaVdrl;
		this.resultadoVdrl = resultadoVdrl;
		this.vihHepatitisB = vihHepatitisB;
		this.fechaVih = fechaVih;
		this.resultadoVih = resultadoVih;
		this.creatinina = creatinina;
		this.fechaCreatinina = fechaCreatinina;
		this.resultadoCreatinina = resultadoCreatinina;
		this.acidoUrico = acidoUrico;
		this.fechaAcido = fechaAcido;
		this.resultadoAcido = resultadoAcido;
		this.citologiaCervicovaginal = citologiaCervicovaginal;
		this.fechaCitologia = fechaCitologia;
		this.resultadoCitologia = resultadoCitologia;
	}



	public Integer getIdExamenes() {
		return idExamenes;
	}

	public void setIdExamenes(Integer idExamenes) {
		this.idExamenes = idExamenes;
	}
	
	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Respuesta getGlucosaAyuno() {
		return glucosaAyuno;
	}

	public void setGlucosaAyuno(Respuesta glucosaAyuno) {
		this.glucosaAyuno = glucosaAyuno;
	}

	public LocalDate getFechaGlucosa() {
		return fechaGlucosa;
	}

	public void setFechaGlucosa(LocalDate fechaGlucosa) {
		this.fechaGlucosa = fechaGlucosa;
	}

	public String getResultadoGlucosa() {
		return resultadoGlucosa;
	}

	public void setResultadoGlucosa(String resultadoGlucosa) {
		this.resultadoGlucosa = resultadoGlucosa;
	}

	public Respuesta getBiometricaHematica() {
		return biometricaHematica;
	}

	public void setBiometricaHematica(Respuesta biometricaHematica) {
		this.biometricaHematica = biometricaHematica;
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

	public Respuesta getEgo() {
		return ego;
	}

	public void setEgo(Respuesta ego) {
		this.ego = ego;
	}

	public LocalDate getFechaEgo() {
		return fechaEgo;
	}

	public void setFechaEgo(LocalDate fechaEgo) {
		this.fechaEgo = fechaEgo;
	}

	public String getResultadoEgo() {
		return resultadoEgo;
	}

	public void setResultadoEgo(String resultadoEgo) {
		this.resultadoEgo = resultadoEgo;
	}

	public Respuesta getUrocultivo() {
		return urocultivo;
	}

	public void setUrocultivo(Respuesta urocultivo) {
		this.urocultivo = urocultivo;
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

	public Respuesta getVdrl() {
		return vdrl;
	}

	public void setVdrl(Respuesta vdrl) {
		this.vdrl = vdrl;
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

	public Respuesta getVihHepatitisB() {
		return vihHepatitisB;
	}

	public void setVihHepatitisB(Respuesta vihHepatitisB) {
		this.vihHepatitisB = vihHepatitisB;
	}

	public LocalDate getFechaVih() {
		return fechaVih;
	}

	public void setFechaVih(LocalDate fechaVih) {
		this.fechaVih = fechaVih;
	}

	public String getResultadoVih() {
		return resultadoVih;
	}

	public void setResultadoVih(String resultadoVih) {
		this.resultadoVih = resultadoVih;
	}

	public Respuesta getCreatinina() {
		return creatinina;
	}

	public void setCreatinina(Respuesta creatinina) {
		this.creatinina = creatinina;
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

	public Respuesta getAcidoUrico() {
		return acidoUrico;
	}

	public void setAcidoUrico(Respuesta acidoUrico) {
		this.acidoUrico = acidoUrico;
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

	public Respuesta getCitologiaCervicovaginal() {
		return citologiaCervicovaginal;
	}

	public void setCitologiaCervicovaginal(Respuesta citologiaCervicovaginal) {
		this.citologiaCervicovaginal = citologiaCervicovaginal;
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
