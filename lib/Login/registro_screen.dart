import 'package:flutter/material.dart';
import 'package:maternidad/service/authservice.dart';

class RegisterScreen extends StatefulWidget {
  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController = TextEditingController();
  final TextEditingController _nombresController = TextEditingController();
  final TextEditingController _apellidosController = TextEditingController();
  final TextEditingController _fechaNacimientoController = TextEditingController();
  final TextEditingController _domicilioController = TextEditingController();
  final TextEditingController _curpController = TextEditingController();
  final TextEditingController _celularController = TextEditingController();
  final TextEditingController _emergenciaController = TextEditingController();
  final TextEditingController _tallaController = TextEditingController();
  final TextEditingController _pesoController = TextEditingController();

  bool _isLoading = false;
  bool _obscurePassword = true;
  bool _obscureConfirmPassword = true;
  String? _errorMessage;
  DateTime? _selectedDate;

  @override
  void initState() {
    super.initState();
  }

  Future<void> _selectDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: DateTime.now(),
      firstDate: DateTime(1900),
      lastDate: DateTime.now(),
      initialDatePickerMode: DatePickerMode.year,
      helpText: 'Selecciona tu fecha de nacimiento',
      cancelText: 'Cancelar',
      confirmText: 'Seleccionar',
      fieldLabelText: 'Fecha de nacimiento',
      fieldHintText: 'DD/MM/AAAA',
    );
    
    if (picked != null && picked != _selectedDate) {
      setState(() {
        _selectedDate = picked;
        _fechaNacimientoController.text = 
            "${picked.day.toString().padLeft(2, '0')}/"
            "${picked.month.toString().padLeft(2, '0')}/"
            "${picked.year}";
      });
    }
  }

  bool _validateFields() {
    if (_emailController.text.isEmpty ||
        _passwordController.text.isEmpty ||
        _confirmPasswordController.text.isEmpty ||
        _nombresController.text.isEmpty ||
        _apellidosController.text.isEmpty ||
        _fechaNacimientoController.text.isEmpty ||
        _curpController.text.isEmpty ||
        _celularController.text.isEmpty) {
      setState(() {
        _errorMessage = 'Por favor completa todos los campos obligatorios';
      });
      return false;
    }

    if (_passwordController.text != _confirmPasswordController.text) {
      setState(() {
        _errorMessage = 'Las contraseñas no coinciden';
      });
      return false;
    }

    if (_passwordController.text.length < 6) {
      setState(() {
        _errorMessage = 'La contraseña debe tener al menos 6 caracteres';
      });
      return false;
    }

    // Validar formato de email básico
    final emailRegex = RegExp(r'^[^@]+@[^@]+\.[^@]+');
    if (!emailRegex.hasMatch(_emailController.text)) {
      setState(() {
        _errorMessage = 'Ingresa un email válido';
      });
      return false;
    }

    return true;
  }

  Future<void> _register() async {
    if (!_validateFields()) {
      return;
    }

    setState(() {
      _isLoading = true;
      _errorMessage = null;
    });

    // Formatear fecha para el backend (AAAA-MM-DD)
    String fechaNacimiento = '';
    if (_selectedDate != null) {
      fechaNacimiento = "${_selectedDate!.year}-"
          "${_selectedDate!.month.toString().padLeft(2, '0')}-"
          "${_selectedDate!.day.toString().padLeft(2, '0')}";
    }

    final result = await AuthService.register(
      email: _emailController.text,
      password: _passwordController.text,
      nombres: _nombresController.text,
      apellidos: _apellidosController.text,
      fechaNacimiento: fechaNacimiento,
      domicilio: _domicilioController.text,
      curp: _curpController.text,
      celular: _celularController.text,
      emergencia: _emergenciaController.text,
      talla: _tallaController.text,
      peso: _pesoController.text,
    );

    setState(() {
      _isLoading = false;
    });

    if (result['success'] == true) {
      // Mostrar mensaje de éxito
      _showSuccessMessage(result['message'] ?? 'Registro exitoso');
      
      // Navegar al login después de un breve delay
      Future.delayed(Duration(seconds: 2), () {
        Navigator.pushReplacementNamed(context, '/login');
      });
    } else {
      setState(() {
        _errorMessage = result['message'] ?? 'Error en el registro';
      });
    }
  }

  void _showSuccessMessage(String mensaje) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(mensaje),
        backgroundColor: Colors.green,
        duration: Duration(seconds: 3),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topCenter,
            end: Alignment.bottomCenter,
            colors: [
              Colors.purple.shade100,
              Colors.white,
            ],
          ),
        ),
        child: SafeArea(
          child: Center(
            child: SingleChildScrollView(
              padding: EdgeInsets.all(16),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  // Logo/Icono
                  Container(
                    width: 100,
                    height: 100,
                    decoration: BoxDecoration(
                      color: Colors.purple.shade50,
                      shape: BoxShape.circle,
                      border: Border.all(color: Colors.purple.shade200, width: 2),
                    ),
                    child: Icon(
                      Icons.person_add,
                      size: 50,
                      color: Colors.purple,
                    ),
                  ),
                  
                  SizedBox(height: 24),
                  
                  // Título
                  Text(
                    'Crear Cuenta',
                    style: TextStyle(
                      fontSize: 28,
                      fontWeight: FontWeight.bold,
                      color: Colors.purple.shade800,
                    ),
                  ),
                  
                  SizedBox(height: 8),
                  
                  Text(
                    'Completa tus datos para registrarte',
                    style: TextStyle(
                      fontSize: 14,
                      color: Colors.grey.shade600,
                    ),
                    textAlign: TextAlign.center,
                  ),
                  
                  SizedBox(height: 24),
                  
                  // Card del formulario
                  Card(
                    elevation: 8,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(16),
                    ),
                    child: Padding(
                      padding: EdgeInsets.all(20),
                      child: Column(
                        children: [
                          // Mensaje de error
                          if (_errorMessage != null)
                            Container(
                              width: double.infinity,
                              padding: EdgeInsets.all(12),
                              decoration: BoxDecoration(
                                color: Colors.red.shade50,
                                borderRadius: BorderRadius.circular(8),
                                border: Border.all(color: Colors.red.shade200),
                              ),
                              child: Row(
                                children: [
                                  Icon(Icons.error_outline, color: Colors.red, size: 20),
                                  SizedBox(width: 8),
                                  Expanded(
                                    child: Text(
                                      _errorMessage!,
                                      style: TextStyle(color: Colors.red),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          
                          if (_errorMessage != null) SizedBox(height: 16),
                          
                          // Campos del formulario en columnas
                          Column(
                            children: [
                              // Nombres y Apellidos en fila
                              Row(
                                children: [
                                  Expanded(
                                    child: TextFormField(
                                      controller: _nombresController,
                                      decoration: InputDecoration(
                                        labelText: 'Nombres*',
                                        prefixIcon: Icon(Icons.person, color: Colors.purple),
                                        border: OutlineInputBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                        focusedBorder: OutlineInputBorder(
                                          borderSide: BorderSide(color: Colors.purple),
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                    ),
                                  ),
                                  SizedBox(width: 10),
                                  Expanded(
                                    child: TextFormField(
                                      controller: _apellidosController,
                                      decoration: InputDecoration(
                                        labelText: 'Apellidos*',
                                        prefixIcon: Icon(Icons.person_outline, color: Colors.purple),
                                        border: OutlineInputBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                        focusedBorder: OutlineInputBorder(
                                          borderSide: BorderSide(color: Colors.purple),
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Email
                              TextFormField(
                                controller: _emailController,
                                decoration: InputDecoration(
                                  labelText: 'Email*',
                                  prefixIcon: Icon(Icons.email, color: Colors.purple),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(color: Colors.purple),
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                ),
                                keyboardType: TextInputType.emailAddress,
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Fecha de Nacimiento
                              TextFormField(
                                controller: _fechaNacimientoController,
                                readOnly: true,
                                decoration: InputDecoration(
                                  labelText: 'Fecha de Nacimiento*',
                                  prefixIcon: Icon(Icons.calendar_today, color: Colors.purple),
                                  suffixIcon: IconButton(
                                    icon: Icon(Icons.calendar_month, color: Colors.grey),
                                    onPressed: () => _selectDate(context),
                                  ),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(color: Colors.purple),
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                ),
                                onTap: () => _selectDate(context),
                              ),
                              
                              SizedBox(height: 16),
                              
                              // CURP
                              TextFormField(
                                controller: _curpController,
                                decoration: InputDecoration(
                                  labelText: 'CURP*',
                                  prefixIcon: Icon(Icons.badge, color: Colors.purple),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(color: Colors.purple),
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                ),
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Celular y Emergencia en fila
                              Row(
                                children: [
                                  Expanded(
                                    child: TextFormField(
                                      controller: _celularController,
                                      decoration: InputDecoration(
                                        labelText: 'Celular*',
                                        prefixIcon: Icon(Icons.phone, color: Colors.purple),
                                        border: OutlineInputBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                        focusedBorder: OutlineInputBorder(
                                          borderSide: BorderSide(color: Colors.purple),
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                      keyboardType: TextInputType.phone,
                                    ),
                                  ),
                                  SizedBox(width: 10),
                                  Expanded(
                                    child: TextFormField(
                                      controller: _emergenciaController,
                                      decoration: InputDecoration(
                                        labelText: 'Emergencia',
                                        prefixIcon: Icon(Icons.emergency, color: Colors.purple),
                                        border: OutlineInputBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                        focusedBorder: OutlineInputBorder(
                                          borderSide: BorderSide(color: Colors.purple),
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                      keyboardType: TextInputType.phone,
                                    ),
                                  ),
                                ],
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Domicilio
                              TextFormField(
                                controller: _domicilioController,
                                decoration: InputDecoration(
                                  labelText: 'Domicilio',
                                  prefixIcon: Icon(Icons.home, color: Colors.purple),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(color: Colors.purple),
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                ),
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Talla y Peso en fila
                              Row(
                                children: [
                                  Expanded(
                                    child: TextFormField(
                                      controller: _tallaController,
                                      decoration: InputDecoration(
                                        labelText: 'Talla (M)',
                                        prefixIcon: Icon(Icons.height, color: Colors.purple),
                                        border: OutlineInputBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                        focusedBorder: OutlineInputBorder(
                                          borderSide: BorderSide(color: Colors.purple),
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                      keyboardType: TextInputType.number,
                                    ),
                                  ),
                                  SizedBox(width: 10),
                                  Expanded(
                                    child: TextFormField(
                                      controller: _pesoController,
                                      decoration: InputDecoration(
                                        labelText: 'Peso (kg)',
                                        prefixIcon: Icon(Icons.monitor_weight, color: Colors.purple),
                                        border: OutlineInputBorder(
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                        focusedBorder: OutlineInputBorder(
                                          borderSide: BorderSide(color: Colors.purple),
                                          borderRadius: BorderRadius.circular(10),
                                        ),
                                      ),
                                      keyboardType: TextInputType.number,
                                    ),
                                  ),
                                ],
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Contraseña
                              TextFormField(
                                controller: _passwordController,
                                decoration: InputDecoration(
                                  labelText: 'Contraseña*',
                                  prefixIcon: Icon(Icons.lock, color: Colors.purple),
                                  suffixIcon: IconButton(
                                    icon: Icon(
                                      _obscurePassword ? Icons.visibility : Icons.visibility_off,
                                      color: Colors.grey,
                                    ),
                                    onPressed: () {
                                      setState(() {
                                        _obscurePassword = !_obscurePassword;
                                      });
                                    },
                                  ),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(color: Colors.purple),
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                ),
                                obscureText: _obscurePassword,
                              ),
                              
                              SizedBox(height: 16),
                              
                              // Confirmar Contraseña
                              TextFormField(
                                controller: _confirmPasswordController,
                                decoration: InputDecoration(
                                  labelText: 'Confirmar Contraseña*',
                                  prefixIcon: Icon(Icons.lock_reset, color: Colors.purple),
                                  suffixIcon: IconButton(
                                    icon: Icon(
                                      _obscureConfirmPassword ? Icons.visibility : Icons.visibility_off,
                                      color: Colors.grey,
                                    ),
                                    onPressed: () {
                                      setState(() {
                                        _obscureConfirmPassword = !_obscureConfirmPassword;
                                      });
                                    },
                                  ),
                                  border: OutlineInputBorder(
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                  focusedBorder: OutlineInputBorder(
                                    borderSide: BorderSide(color: Colors.purple),
                                    borderRadius: BorderRadius.circular(10),
                                  ),
                                ),
                                obscureText: _obscureConfirmPassword,
                              ),
                              
                              SizedBox(height: 24),
                              
                              // Botón de Registro
                              SizedBox(
                                width: double.infinity,
                                height: 50,
                                child: ElevatedButton(
                                  onPressed: _isLoading ? null : _register,
                                  style: ElevatedButton.styleFrom(
                                    backgroundColor: Colors.purple,
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(10),
                                    ),
                                    elevation: 2,
                                  ),
                                  child: _isLoading
                                      ? SizedBox(
                                          width: 24,
                                          height: 24,
                                          child: CircularProgressIndicator(
                                            color: Colors.white,
                                            strokeWidth: 2,
                                          ),
                                        )
                                      : Text(
                                          'Registrarse',
                                          style: TextStyle(
                                            fontSize: 16,
                                            fontWeight: FontWeight.bold,
                                          ),
                                        ),
                                ),
                              ),
                            ],
                          ),
                        ],
                      ),
                    ),
                  ),
                  
                  SizedBox(height: 24),
                  
                  // Enlace a Login
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text(
                        '¿Ya tienes cuenta? ',
                        style: TextStyle(color: Colors.grey.shade600),
                      ),
                      GestureDetector(
                        onTap: () {
                          Navigator.pushReplacementNamed(context, '/login');
                        },
                        child: Text(
                          'Inicia Sesión',
                          style: TextStyle(
                            color: Colors.purple,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                      ),
                    ],
                  ),
                  
                  SizedBox(height: 20),
                  
                  // Nota sobre campos obligatorios
                  Padding(
                    padding: EdgeInsets.symmetric(horizontal: 20),
                    child: Text(
                      '* Campos obligatorios',
                      style: TextStyle(
                        fontSize: 12,
                        color: Colors.grey.shade600,
                        fontStyle: FontStyle.italic,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}