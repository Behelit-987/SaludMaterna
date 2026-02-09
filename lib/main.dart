import 'package:flutter/material.dart';
//import 'package:shared_preferences/shared_preferences.dart';
import 'login/login_screen.dart';
import 'login/registro_screen.dart';
import 'screen/calendario_citas_screen.dart';
import 'screen/registrar_mestruacion_screen.dart';
import 'service/authservice.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Salud Materna',
      theme: ThemeData(
        primarySwatch: Colors.purple,
        fontFamily: 'Roboto',
        appBarTheme: AppBarTheme(
          backgroundColor: Colors.purple,
          foregroundColor: Colors.white,
        ),
      ),
      home: FutureBuilder(
        future: _checkInitialRoute(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return Scaffold(
              body: Center(
                child: CircularProgressIndicator(),
              ),
            );
          }
          return snapshot.data ?? LoginScreen();
        },
      ),
      routes: {
        '/login': (context) => LoginScreen(),
        '/registro': (context) => RegisterScreen(),
        '/calendario': (context) => CalendarioCitasScreen(),
        '/registrar-mestruacion': (context) => RegistrarMestruacionScreen(),
        // Agrega más rutas según necesites
      },
    );
  }

  Future<Widget?> _checkInitialRoute() async {
    final authStatus = await AuthService.checkAuth();
    
    if (authStatus['isLoggedIn'] == true) {
      final tieneMestruacion = authStatus['tieneMestruacion'] ?? false;
      
      if (tieneMestruacion) {
        return CalendarioCitasScreen();
      } else {
        return RegistrarMestruacionScreen();
      }
    }
    
    return null; // Ir a LoginScreen
  }
}