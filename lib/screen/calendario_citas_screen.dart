import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../service/authservice.dart';
import '../service/apiservice.dart';

class CalendarioCitasScreen extends StatefulWidget {
  @override
  _CalendarioCitasScreenState createState() => _CalendarioCitasScreenState();
}

class _CalendarioCitasScreenState extends State<CalendarioCitasScreen> {
  Map<String, dynamic> _userData = {};
  List<dynamic> _consultas = [];
  List<dynamic> _ultrasonidos = [];
  Map<String, dynamic>? _estadoCitas;
  bool _isLoading = true;
  bool _tieneMestruacion = false;
  String? _fechaMestruacion;
  int? _userId;

  @override
  void initState() {
    super.initState();
    _loadUserData();
  }

  Future<void> _loadUserData() async {
    final prefs = await SharedPreferences.getInstance();
    final userDataStr = prefs.getString('user_data');
    
    if (userDataStr != null) {
      setState(() {
        _userData = json.decode(userDataStr);
        _userId = _userData['id'];
        _tieneMestruacion = prefs.getBool('tiene_mestruacion') ?? false;
      });
      
      await _loadCitasData();
    }
    
    setState(() {
      _isLoading = false;
    });
  }

  Future<void> _loadCitasData() async {
    if (_userId == null) return;

    try {
      // 1. Obtener consultas prenatales
      final consultasResult = await ApiService.getCitas(_userId!);
      if (consultasResult['success'] == true) {
        setState(() {
          _consultas = consultasResult['consultas'] ?? [];
        });
      }

      // 2. Obtener ultrasonidos
      final ultrasonidosResult = await ApiService.getUltrasonidos(_userId!);
      if (ultrasonidosResult['success'] == true) {
        setState(() {
          _ultrasonidos = ultrasonidosResult['ultrasonidos'] ?? [];
        });
      }

      // 3. Obtener estado de citas
      final estadoResult = await ApiService.getEstadoCitas(_userId!);
      if (estadoResult['success'] == true) {
        setState(() {
          _estadoCitas = estadoResult;
        });
      }

      // 4. Obtener fecha de menstruación
      final mestruacionResult = await ApiService.getMestruacion(_userId!);
      if (mestruacionResult['success'] == true) {
        if (mestruacionResult['fechaMestruacion'] != null) {
          setState(() {
            _fechaMestruacion = mestruacionResult['fechaMestruacion'];
            _tieneMestruacion = true;
          });
        }
      }
    } catch (e) {
      print('Error cargando citas: $e');
    }
  }

  Widget _buildHeader() {
    return Container(
      padding: EdgeInsets.all(20),
      decoration: BoxDecoration(
        gradient: LinearGradient(
          colors: [Colors.purple.shade700, Colors.purple.shade900],
          begin: Alignment.topLeft,
          end: Alignment.bottomRight,
        ),
        borderRadius: BorderRadius.vertical(bottom: Radius.circular(20)),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Row(
            children: [
              CircleAvatar(
                radius: 30,
                backgroundColor: Colors.white,
                child: Icon(
                  Icons.person,
                  size: 30,
                  color: Colors.purple,
                ),
              ),
              SizedBox(width: 15),
              Expanded(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text(
                      '${_userData['nombreCompleto'] ?? _userData['nombres'] ?? ''}',
                      style: TextStyle(
                        color: Colors.white,
                        fontSize: 18,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(height: 4),
                    Text(
                      'Calendario de Citas Prenatales',
                      style: TextStyle(
                        color: Colors.white.withOpacity(0.9),
                        fontSize: 14,
                      ),
                    ),
                  ],
                ),
              ),
            ],
          ),
          
          SizedBox(height: 15),
          
          if (_tieneMestruacion && _fechaMestruacion != null)
            Container(
              padding: EdgeInsets.all(12),
              decoration: BoxDecoration(
                color: Colors.white.withOpacity(0.1),
                borderRadius: BorderRadius.circular(10),
              ),
              child: Row(
                children: [
                  Icon(Icons.calendar_today, color: Colors.white, size: 20),
                  SizedBox(width: 10),
                  Expanded(
                    child: Text(
                      'Última menstruación: ${_formatFecha(_fechaMestruacion!)}',
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                ],
              ),
            ),
        ],
      ),
    );
  }

  String _formatFecha(String fecha) {
    try {
      return DateFormat('dd/MM/yyyy').format(DateTime.parse(fecha));
    } catch (e) {
      return fecha;
    }
  }

  Widget _buildResumenCitas() {
    final totalConsultas = _consultas.length;
    final totalUltrasonidos = _ultrasonidos.length;
    
    // Extraer datos del estado de citas
    int consultasConfirmadas = 0;
    int ultrasonidosConfirmados = 0;
    
    if (_estadoCitas != null) {
      consultasConfirmadas = _estadoCitas!['totalConsultasConfirmadas'] ?? 0;
      ultrasonidosConfirmados = _estadoCitas!['totalUltrasonidosConfirmados'] ?? 0;
    }

    return Card(
      margin: EdgeInsets.all(16),
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Resumen de Citas',
              style: TextStyle(
                fontSize: 18,
                fontWeight: FontWeight.bold,
                color: Colors.purple.shade800,
              ),
            ),
            SizedBox(height: 12),
            
            Row(
              children: [
                _buildStatCard(
                  title: 'Consultas',
                  total: totalConsultas,
                  confirmadas: consultasConfirmadas,
                  color: Colors.blue,
                ),
                SizedBox(width: 12),
                _buildStatCard(
                  title: 'Ultrasonidos',
                  total: totalUltrasonidos,
                  confirmadas: ultrasonidosConfirmados,
                  color: Colors.green,
                ),
              ],
            ),
            
            SizedBox(height: 12),
            
            if (!_tieneMestruacion)
              Container(
                padding: EdgeInsets.all(12),
                decoration: BoxDecoration(
                  color: Colors.orange.shade50,
                  borderRadius: BorderRadius.circular(8),
                  border: Border.all(color: Colors.orange.shade200),
                ),
                child: Row(
                  children: [
                    Icon(Icons.info, color: Colors.orange),
                    SizedBox(width: 10),
                    Expanded(
                      child: Text(
                        'Registra tu última menstruación para calcular tus citas',
                        style: TextStyle(color: Colors.orange.shade800),
                      ),
                    ),
                    TextButton(
                      onPressed: () {
                        Navigator.pushNamed(context, '/registrar-mestruacion');
                      },
                      child: Text('Registrar'),
                    ),
                  ],
                ),
              ),
          ],
        ),
      ),
    );
  }

  Widget _buildStatCard({
    required String title,
    required int total,
    required int confirmadas,
    required Color color,
  }) {
    return Expanded(
      child: Container(
        padding: EdgeInsets.all(12),
        decoration: BoxDecoration(
          color: color.withOpacity(0.1),
          borderRadius: BorderRadius.circular(10),
          border: Border.all(color: color.withOpacity(0.3)),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              title,
              style: TextStyle(
                color: color,
                fontSize: 14,
                fontWeight: FontWeight.w500,
              ),
            ),
            SizedBox(height: 4),
            Text(
              '$confirmadas/${total > 0 ? total : '?'}',
              style: TextStyle(
                fontSize: 20,
                fontWeight: FontWeight.bold,
                color: color,
              ),
            ),
            SizedBox(height: 2),
            Text(
              'confirmadas',
              style: TextStyle(
                fontSize: 12,
                color: color.withOpacity(0.7),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildListaCitas() {
    final todasLasCitas = [..._consultas, ..._ultrasonidos];
    
    // Ordenar por fecha
    todasLasCitas.sort((a, b) {
      final fechaA = _getFechaCita(a);
      final fechaB = _getFechaCita(b);
      return fechaA.compareTo(fechaB);
    });

    return Card(
      margin: EdgeInsets.all(16),
      child: Padding(
        padding: EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Row(
              children: [
                Icon(Icons.calendar_today, color: Colors.purple),
                SizedBox(width: 8),
                Text(
                  'Citas Programadas',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.bold,
                  ),
                ),
              ],
            ),
            
            SizedBox(height: 12),
            
            if (todasLasCitas.isEmpty)
              Container(
                padding: EdgeInsets.all(20),
                child: Column(
                  children: [
                    Icon(
                      Icons.calendar_today,
                      size: 60,
                      color: Colors.grey.shade300,
                    ),
                    SizedBox(height: 12),
                    Text(
                      _tieneMestruacion 
                        ? 'No hay citas programadas aún'
                        : 'Registra tu menstruación para ver tus citas',
                      style: TextStyle(
                        color: Colors.grey,
                        fontSize: 14,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ],
                ),
              ),
            
            if (todasLasCitas.isNotEmpty)
              Column(
                children: todasLasCitas.map((cita) {
                  final esUltrasonido = cita.containsKey('citaUltrasonido');
                  final tipo = esUltrasonido ? 'Ultrasonido' : 'Consulta Prenatal';
                  final fecha = esUltrasonido 
                      ? cita['citaUltrasonido'] 
                      : cita['fechaConsulta'];
                  final semana = cita['semanaEmbarazo']?.toString() ?? 'N/A';
                  final confirmada = cita['confirmada'] ?? false;
                  final id = esUltrasonido 
                      ? cita['idUltrasonido'] 
                      : cita['idConsulta'];
                  
                  return _buildCitaItem(
                    tipo: tipo,
                    fecha: fecha,
                    semana: semana,
                    confirmada: confirmada,
                    esUltrasonido: esUltrasonido,
                    id: id,
                  );
                }).toList(),
              ),
          ],
        ),
      ),
    );
  }

  Widget _buildCitaItem({
    required String tipo,
    required String fecha,
    required String semana,
    required bool confirmada,
    required bool esUltrasonido,
    required dynamic id,
  }) {
    return Container(
      margin: EdgeInsets.only(bottom: 12),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey.shade200),
        borderRadius: BorderRadius.circular(8),
      ),
      child: ListTile(
        leading: CircleAvatar(
          backgroundColor: esUltrasonido 
              ? Colors.orange.shade100 
              : Colors.blue.shade100,
          child: Icon(
            esUltrasonido ? Icons.health_and_safety : Icons.calendar_today,
            color: esUltrasonido ? Colors.orange : Colors.blue,
          ),
        ),
        title: Text('$tipo - Semana $semana'),
        subtitle: Text(_formatFecha(fecha)),
        trailing: confirmada
            ? Chip(
                label: Text(
                  'Confirmada',
                  style: TextStyle(color: Colors.white, fontSize: 12),
                ),
                backgroundColor: Colors.green,
                padding: EdgeInsets.symmetric(horizontal: 8, vertical: 2),
              )
            : OutlinedButton(
                onPressed: () => _confirmarCita(
                  id: id,
                  tipo: esUltrasonido ? 'ultrasonido' : 'prenatal',
                  semana: semana,
                  fecha: fecha,
                ),
                child: Text(
                  'Confirmar',
                  style: TextStyle(fontSize: 12),
                ),
                style: OutlinedButton.styleFrom(
                  padding: EdgeInsets.symmetric(horizontal: 12, vertical: 4),
                ),
              ),
      ),
    );
  }

  DateTime _getFechaCita(Map<String, dynamic> cita) {
    final fechaStr = cita.containsKey('citaUltrasonido') 
        ? cita['citaUltrasonido']
        : cita['fechaConsulta'];
    
    try {
      return DateTime.parse(fechaStr);
    } catch (e) {
      return DateTime.now();
    }
  }

  Future<void> _confirmarCita({
    required dynamic id,
    required String tipo,
    required String semana,
    required String fecha,
  }) async {
    if (_userId == null) return;
    
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Confirmar Cita'),
        content: Text('¿Confirmar cita de $tipo para la semana $semana?'),
        actions: [
          TextButton(
            onPressed: () => Navigator.pop(context),
            child: Text('Cancelar'),
          ),
          ElevatedButton(
            onPressed: () async {
              Navigator.pop(context);
              
              // Convertir semana a rango (ej: "10" → "10-14")
              String rangoSemanas = _convertirSemanaARango(int.tryParse(semana) ?? 0);
              
              final result = await ApiService.confirmarCita(
                userId: _userId!,
                tipoCita: tipo,
                rangoSemanas: rangoSemanas,
                fecha: fecha.split('T')[0], // Solo fecha, sin tiempo
              );
              
              if (result['success'] == true) {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Text('Cita confirmada exitosamente'),
                    backgroundColor: Colors.green,
                  ),
                );
                await _loadCitasData(); // Recargar datos
              } else {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: Text('Error: ${result['message']}'),
                    backgroundColor: Colors.red,
                  ),
                );
              }
            },
            child: Text('Confirmar'),
          ),
        ],
      ),
    );
  }

  String _convertirSemanaARango(int semana) {
    // Lógica para convertir semana a rango según NOM-007
    if (semana >= 0 && semana <= 8) return "0-8";
    if (semana >= 10 && semana <= 14) return "10-14";
    if (semana >= 16 && semana <= 18) return "16-18";
    if (semana == 22) return "22";
    if (semana == 28) return "28";
    if (semana == 32) return "32";
    if (semana == 36) return "36";
    if (semana >= 38 && semana <= 41) return "38-41";
    return "$semana";
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Calendario de Citas'),
        backgroundColor: Colors.purple,
        actions: [
          IconButton(
            icon: Icon(Icons.refresh),
            onPressed: _loadCitasData,
          ),
          PopupMenuButton<String>(
            onSelected: (value) {
              if (value == 'logout') {
                _logout();
              } else if (value == 'perfil') {
                Navigator.pushNamed(context, '/perfil');
              }
            },
            itemBuilder: (context) => [
              PopupMenuItem(
                value: 'perfil',
                child: Row(
                  children: [
                    Icon(Icons.person, size: 20),
                    SizedBox(width: 8),
                    Text('Mi Perfil'),
                  ],
                ),
              ),
              PopupMenuItem(
                value: 'logout',
                child: Row(
                  children: [
                    Icon(Icons.logout, size: 20, color: Colors.red),
                    SizedBox(width: 8),
                    Text('Cerrar Sesión', style: TextStyle(color: Colors.red)),
                  ],
                ),
              ),
            ],
          ),
        ],
      ),
      body: _isLoading
          ? Center(child: CircularProgressIndicator())
          : RefreshIndicator(
              onRefresh: _loadCitasData,
              child: ListView(
                children: [
                  _buildHeader(),
                  _buildResumenCitas(),
                  _buildListaCitas(),
                  SizedBox(height: 20),
                ],
              ),
            ),
      floatingActionButton: FloatingActionButton.extended(
        onPressed: () {
          Navigator.pushNamed(context, '/registrar-mestruacion');
        },
        icon: Icon(Icons.add),
        label: Text(_tieneMestruacion ? 'Actualizar Fecha' : 'Registrar Menstruación'),
        backgroundColor: Colors.purple,
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 0,
        items: [
          BottomNavigationBarItem(
            icon: Icon(Icons.calendar_today),
            label: 'Citas',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.assignment),
            label: 'Cuestionario',
          ),
          BottomNavigationBarItem(
            icon: Icon(Icons.chat),
            label: 'Chatbot',
          ),
        ],
        onTap: (index) {
          switch (index) {
            case 0:
              // Ya estamos en citas
              break;
            case 1:
              Navigator.pushNamed(context, '/cuestionario');
              break;
            case 2:
              Navigator.pushNamed(context, '/chatbot');
              break;
          }
        },
      ),
    );
  }

  Future<void> _logout() async {
    await AuthService.logout();
    Navigator.pushReplacementNamed(context, '/login');
  }
}