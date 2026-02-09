package mx.edu.uacm.ws.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuestionario_salud")
public class CuestionarioSalud {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cuestionario")
    private Long idCuestionario;
    
    @Column(name = "id_user")
    private Long idUser;
    
    @Column(name = "primer_embarazo")
    private Boolean primerEmbarazo;
    
    @Column(name = "obesidad")
    private Boolean obesidad;
    
    @Column(name = "sobrepeso")
    private Boolean sobrepeso;
    
    @Column(name = "presion_arterial_alta")
    private Boolean presionArterialAlta;
    
    @Column(name = "diabetes_mellitus")
    private Boolean diabetesMellitus;
    
    @Column(name = "epilepsia")
    private Boolean epilepsia;
    
    @Column(name = "nefropatia")
    private Boolean nefropatia;
    
    @Column(name = "lupus")
    private Boolean lupus;
    
    @Column(name = "desnutricion")
    private Boolean desnutricion;
    
    @Column(name = "enfermedad_tiroidea")
    private Boolean enfermedadTiroidea;
    
    @Column(name = "otras_enfermedades")
    private Boolean otrasEnfermedades;
    
    @Column(name = "descripcion_otras", length = 1000)
    private String descripcionOtras;
    
    @Column(name = "enfermedades_pulmon")
    private Boolean enfermedadesPulmon;
    
    @Column(name = "asma")
    private Boolean asma;
    
    @Column(name = "tuberculosis")
    private Boolean tuberculosis;
    
    @Column(name = "epoc")
    private Boolean epoc;
    
    @Column(name = "fibrosis_quistica")
    private Boolean fibrosisQuistica;
    
    @Column(name = "neumonia_recurrente")
    private Boolean neumoniaRecurrente;
    
    @Column(name = "otras_pulmonares")
    private Boolean otrasPulmonares;
    
    @Column(name = "descripcion_otras_pulmonares", length = 500)
    private String descripcionOtrasPulmonares;
    
    @Column(name = "enfermedades_corazon")
    private Boolean enfermedadesCorazon;
    
    @Column(name = "cardiopatia_congenita")
    private Boolean cardiopatiaCongenita;
    
    @Column(name = "valvulopatia")
    private Boolean valvulopatia;
    
    @Column(name = "arritmias")
    private Boolean arritmias;
    
    @Column(name = "miocardiopatia")
    private Boolean miocardiopatia;
    
    @Column(name = "hipertension_pulmonar")
    private Boolean hipertensionPulmonar;
    
    @Column(name = "otras_cardiaca")
    private Boolean otrasCardiaca;
    
    @Column(name = "descripcion_otras_cardiaca", length = 500)
    private String descripcionOtrasCardiaca;
    
    // Enfermedades hematológicas (desplegable)
    @Column(name = "enfermedades_hematologicas")
    private Boolean enfermedadesHematologicas;
    
    @Column(name = "anemia")
    private Boolean anemia;
    
    @Column(name = "trombocitopenia")
    private Boolean trombocitopenia;
    
    @Column(name = "hemofilia")
    private Boolean hemofilia;
    
    @Column(name = "leucemia")
    private Boolean leucemia;
    
    @Column(name = "anemia_falciforme")
    private Boolean anemiaFalciforme;
    
    @Column(name = "otras_hematologicas")
    private Boolean otrasHematologicas;
    
    @Column(name = "descripcion_otras_hematologicas", length = 500)
    private String descripcionOtrasHematologicas;
    
    @Column(name = "enfermedades_transmision_sexual")
    private Boolean enfermedadesTransmisionSexual;
    
    @Column(name = "vih")
    private Boolean vih;
    
    @Column(name = "sifilis")
    private Boolean sifilis;
    
    @Column(name = "gonorrea")
    private Boolean gonorrea;
    
    @Column(name = "clamidia")
    private Boolean clamidia;
    
    @Column(name = "herpes_genital")
    private Boolean herpesGenital;
    
    @Column(name = "vph")
    private Boolean vph;
    
    @Column(name = "otras_ets")
    private Boolean otrasEts;
    
    @Column(name = "descripcion_otras_ets", length = 500)
    private String descripcionOtrasEts;
    
    @Column(name = "numero_embarazos")
    private Integer numeroEmbarazos;
    
    @Column(name = "numero_partos")
    private Integer numeroPartos;
    
    @Column(name = "numero_cesareas")
    private Integer numeroCesareas;
    
    @Column(name = "numero_abortos")
    private Integer numeroAbortos;
    
    @Column(name = "numero_ectopicos")
    private Integer numeroEctopicos;
    
    @Column(name = "trastornos_hipertensivos")
    private Boolean trastornosHipertensivos;
    
    @Column(name = "desprendimiento_placenta")
    private Boolean desprendimientoPlacenta;
    
    @Column(name = "parto_pretermino")
    private Boolean partoPretermino;
    
    @Column(name = "diabetes_gestacional")
    private Boolean diabetesGestacional;
    
    @Column(name = "restriccion_crecimiento")
    private Boolean restriccionCrecimiento;
    
    @Column(name = "muerte_fetal")
    private Boolean muerteFetal;
    
    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;
    
    public CuestionarioSalud() {}

	public CuestionarioSalud(Long idCuestionario, Long idUser, Boolean primerEmbarazo, Boolean obesidad,
			Boolean sobrepeso, Boolean presionArterialAlta, Boolean diabetesMellitus, Boolean epilepsia,
			Boolean nefropatia, Boolean lupus, Boolean desnutricion, Boolean enfermedadTiroidea,
			Boolean otrasEnfermedades, String descripcionOtras, Boolean enfermedadesPulmon, Boolean asma,
			Boolean tuberculosis, Boolean epoc, Boolean fibrosisQuistica, Boolean neumoniaRecurrente,
			Boolean otrasPulmonares, String descripcionOtrasPulmonares, Boolean enfermedadesCorazon,
			Boolean cardiopatiaCongenita, Boolean valvulopatia, Boolean arritmias, Boolean miocardiopatia,
			Boolean hipertensionPulmonar, Boolean otrasCardiaca, String descripcionOtrasCardiaca,
			Boolean enfermedadesHematologicas, Boolean anemia, Boolean trombocitopenia, Boolean hemofilia,
			Boolean leucemia, Boolean anemiaFalciforme, Boolean otrasHematologicas,
			String descripcionOtrasHematologicas, Boolean enfermedadesTransmisionSexual, Boolean vih, Boolean sifilis,
			Boolean gonorrea, Boolean clamidia, Boolean herpesGenital, Boolean vph, Boolean otrasEts,
			String descripcionOtrasEts, Integer numeroEmbarazos, Integer numeroPartos, Integer numeroCesareas,
			Integer numeroAbortos, Integer numeroEctopicos, Boolean trastornosHipertensivos,
			Boolean desprendimientoPlacenta, Boolean partoPretermino, Boolean diabetesGestacional,
			Boolean restriccionCrecimiento, Boolean muerteFetal, LocalDateTime fechaRegistro) {
		super();
		this.idCuestionario = idCuestionario;
		this.idUser = idUser;
		this.primerEmbarazo = primerEmbarazo;
		this.obesidad = obesidad;
		this.sobrepeso = sobrepeso;
		this.presionArterialAlta = presionArterialAlta;
		this.diabetesMellitus = diabetesMellitus;
		this.epilepsia = epilepsia;
		this.nefropatia = nefropatia;
		this.lupus = lupus;
		this.desnutricion = desnutricion;
		this.enfermedadTiroidea = enfermedadTiroidea;
		this.otrasEnfermedades = otrasEnfermedades;
		this.descripcionOtras = descripcionOtras;
		this.enfermedadesPulmon = enfermedadesPulmon;
		this.asma = asma;
		this.tuberculosis = tuberculosis;
		this.epoc = epoc;
		this.fibrosisQuistica = fibrosisQuistica;
		this.neumoniaRecurrente = neumoniaRecurrente;
		this.otrasPulmonares = otrasPulmonares;
		this.descripcionOtrasPulmonares = descripcionOtrasPulmonares;
		this.enfermedadesCorazon = enfermedadesCorazon;
		this.cardiopatiaCongenita = cardiopatiaCongenita;
		this.valvulopatia = valvulopatia;
		this.arritmias = arritmias;
		this.miocardiopatia = miocardiopatia;
		this.hipertensionPulmonar = hipertensionPulmonar;
		this.otrasCardiaca = otrasCardiaca;
		this.descripcionOtrasCardiaca = descripcionOtrasCardiaca;
		this.enfermedadesHematologicas = enfermedadesHematologicas;
		this.anemia = anemia;
		this.trombocitopenia = trombocitopenia;
		this.hemofilia = hemofilia;
		this.leucemia = leucemia;
		this.anemiaFalciforme = anemiaFalciforme;
		this.otrasHematologicas = otrasHematologicas;
		this.descripcionOtrasHematologicas = descripcionOtrasHematologicas;
		this.enfermedadesTransmisionSexual = enfermedadesTransmisionSexual;
		this.vih = vih;
		this.sifilis = sifilis;
		this.gonorrea = gonorrea;
		this.clamidia = clamidia;
		this.herpesGenital = herpesGenital;
		this.vph = vph;
		this.otrasEts = otrasEts;
		this.descripcionOtrasEts = descripcionOtrasEts;
		this.numeroEmbarazos = numeroEmbarazos;
		this.numeroPartos = numeroPartos;
		this.numeroCesareas = numeroCesareas;
		this.numeroAbortos = numeroAbortos;
		this.numeroEctopicos = numeroEctopicos;
		this.trastornosHipertensivos = trastornosHipertensivos;
		this.desprendimientoPlacenta = desprendimientoPlacenta;
		this.partoPretermino = partoPretermino;
		this.diabetesGestacional = diabetesGestacional;
		this.restriccionCrecimiento = restriccionCrecimiento;
		this.muerteFetal = muerteFetal;
		this.fechaRegistro = fechaRegistro;
	}

	public Long getIdCuestionario() {
		return idCuestionario;
	}

	public void setIdCuestionario(Long idCuestionario) {
		this.idCuestionario = idCuestionario;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public Boolean getPrimerEmbarazo() {
		return primerEmbarazo;
	}

	public void setPrimerEmbarazo(Boolean primerEmbarazo) {
		this.primerEmbarazo = primerEmbarazo;
	}

	public Boolean getObesidad() {
		return obesidad;
	}

	public void setObesidad(Boolean obesidad) {
		this.obesidad = obesidad;
	}

	public Boolean getSobrepeso() {
		return sobrepeso;
	}

	public void setSobrepeso(Boolean sobrepeso) {
		this.sobrepeso = sobrepeso;
	}

	public Boolean getPresionArterialAlta() {
		return presionArterialAlta;
	}

	public void setPresionArterialAlta(Boolean presionArterialAlta) {
		this.presionArterialAlta = presionArterialAlta;
	}

	public Boolean getDiabetesMellitus() {
		return diabetesMellitus;
	}

	public void setDiabetesMellitus(Boolean diabetesMellitus) {
		this.diabetesMellitus = diabetesMellitus;
	}

	public Boolean getEpilepsia() {
		return epilepsia;
	}

	public void setEpilepsia(Boolean epilepsia) {
		this.epilepsia = epilepsia;
	}

	public Boolean getNefropatia() {
		return nefropatia;
	}

	public void setNefropatia(Boolean nefropatia) {
		this.nefropatia = nefropatia;
	}

	public Boolean getLupus() {
		return lupus;
	}

	public void setLupus(Boolean lupus) {
		this.lupus = lupus;
	}

	public Boolean getDesnutricion() {
		return desnutricion;
	}

	public void setDesnutricion(Boolean desnutricion) {
		this.desnutricion = desnutricion;
	}

	public Boolean getEnfermedadTiroidea() {
		return enfermedadTiroidea;
	}

	public void setEnfermedadTiroidea(Boolean enfermedadTiroidea) {
		this.enfermedadTiroidea = enfermedadTiroidea;
	}

	public Boolean getOtrasEnfermedades() {
		return otrasEnfermedades;
	}

	public void setOtrasEnfermedades(Boolean otrasEnfermedades) {
		this.otrasEnfermedades = otrasEnfermedades;
	}

	public String getDescripcionOtras() {
		return descripcionOtras;
	}

	public void setDescripcionOtras(String descripcionOtras) {
		this.descripcionOtras = descripcionOtras;
	}

	public Boolean getEnfermedadesPulmon() {
		return enfermedadesPulmon;
	}

	public void setEnfermedadesPulmon(Boolean enfermedadesPulmon) {
		this.enfermedadesPulmon = enfermedadesPulmon;
	}

	public Boolean getAsma() {
		return asma;
	}

	public void setAsma(Boolean asma) {
		this.asma = asma;
	}

	public Boolean getTuberculosis() {
		return tuberculosis;
	}

	public void setTuberculosis(Boolean tuberculosis) {
		this.tuberculosis = tuberculosis;
	}

	public Boolean getEpoc() {
		return epoc;
	}

	public void setEpoc(Boolean epoc) {
		this.epoc = epoc;
	}

	public Boolean getFibrosisQuistica() {
		return fibrosisQuistica;
	}

	public void setFibrosisQuistica(Boolean fibrosisQuistica) {
		this.fibrosisQuistica = fibrosisQuistica;
	}

	public Boolean getNeumoniaRecurrente() {
		return neumoniaRecurrente;
	}

	public void setNeumoniaRecurrente(Boolean neumoniaRecurrente) {
		this.neumoniaRecurrente = neumoniaRecurrente;
	}

	public Boolean getOtrasPulmonares() {
		return otrasPulmonares;
	}

	public void setOtrasPulmonares(Boolean otrasPulmonares) {
		this.otrasPulmonares = otrasPulmonares;
	}

	public String getDescripcionOtrasPulmonares() {
		return descripcionOtrasPulmonares;
	}

	public void setDescripcionOtrasPulmonares(String descripcionOtrasPulmonares) {
		this.descripcionOtrasPulmonares = descripcionOtrasPulmonares;
	}

	public Boolean getEnfermedadesCorazon() {
		return enfermedadesCorazon;
	}

	public void setEnfermedadesCorazon(Boolean enfermedadesCorazon) {
		this.enfermedadesCorazon = enfermedadesCorazon;
	}

	public Boolean getCardiopatiaCongenita() {
		return cardiopatiaCongenita;
	}

	public void setCardiopatiaCongenita(Boolean cardiopatiaCongenita) {
		this.cardiopatiaCongenita = cardiopatiaCongenita;
	}

	public Boolean getValvulopatia() {
		return valvulopatia;
	}

	public void setValvulopatia(Boolean valvulopatia) {
		this.valvulopatia = valvulopatia;
	}

	public Boolean getArritmias() {
		return arritmias;
	}

	public void setArritmias(Boolean arritmias) {
		this.arritmias = arritmias;
	}

	public Boolean getMiocardiopatia() {
		return miocardiopatia;
	}

	public void setMiocardiopatia(Boolean miocardiopatia) {
		this.miocardiopatia = miocardiopatia;
	}

	public Boolean getHipertensionPulmonar() {
		return hipertensionPulmonar;
	}

	public void setHipertensionPulmonar(Boolean hipertensionPulmonar) {
		this.hipertensionPulmonar = hipertensionPulmonar;
	}

	public Boolean getOtrasCardiaca() {
		return otrasCardiaca;
	}

	public void setOtrasCardiaca(Boolean otrasCardiaca) {
		this.otrasCardiaca = otrasCardiaca;
	}

	public String getDescripcionOtrasCardiaca() {
		return descripcionOtrasCardiaca;
	}

	public void setDescripcionOtrasCardiaca(String descripcionOtrasCardiaca) {
		this.descripcionOtrasCardiaca = descripcionOtrasCardiaca;
	}

	public Boolean getEnfermedadesHematologicas() {
		return enfermedadesHematologicas;
	}

	public void setEnfermedadesHematologicas(Boolean enfermedadesHematologicas) {
		this.enfermedadesHematologicas = enfermedadesHematologicas;
	}

	public Boolean getAnemia() {
		return anemia;
	}

	public void setAnemia(Boolean anemia) {
		this.anemia = anemia;
	}

	public Boolean getTrombocitopenia() {
		return trombocitopenia;
	}

	public void setTrombocitopenia(Boolean trombocitopenia) {
		this.trombocitopenia = trombocitopenia;
	}

	public Boolean getHemofilia() {
		return hemofilia;
	}

	public void setHemofilia(Boolean hemofilia) {
		this.hemofilia = hemofilia;
	}

	public Boolean getLeucemia() {
		return leucemia;
	}

	public void setLeucemia(Boolean leucemia) {
		this.leucemia = leucemia;
	}

	public Boolean getAnemiaFalciforme() {
		return anemiaFalciforme;
	}

	public void setAnemiaFalciforme(Boolean anemiaFalciforme) {
		this.anemiaFalciforme = anemiaFalciforme;
	}

	public Boolean getOtrasHematologicas() {
		return otrasHematologicas;
	}

	public void setOtrasHematologicas(Boolean otrasHematologicas) {
		this.otrasHematologicas = otrasHematologicas;
	}

	public String getDescripcionOtrasHematologicas() {
		return descripcionOtrasHematologicas;
	}

	public void setDescripcionOtrasHematologicas(String descripcionOtrasHematologicas) {
		this.descripcionOtrasHematologicas = descripcionOtrasHematologicas;
	}

	public Boolean getEnfermedadesTransmisionSexual() {
		return enfermedadesTransmisionSexual;
	}

	public void setEnfermedadesTransmisionSexual(Boolean enfermedadesTransmisionSexual) {
		this.enfermedadesTransmisionSexual = enfermedadesTransmisionSexual;
	}

	public Boolean getVih() {
		return vih;
	}

	public void setVih(Boolean vih) {
		this.vih = vih;
	}

	public Boolean getSifilis() {
		return sifilis;
	}

	public void setSifilis(Boolean sifilis) {
		this.sifilis = sifilis;
	}

	public Boolean getGonorrea() {
		return gonorrea;
	}

	public void setGonorrea(Boolean gonorrea) {
		this.gonorrea = gonorrea;
	}

	public Boolean getClamidia() {
		return clamidia;
	}

	public void setClamidia(Boolean clamidia) {
		this.clamidia = clamidia;
	}

	public Boolean getHerpesGenital() {
		return herpesGenital;
	}

	public void setHerpesGenital(Boolean herpesGenital) {
		this.herpesGenital = herpesGenital;
	}

	public Boolean getVph() {
		return vph;
	}

	public void setVph(Boolean vph) {
		this.vph = vph;
	}

	public Boolean getOtrasEts() {
		return otrasEts;
	}

	public void setOtrasEts(Boolean otrasEts) {
		this.otrasEts = otrasEts;
	}

	public String getDescripcionOtrasEts() {
		return descripcionOtrasEts;
	}

	public void setDescripcionOtrasEts(String descripcionOtrasEts) {
		this.descripcionOtrasEts = descripcionOtrasEts;
	}

	public Integer getNumeroEmbarazos() {
		return numeroEmbarazos;
	}

	public void setNumeroEmbarazos(Integer numeroEmbarazos) {
		this.numeroEmbarazos = numeroEmbarazos;
	}

	public Integer getNumeroPartos() {
		return numeroPartos;
	}

	public void setNumeroPartos(Integer numeroPartos) {
		this.numeroPartos = numeroPartos;
	}

	public Integer getNumeroCesareas() {
		return numeroCesareas;
	}

	public void setNumeroCesareas(Integer numeroCesareas) {
		this.numeroCesareas = numeroCesareas;
	}

	public Integer getNumeroAbortos() {
		return numeroAbortos;
	}

	public void setNumeroAbortos(Integer numeroAbortos) {
		this.numeroAbortos = numeroAbortos;
	}

	public Integer getNumeroEctopicos() {
		return numeroEctopicos;
	}

	public void setNumeroEctopicos(Integer numeroEctopicos) {
		this.numeroEctopicos = numeroEctopicos;
	}

	public Boolean getTrastornosHipertensivos() {
		return trastornosHipertensivos;
	}

	public void setTrastornosHipertensivos(Boolean trastornosHipertensivos) {
		this.trastornosHipertensivos = trastornosHipertensivos;
	}

	public Boolean getDesprendimientoPlacenta() {
		return desprendimientoPlacenta;
	}

	public void setDesprendimientoPlacenta(Boolean desprendimientoPlacenta) {
		this.desprendimientoPlacenta = desprendimientoPlacenta;
	}

	public Boolean getPartoPretermino() {
		return partoPretermino;
	}

	public void setPartoPretermino(Boolean partoPretermino) {
		this.partoPretermino = partoPretermino;
	}

	public Boolean getDiabetesGestacional() {
		return diabetesGestacional;
	}

	public void setDiabetesGestacional(Boolean diabetesGestacional) {
		this.diabetesGestacional = diabetesGestacional;
	}

	public Boolean getRestriccionCrecimiento() {
		return restriccionCrecimiento;
	}

	public void setRestriccionCrecimiento(Boolean restriccionCrecimiento) {
		this.restriccionCrecimiento = restriccionCrecimiento;
	}

	public Boolean getMuerteFetal() {
		return muerteFetal;
	}

	public void setMuerteFetal(Boolean muerteFetal) {
		this.muerteFetal = muerteFetal;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	@Override
	public String toString() {
		return "CuestionarioSalud [idCuestionario=" + idCuestionario + ", idUser=" + idUser + ", primerEmbarazo="
				+ primerEmbarazo + ", obesidad=" + obesidad + ", sobrepeso=" + sobrepeso + ", presionArterialAlta="
				+ presionArterialAlta + ", diabetesMellitus=" + diabetesMellitus + ", epilepsia=" + epilepsia
				+ ", nefropatia=" + nefropatia + ", lupus=" + lupus + ", desnutricion=" + desnutricion
				+ ", enfermedadTiroidea=" + enfermedadTiroidea + ", otrasEnfermedades=" + otrasEnfermedades
				+ ", descripcionOtras=" + descripcionOtras + ", enfermedadesPulmon=" + enfermedadesPulmon + ", asma="
				+ asma + ", tuberculosis=" + tuberculosis + ", epoc=" + epoc + ", fibrosisQuistica=" + fibrosisQuistica
				+ ", neumoniaRecurrente=" + neumoniaRecurrente + ", otrasPulmonares=" + otrasPulmonares
				+ ", descripcionOtrasPulmonares=" + descripcionOtrasPulmonares + ", enfermedadesCorazon="
				+ enfermedadesCorazon + ", cardiopatiaCongenita=" + cardiopatiaCongenita + ", valvulopatia="
				+ valvulopatia + ", arritmias=" + arritmias + ", miocardiopatia=" + miocardiopatia
				+ ", hipertensionPulmonar=" + hipertensionPulmonar + ", otrasCardiaca=" + otrasCardiaca
				+ ", descripcionOtrasCardiaca=" + descripcionOtrasCardiaca + ", enfermedadesHematologicas="
				+ enfermedadesHematologicas + ", anemia=" + anemia + ", trombocitopenia=" + trombocitopenia
				+ ", hemofilia=" + hemofilia + ", leucemia=" + leucemia + ", anemiaFalciforme=" + anemiaFalciforme
				+ ", otrasHematologicas=" + otrasHematologicas + ", descripcionOtrasHematologicas="
				+ descripcionOtrasHematologicas + ", enfermedadesTransmisionSexual=" + enfermedadesTransmisionSexual
				+ ", vih=" + vih + ", sifilis=" + sifilis + ", gonorrea=" + gonorrea + ", clamidia=" + clamidia
				+ ", herpesGenital=" + herpesGenital + ", vph=" + vph + ", otrasEts=" + otrasEts
				+ ", descripcionOtrasEts=" + descripcionOtrasEts + ", numeroEmbarazos=" + numeroEmbarazos
				+ ", numeroPartos=" + numeroPartos + ", numeroCesareas=" + numeroCesareas + ", numeroAbortos="
				+ numeroAbortos + ", numeroEctopicos=" + numeroEctopicos + ", trastornosHipertensivos="
				+ trastornosHipertensivos + ", desprendimientoPlacenta=" + desprendimientoPlacenta
				+ ", partoPretermino=" + partoPretermino + ", diabetesGestacional=" + diabetesGestacional
				+ ", restriccionCrecimiento=" + restriccionCrecimiento + ", muerteFetal=" + muerteFetal
				+ ", fechaRegistro=" + fechaRegistro + "]";
	}
    
    

}
