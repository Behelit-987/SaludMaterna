import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class AuthService {
  static const String baseUrl = "http://10.0.2.2:8080/api/v1";
  
  // Login
  static Future<Map<String, dynamic>> login(String email, String password) async {
    try {
      final url = Uri.parse('$baseUrl/login');
      final body = json.encode({
        'email': email,
        'password': password,
      });
      
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
        },
        body: body,
      );
      
      print("Login response: ${response.body}");
      
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        
        if (data['success'] == true) {
          // Guardar datos localmente
          final prefs = await SharedPreferences.getInstance();
          await prefs.setString('token', data['token']);
          await prefs.setString('user_data', json.encode(data['user']));
          await prefs.setString('email', email);
          await prefs.setBool('tiene_mestruacion', data['tieneMestruacionRegistrada'] ?? false);
          await prefs.setString('pantalla_inicial', data['pantallaInicial'] ?? 'home');
          
          // Guardar info de citas si existe
          if (data['citas'] != null) {
            await prefs.setString('citas_info', json.encode(data['citas']));
          }
          
          return {
            'success': true,
            'data': data,
            'pantallaInicial': data['pantallaInicial'],
            'mensaje': data['pantallaInicialMensaje'],
          };
        } else {
          return {
            'success': false,
            'message': data['message'],
          };
        }
      } else {
        return {
          'success': false,
          'message': 'Error ${response.statusCode}',
        };
      }
    } catch (e) {
      return {
        'success': false,
        'message': 'Error de conexión: $e',
      };
    }
  }
  
  // Registro
  static Future<Map<String, dynamic>> registro(Map<String, dynamic> userData) async {
    try {
      final url = Uri.parse('$baseUrl/registro');
      final body = json.encode(userData);
      
      final response = await http.post(
        url,
        headers: {
          'Content-Type': 'application/json',
        },
        body: body,
      );
      
      print("Registro response: ${response.body}");
      
      if (response.statusCode == 200) {
        final data = json.decode(response.body);
        
        if (data['success'] == true) {
          // Guardar datos localmente
          final prefs = await SharedPreferences.getInstance();
          await prefs.setString('token', data['token']);
          await prefs.setString('user_data', json.encode(data['user']));
          await prefs.setString('email', userData['email']);
          await prefs.setBool('tiene_mestruacion', false); // Recién registrado
          await prefs.setString('pantalla_inicial', 'registrar_mestruacion');
          
          return {
            'success': true,
            'data': data,
            'pantallaInicial': 'registrar_mestruacion',
            'mensaje': 'Registro exitoso. Ahora registra tu última menstruación.',
          };
        } else {
          return {
            'success': false,
            'message': data['message'],
          };
        }
      } else {
        return {
          'success': false,
          'message': 'Error ${response.statusCode}',
        };
      }
    } catch (e) {
      return {
        'success': false,
        'message': 'Error: $e',
      };
    }
  }

  static Future<Map<String, dynamic>> register({
    required String email,
    required String password,
    required String nombres,
    required String apellidos,
    required String fechaNacimiento,
    required String curp,
    required String celular,
    String domicilio = '',
    String emergencia = '',
    String talla = '',
    String peso = '',
  }) async {
    try {
      // Convertir talla y peso a números (Float para Java)
      double? tallaNum = talla.isNotEmpty ? double.tryParse(talla)?.toDouble() : null;
      double? pesoNum = peso.isNotEmpty ? double.tryParse(peso)?.toDouble() : null;
      
      // Crear el body del request
      Map<String, dynamic> requestBody = {
        'email': email,
        'password': password,
        'nombres': nombres,
        'apellidos': apellidos,
        'fechaNacimiento': fechaNacimiento,
        'domicilio': domicilio,
        'curp': curp,
        'numCelular': celular,
        'numEmergencia': emergencia,
      };
      
      // Agregar talla y peso solo si no están vacíos
      if (tallaNum != null) requestBody['talla'] = tallaNum;
      if (pesoNum != null) requestBody['peso'] = pesoNum;
      
      final response = await http.post(
        Uri.parse('$baseUrl/api/v1/register'),
        headers: {'Content-Type': 'application/json'},
        body: json.encode(requestBody),
      );

      if (response.statusCode == 201 || response.statusCode == 200) {
        final data = json.decode(response.body);
        return {
          'success': true,
          'message': data['message'] ?? 'Registro exitoso',
          'user': data['usuaria'] ?? data['user'],
        };
      } else {
        try {
          final error = json.decode(response.body);
          return {
            'success': false,
            'message': error['message'] ?? 'Error en el registro',
          };
        } catch (_) {
          return {
            'success': false,
            'message': 'Error en el servidor (${response.statusCode})',
          };
        }
      }
    } catch (e) {
      return {
        'success': false,
        'message': 'Error de conexión: $e',
      };
    }
  }
  
  // Verificar si ya hay un usuario loggeado
  static Future<Map<String, dynamic>> checkAuth() async {
    final prefs = await SharedPreferences.getInstance();
    final token = prefs.getString('token');
    final userData = prefs.getString('user_data');
    
    if (token != null && userData != null) {
      final user = json.decode(userData);
      final tieneMestruacion = prefs.getBool('tiene_mestruacion') ?? false;
      final pantallaInicial = prefs.getString('pantalla_inicial') ?? 'home';
      
      return {
        'success': true,
        'isLoggedIn': true,
        'user': user,
        'tieneMestruacion': tieneMestruacion,
        'pantallaInicial': pantallaInicial,
      };
    }
    
    return {
      'success': true,
      'isLoggedIn': false,
    };
  }
  
  // Cerrar sesión
  static Future<void> logout() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.remove('token');
    await prefs.remove('user_data');
    await prefs.remove('email');
    await prefs.remove('tiene_mestruacion');
    await prefs.remove('pantalla_inicial');
    await prefs.remove('citas_info');
  }
  
  // Obtener datos del usuario actual
  static Future<Map<String, dynamic>> getCurrentUser() async {
    final prefs = await SharedPreferences.getInstance();
    final userData = prefs.getString('user_data');
    
    if (userData != null) {
      return json.decode(userData);
    }
    
    return {};
  }
  
  // Obtener ID del usuario actual
  static Future<int?> getCurrentUserId() async {
    final user = await getCurrentUser();
    return user['id'];
  }
}