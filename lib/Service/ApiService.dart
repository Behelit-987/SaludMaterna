import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:shared_preferences/shared_preferences.dart';

class ApiService {
  static const String baseUrl = "http://10.0.2.2:8080/api/v1";
  
  static Future<String?> _getToken() async {
    final prefs = await SharedPreferences.getInstance();
    return prefs.getString('token');
  }
  
  // Headers con token
  static Future<Map<String, String>> _getHeaders() async {
    final token = await _getToken();
    final headers = {
      'Content-Type': 'application/json',
    };
    
    if (token != null) {
      headers['Authorization'] = 'Bearer $token';
    }
    
    return headers;
  }
  
  // ========== ENDPOINTS DE CITAS ==========
  
  // 1. Obtener todas las citas (consultas + ultrasonidos)
  static Future<Map<String, dynamic>> getCitas(int userId) async {
    try {
      final url = Uri.parse('$baseUrl/citas/consultas/$userId');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 2. Obtener ultrasonidos específicos
  static Future<Map<String, dynamic>> getUltrasonidos(int userId) async {
    try {
      final url = Uri.parse('$baseUrl/citas/ultrasonidos/$userId');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 3. Registrar última menstruación
  static Future<Map<String, dynamic>> registrarMestruacion(
    int userId, 
    String fecha
  ) async {
    try {
      final url = Uri.parse('$baseUrl/citas/registrar-mestruacion');
      final headers = await _getHeaders();
      final body = json.encode({
        'userId': userId,
        'fechaMestruacion': fecha,
      });
      
      final response = await http.post(
        url,
        headers: headers,
        body: body,
      );
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 4. Obtener información de menstruación
  static Future<Map<String, dynamic>> getMestruacion(int userId) async {
    try {
      final url = Uri.parse('$baseUrl/citas/mestruacion/$userId');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 5. Confirmar una cita
  static Future<Map<String, dynamic>> confirmarCita({
    required int userId,
    required String tipoCita,
    required String rangoSemanas,
    required String fecha,
  }) async {
    try {
      final url = Uri.parse('$baseUrl/citas/confirmar');
      final headers = await _getHeaders();
      final body = json.encode({
        'userId': userId,
        'tipoCita': tipoCita,
        'rangoSemanas': rangoSemanas,
        'fechaSeleccionada': fecha,
      });
      
      final response = await http.post(
        url,
        headers: headers,
        body: body,
      );
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 6. Obtener estado de citas confirmadas
  static Future<Map<String, dynamic>> getEstadoCitas(int userId) async {
    try {
      final url = Uri.parse('$baseUrl/citas/estado/$userId');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // ========== ENDPOINTS DE CUESTIONARIO ==========
  
  // 7. Guardar cuestionario
  static Future<Map<String, dynamic>> guardarCuestionario(
    Map<String, dynamic> cuestionario
  ) async {
    try {
      final url = Uri.parse('$baseUrl/cuestionario/guardar');
      final headers = await _getHeaders();
      final body = json.encode(cuestionario);
      
      final response = await http.post(
        url,
        headers: headers,
        body: body,
      );
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 8. Obtener cuestionario
  static Future<Map<String, dynamic>> getCuestionario() async {
    try {
      final url = Uri.parse('$baseUrl/cuestionario/obtener');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 9. Verificar si existe cuestionario
  static Future<Map<String, dynamic>> existeCuestionario() async {
    try {
      final url = Uri.parse('$baseUrl/cuestionario/existe');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 10. Obtener resumen del cuestionario
  static Future<Map<String, dynamic>> getResumenCuestionario() async {
    try {
      final url = Uri.parse('$baseUrl/cuestionario/resumen');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // ========== ENDPOINTS DE USUARIO ==========
  
  // 11. Obtener datos de usuario
  static Future<Map<String, dynamic>> getUsuaria(int userId) async {
    try {
      final url = Uri.parse('$baseUrl/usuaria/$userId');
      final headers = await _getHeaders();
      
      final response = await http.get(url, headers: headers);
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // 12. Actualizar contraseña
  static Future<Map<String, dynamic>> actualizarPassword({
    required String email,
    required String passwordActual,
    required String nuevaPassword,
  }) async {
    try {
      final url = Uri.parse('$baseUrl/actualizar-password');
      final headers = await _getHeaders();
      final body = json.encode({
        'email': email,
        'passwordActual': passwordActual,
        'nuevaPassword': nuevaPassword,
      });
      
      final response = await http.post(
        url,
        headers: headers,
        body: body,
      );
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // ========== ENDPOINTS DE CHATBOT ==========
  
  // 13. Enviar pregunta al chatbot
  static Future<Map<String, dynamic>> consultarChatbot({
    required String pregunta,
    required String categoria,
  }) async {
    try {
      final url = Uri.parse('$baseUrl/chatbot/responder');
      final headers = await _getHeaders();
      final body = json.encode({
        'pregunta': pregunta,
        'categoria': categoria,
      });
      
      final response = await http.post(
        url,
        headers: headers,
        body: body,
      );
      
      if (response.statusCode == 200) {
        return json.decode(response.body);
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
  
  // ========== MÉTODOS AUXILIARES ==========
  
  
  // Verificar si el usuario está autenticado
  static Future<bool> isAuthenticated() async {
    final token = await _getToken();
    if (token == null) return false;
    
    try {
      // Podrías verificar el token con el backend
      // Por ahora, solo verificamos que existe
      return true;
    } catch (e) {
      return false;
    }
  }
  
  // Manejar errores de API
  static String getErrorMessage(dynamic error) {
    if (error is String) {
      return error;
    } else if (error is Map<String, dynamic>) {
      return error['message'] ?? 'Error desconocido';
    } else {
      return 'Error de conexión';
    }
  }
}