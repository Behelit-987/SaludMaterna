package mx.edu.uacm.ws.DTO;

import java.time.LocalDate;

import mx.edu.uacm.ws.Entity.ExamenesLaboratorio.Respuesta;

public class ExamenesLaboratorioDTO {
    
    private Respuesta glucosaAyuno;
    private LocalDate fechaGlucosa;
    private String resultadoGlucosa;
    
    private Respuesta biometricaHematica;
    private LocalDate fechaBiometrica;
    private String resultadoBiometrica;
    
    private Respuesta ego;
    private LocalDate fechaEgo;
    private String resultadoEgo;
    
    private Respuesta urocultivo;
    private LocalDate fechaUrocultivo;
    private String resultadoUrocultivo;
    
    private Respuesta vdrl;
    private LocalDate fechaVdrl;
    private String resultadoVdrl;
    
    private Respuesta vihHepatitisB;
    private LocalDate fechaVih;
    private String resultadoVih;
    
    private Respuesta creatinina;
    private LocalDate fechaCreatinina;
    private String resultadoCreatinina;
    
    private Respuesta acidoUrico;
    private LocalDate fechaAcido;
    private String resultadoAcido;
    
    private Respuesta citologiaCervicovaginal;
    private LocalDate fechaCitologia;
    private String resultadoCitologia;
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