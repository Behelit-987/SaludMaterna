class Usuaria {
  int? idUser;
  String nombres;
  String apellidos;
  DateTime fechaNacimiento;
  String domicilio;
  String numCelular;
  String numEmergencia;
  double talla;
  double peso;
  String? curp;
  String email;
  String passwordHash;
  DateTime? fechaRegistro;
  bool activo;

  Usuaria({
    this.idUser,
    required this.nombres,
    required this.apellidos,
    required this.fechaNacimiento,
    required this.domicilio,
    required this.numCelular,
    required this.numEmergencia,
    required this.talla,
    required this.peso,
    this.curp,
    required this.email,
    required this.passwordHash,
    this.fechaRegistro,
    this.activo = true,
  });

  Map<String, dynamic> toMap() {
    return {
      'id_User': idUser,
      'nombres': nombres,
      'apellidos': apellidos,
      'fecha_nacimiento': fechaNacimiento.toIso8601String(),
      'domicilio': domicilio,
      'Num_celular': numCelular,
      'Num_emergencia': numEmergencia,
      'talla': talla,
      'peso': peso,
      'curp': curp,
      'email': email,
      'password_hash': passwordHash,
      'fecha_registro': fechaRegistro?.toIso8601String(),
      'activo': activo ? 1 : 0,
    };
  }

  factory Usuaria.fromMap(Map<String, dynamic> map) {
    return Usuaria(
      idUser: map['id_User'],
      nombres: map['nombres'],
      apellidos: map['apellidos'],
      fechaNacimiento: DateTime.parse(map['fecha_nacimiento']),
      domicilio: map['domicilio'],
      numCelular: map['Num_celular'],
      numEmergencia: map['Num_emergencia'],
      talla: map['talla'] as double,
      peso: map['peso'] as double,
      curp: map['curp'],
      email: map['email'],
      passwordHash: map['password_hash'],
      fechaRegistro: map['fecha_registro'] != null 
          ? DateTime.parse(map['fecha_registro'])
          : null,
      activo: map['activo'] == 1,
    );
  }

  // Calcular IMC
  double get imc {
    return peso / (talla * talla);
  }

  // Calcular edad
  int get edad {
    final now = DateTime.now();
    int age = now.year - fechaNacimiento.year;
    if (now.month < fechaNacimiento.month ||
        (now.month == fechaNacimiento.month && now.day < fechaNacimiento.day)) {
      age--;
    }
    return age;
  }

  // Determinar categoría según edad
  String get categoriaEdad {
    if (edad >= 4 && edad <= 11) return 'INFANCIA';
    if (edad >= 12 && edad <= 48) return 'EDAD_FERTIL';
    if (edad > 48) return 'SALUD_MATERNA';
    return 'MENOR_4';
  }
}