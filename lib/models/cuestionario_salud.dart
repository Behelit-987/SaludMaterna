class CuestionarioSalud {
  int? idCuestionario;
  int idUser;
  
  // Información básica
  bool? primerEmbarazo;
  
  // Condiciones generales
  bool? obesidad;
  bool? sobrepeso;
  bool? presionArterialAlta;
  bool? diabetesMellitus;
  bool? epilepsia;
  bool? nefropatia;
  bool? lupus;
  bool? desnutricion;
  bool? enfermedadTiroidea;
  bool? otrasEnfermedades;
  String? descripcionOtras;
  
  // Enfermedades del pulmón
  bool? enfermedadesPulmon;
  bool? asma;
  bool? tuberculosis;
  bool? epoc;
  bool? fibrosisQuistica;
  bool? neumoniaRecurrente;
  bool? otrasPulmonares;
  String? descripcionOtrasPulmonares;
  
  // Enfermedades del corazón
  bool? enfermedadesCorazon;
  bool? cardiopatiaCongenita;
  bool? valvulopatia;
  bool? arritmias;
  bool? miocardiopatia;
  bool? hipertensionPulmonar;
  bool? otrasCardiaca;
  String? descripcionOtrasCardiaca;
  
  // Enfermedades hematológicas
  bool? enfermedadesHematologicas;
  bool? anemia;
  bool? trombocitopenia;
  bool? hemofilia;
  bool? leucemia;
  bool? anemiaFalciforme;
  bool? otrasHematologicas;
  String? descripcionOtrasHematologicas;
  
  // Enfermedades de Transmisión Sexual
  bool? enfermedadesTransmisionSexual;
  bool? vih;
  bool? sifilis;
  bool? gonorrea;
  bool? clamidia;
  bool? herpesGenital;
  bool? vph;
  bool? otrasEts;
  String? descripcionOtrasEts;
  
  // Para embarazos anteriores
  int? numeroEmbarazos;
  int? numeroPartos;
  int? numeroCesareas;
  int? numeroAbortos;
  int? numeroEctopicos;
  
  // Complicaciones en embarazos anteriores
  bool? trastornosHipertensivos;
  bool? desprendimientoPlacenta;
  bool? partoPretermino;
  bool? diabetesGestacional;
  bool? restriccionCrecimiento;
  bool? muerteFetal;
  
  DateTime fechaRegistro;

  CuestionarioSalud({
    this.idCuestionario,
    required this.idUser,
    this.primerEmbarazo,
    this.obesidad,
    this.sobrepeso,
    this.presionArterialAlta,
    this.diabetesMellitus,
    this.epilepsia,
    this.nefropatia,
    this.lupus,
    this.desnutricion,
    this.enfermedadTiroidea,
    this.otrasEnfermedades,
    this.descripcionOtras,
    this.enfermedadesPulmon,
    this.asma,
    this.tuberculosis,
    this.epoc,
    this.fibrosisQuistica,
    this.neumoniaRecurrente,
    this.otrasPulmonares,
    this.descripcionOtrasPulmonares,
    this.enfermedadesCorazon,
    this.cardiopatiaCongenita,
    this.valvulopatia,
    this.arritmias,
    this.miocardiopatia,
    this.hipertensionPulmonar,
    this.otrasCardiaca,
    this.descripcionOtrasCardiaca,
    this.enfermedadesHematologicas,
    this.anemia,
    this.trombocitopenia,
    this.hemofilia,
    this.leucemia,
    this.anemiaFalciforme,
    this.otrasHematologicas,
    this.descripcionOtrasHematologicas,
    this.enfermedadesTransmisionSexual,
    this.vih,
    this.sifilis,
    this.gonorrea,
    this.clamidia,
    this.herpesGenital,
    this.vph,
    this.otrasEts,
    this.descripcionOtrasEts,
    this.numeroEmbarazos,
    this.numeroPartos,
    this.numeroCesareas,
    this.numeroAbortos,
    this.numeroEctopicos,
    this.trastornosHipertensivos,
    this.desprendimientoPlacenta,
    this.partoPretermino,
    this.diabetesGestacional,
    this.restriccionCrecimiento,
    this.muerteFetal,
    DateTime? fechaRegistro,
  }) : fechaRegistro = fechaRegistro ?? DateTime.now();

  Map<String, dynamic> toMap() {
    return {
      'id_cuestionario': idCuestionario,
      'id_user': idUser,
      'primer_embarazo': primerEmbarazo == true ? 1 : 0,
      'obesidad': obesidad == true ? 1 : 0,
      'sobrepeso': sobrepeso == true ? 1 : 0,
      'presion_arterial_alta': presionArterialAlta == true ? 1 : 0,
      'diabetes_mellitus': diabetesMellitus == true ? 1 : 0,
      'epilepsia': epilepsia == true ? 1 : 0,
      'nefropatia': nefropatia == true ? 1 : 0,
      'lupus': lupus == true ? 1 : 0,
      'desnutricion': desnutricion == true ? 1 : 0,
      'enfermedad_tiroidea': enfermedadTiroidea == true ? 1 : 0,
      'otras_enfermedades': otrasEnfermedades == true ? 1 : 0,
      'descripcion_otras': descripcionOtras,
      'enfermedades_pulmon': enfermedadesPulmon == true ? 1 : 0,
      'asma': asma == true ? 1 : 0,
      'tuberculosis': tuberculosis == true ? 1 : 0,
      'epoc': epoc == true ? 1 : 0,
      'fibrosis_quistica': fibrosisQuistica == true ? 1 : 0,
      'neumonia_recurrente': neumoniaRecurrente == true ? 1 : 0,
      'otras_pulmonares': otrasPulmonares == true ? 1 : 0,
      'descripcion_otras_pulmonares': descripcionOtrasPulmonares,
      'enfermedades_corazon': enfermedadesCorazon == true ? 1 : 0,
      'cardiopatia_congenita': cardiopatiaCongenita == true ? 1 : 0,
      'valvulopatia': valvulopatia == true ? 1 : 0,
      'arritmias': arritmias == true ? 1 : 0,
      'miocardiopatia': miocardiopatia == true ? 1 : 0,
      'hipertension_pulmonar': hipertensionPulmonar == true ? 1 : 0,
      'otras_cardiaca': otrasCardiaca == true ? 1 : 0,
      'descripcion_otras_cardiaca': descripcionOtrasCardiaca,
      'enfermedades_hematologicas': enfermedadesHematologicas == true ? 1 : 0,
      'anemia': anemia == true ? 1 : 0,
      'trombocitopenia': trombocitopenia == true ? 1 : 0,
      'hemofilia': hemofilia == true ? 1 : 0,
      'leucemia': leucemia == true ? 1 : 0,
      'anemia_falciforme': anemiaFalciforme == true ? 1 : 0,
      'otras_hematologicas': otrasHematologicas == true ? 1 : 0,
      'descripcion_otras_hematologicas': descripcionOtrasHematologicas,
      'enfermedades_transmision_sexual': enfermedadesTransmisionSexual == true ? 1 : 0,
      'vih': vih == true ? 1 : 0,
      'sifilis': sifilis == true ? 1 : 0,
      'gonorrea': gonorrea == true ? 1 : 0,
      'clamidia': clamidia == true ? 1 : 0,
      'herpes_genital': herpesGenital == true ? 1 : 0,
      'vph': vph == true ? 1 : 0,
      'otras_ets': otrasEts == true ? 1 : 0,
      'descripcion_otras_ets': descripcionOtrasEts,
      'numero_embarazos': numeroEmbarazos,
      'numero_partos': numeroPartos,
      'numero_cesareas': numeroCesareas,
      'numero_abortos': numeroAbortos,
      'numero_ectopicos': numeroEctopicos,
      'trastornos_hipertensivos': trastornosHipertensivos == true ? 1 : 0,
      'desprendimiento_placenta': desprendimientoPlacenta == true ? 1 : 0,
      'parto_pretermino': partoPretermino == true ? 1 : 0,
      'diabetes_gestacional': diabetesGestacional == true ? 1 : 0,
      'restriccion_crecimiento': restriccionCrecimiento == true ? 1 : 0,
      'muerte_fetal': muerteFetal == true ? 1 : 0,
      'fecha_registro': fechaRegistro.toIso8601String(),
    };
  }

  factory CuestionarioSalud.fromMap(Map<String, dynamic> map) {
    return CuestionarioSalud(
      idCuestionario: map['id_cuestionario'],
      idUser: map['id_user'],
      primerEmbarazo: map['primer_embarazo'] == 1,
      obesidad: map['obesidad'] == 1,
      sobrepeso: map['sobrepeso'] == 1,
      presionArterialAlta: map['presion_arterial_alta'] == 1,
      diabetesMellitus: map['diabetes_mellitus'] == 1,
      epilepsia: map['epilepsia'] == 1,
      nefropatia: map['nefropatia'] == 1,
      lupus: map['lupus'] == 1,
      desnutricion: map['desnutricion'] == 1,
      enfermedadTiroidea: map['enfermedad_tiroidea'] == 1,
      otrasEnfermedades: map['otras_enfermedades'] == 1,
      descripcionOtras: map['descripcion_otras'],
      enfermedadesPulmon: map['enfermedades_pulmon'] == 1,
      asma: map['asma'] == 1,
      tuberculosis: map['tuberculosis'] == 1,
      epoc: map['epoc'] == 1,
      fibrosisQuistica: map['fibrosis_quistica'] == 1,
      neumoniaRecurrente: map['neumonia_recurrente'] == 1,
      otrasPulmonares: map['otras_pulmonares'] == 1,
      descripcionOtrasPulmonares: map['descripcion_otras_pulmonares'],
      enfermedadesCorazon: map['enfermedades_corazon'] == 1,
      cardiopatiaCongenita: map['cardiopatia_congenita'] == 1,
      valvulopatia: map['valvulopatia'] == 1,
      arritmias: map['arritmias'] == 1,
      miocardiopatia: map['miocardiopatia'] == 1,
      hipertensionPulmonar: map['hipertension_pulmonar'] == 1,
      otrasCardiaca: map['otras_cardiaca'] == 1,
      descripcionOtrasCardiaca: map['descripcion_otras_cardiaca'],
      enfermedadesHematologicas: map['enfermedades_hematologicas'] == 1,
      anemia: map['anemia'] == 1,
      trombocitopenia: map['trombocitopenia'] == 1,
      hemofilia: map['hemofilia'] == 1,
      leucemia: map['leucemia'] == 1,
      anemiaFalciforme: map['anemia_falciforme'] == 1,
      otrasHematologicas: map['otras_hematologicas'] == 1,
      descripcionOtrasHematologicas: map['descripcion_otras_hematologicas'],
      enfermedadesTransmisionSexual: map['enfermedades_transmision_sexual'] == 1,
      vih: map['vih'] == 1,
      sifilis: map['sifilis'] == 1,
      gonorrea: map['gonorrea'] == 1,
      clamidia: map['clamidia'] == 1,
      herpesGenital: map['herpes_genital'] == 1,
      vph: map['vph'] == 1,
      otrasEts: map['otras_ets'] == 1,
      descripcionOtrasEts: map['descripcion_otras_ets'],
      numeroEmbarazos: map['numero_embarazos'],
      numeroPartos: map['numero_partos'],
      numeroCesareas: map['numero_cesareas'],
      numeroAbortos: map['numero_abortos'],
      numeroEctopicos: map['numero_ectopicos'],
      trastornosHipertensivos: map['trastornos_hipertensivos'] == 1,
      desprendimientoPlacenta: map['desprendimiento_placenta'] == 1,
      partoPretermino: map['parto_pretermino'] == 1,
      diabetesGestacional: map['diabetes_gestacional'] == 1,
      restriccionCrecimiento: map['restriccion_crecimiento'] == 1,
      muerteFetal: map['muerte_fetal'] == 1,
      fechaRegistro: DateTime.parse(map['fecha_registro']),
    );
  }

  // Método para validar si tiene condiciones de riesgo
  bool get tieneCondicionesRiesgo {
    return obesidad == true ||
        sobrepeso == true ||
        presionArterialAlta == true ||
        diabetesMellitus == true ||
        enfermedadesPulmon == true ||
        enfermedadesCorazon == true ||
        enfermedadesHematologicas == true ||
        enfermedadesTransmisionSexual == true;
  }
}