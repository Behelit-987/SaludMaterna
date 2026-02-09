package mx.edu.uacm.ws.Service;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import mx.edu.uacm.ws.DTO.TodosExamenesDTO;
import mx.edu.uacm.ws.Entity.*;
import mx.edu.uacm.ws.Entity.ConocimientoGeneral.Seguro;
import mx.edu.uacm.ws.Repository.DatosConsultaPrenatalRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PDFService {

    @Autowired
    private UsuariaService usuariaService;
    
    @Autowired
    private ConocimientoService conocimientoService;
    
    @Autowired
    private DatosConsultaPrenatalRepository datosConsultaService;
    
    @Autowired
    private TodosExamenesService todosExamenesService; 
   
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public byte[] generarInformeCompleto(Long idUser) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        Optional<Usuaria> usuariaOpt = usuariaService.findById(idUser);
        if (usuariaOpt.isEmpty()) {
            throw new RuntimeException("Usuaria no encontrada");
        }
        Usuaria usuaria = usuariaOpt.get();
        
        Optional<ConocimientoGeneral> conocimientoOpt = conocimientoService.findByUserId(idUser);
        List<DatosConsultaPrenatal> consultas = datosConsultaService.findByIdUserId(idUser);
        
        TodosExamenesDTO todosExamenesDTO = todosExamenesService.obtenerTodosExamenesPorUsuario(idUser);
        
        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        
        agregarEncabezado(document, usuaria);
        
        agregarSeccionDatosPersonales(document, usuaria);
        
        if (conocimientoOpt.isPresent()) {
            agregarSeccionConocimientoGeneral(document, conocimientoOpt.get());
        }
        
        if (!consultas.isEmpty()) {
            agregarSeccionConsultasPrenatales(document, consultas);
        }
        
        agregarSeccionExamenesLaboratorio(document, todosExamenesDTO);
        
        agregarPiePagina(document);
        
        document.close();
        return baos.toByteArray();
    }
    
    private void agregarEncabezado(Document document, Usuaria usuaria) {
        Paragraph titulo = new Paragraph("INFORME MEDICO DE SALUD MATERNA")
            .setBold()
            .setFontSize(18)
            .setTextAlignment(TextAlignment.CENTER)
            .setMarginBottom(10);
        document.add(titulo);
        
        Table encabezado = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
        encabezado.setWidth(UnitValue.createPercentValue(100));
        encabezado.setMarginBottom(20);
        
        encabezado.addCell(crearCeldaEncabezado("Paciente:"));
        encabezado.addCell(crearCeldaValor(usuaria.getNombreCompleto()));
        
        encabezado.addCell(crearCeldaEncabezado("CURP:"));
        encabezado.addCell(crearCeldaValor(usuaria.getCurp()));
        
        encabezado.addCell(crearCeldaEncabezado("Fecha de emisión:"));
        encabezado.addCell(crearCeldaValor(
            java.time.LocalDate.now().format(DATE_FORMATTER)));
        
        document.add(encabezado);
    }
    
    private void agregarSeccionDatosPersonales(Document document, Usuaria usuaria) {
        document.add(new Paragraph("1. DATOS PERSONALES")
            .setBold()
            .setFontSize(14)
            .setMarginTop(15));
        
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{35, 65}));
        tabla.setWidth(UnitValue.createPercentValue(100));
        
        agregarFila(tabla, "Nombre completo:", usuaria.getNombreCompleto());
        agregarFila(tabla, "Fecha de nacimiento:", 
            usuaria.getFechaNacimiento() != null ? 
            usuaria.getFechaNacimiento().format(DATE_FORMATTER) : "No especificado");
        agregarFila(tabla, "Edad:", calcularEdad(usuaria.getFechaNacimiento()));
        agregarFila(tabla, "CURP:", usuaria.getCurp());
        agregarFila(tabla, "Domicilio:", usuaria.getDomicilio());
        agregarFila(tabla, "Teléfono:", usuaria.getNumCelular());
        agregarFila(tabla, "Teléfono de emergencia:", usuaria.getNumEmergencia());
        agregarFila(tabla, "Peso inicial:", 
            usuaria.getPeso() != null ? usuaria.getPeso() + " kg" : "No especificado");
        agregarFila(tabla, "Talla:", 
            usuaria.getTalla() != null ? usuaria.getTalla() + " m" : "No especificado");
        
        document.add(tabla);
    }
    
    private void agregarSeccionConocimientoGeneral(Document document, ConocimientoGeneral conocimiento) {
        document.add(new Paragraph("\n2. INFORMACIÓN MÉDICA GENERAL")
            .setBold()
            .setFontSize(14)
            .setMarginTop(10));
        
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{40, 60}));
        tabla.setWidth(UnitValue.createPercentValue(100));
        
        String grupoSanguineo = "No especificado";
        String rh = "No especificado";
        if (conocimiento.getGrupoSanguineo() != null) {
            grupoSanguineo = conocimiento.getGrupoSanguineo().toString();
        }
        if (conocimiento.getRh() != null) {
            rh = conocimiento.getRh().toString();
        }
        
        agregarFila(tabla, "Grupo sanguíneo:", grupoSanguineo + " " + rh);
        
        String seguroInfo = "No especificado";
        if (conocimiento.getSeguroSocial() != null) {
            seguroInfo = conocimiento.getSeguroSocial().toString();
            
            if ("Ninguna".equals(seguroInfo)) {
            }
            else if (Seguro.Otra.equals(conocimiento.getSeguroSocial()) && 
                    conocimiento.getOtroSeguro() != null) {
                seguroInfo += " - " + conocimiento.getOtroSeguro();
            }
        }
        agregarFila(tabla, "Seguro social:", seguroInfo);
        
        if (conocimiento.getSeguroSocial() != null && 
            !"Ninguna".equals(conocimiento.getSeguroSocial().toString()) &&
            conocimiento.getNumAfiliacion() != null && 
            !conocimiento.getNumAfiliacion().isEmpty()) {
            agregarFila(tabla, "N° de afiliación:", conocimiento.getNumAfiliacion());
        }
        
        if (conocimiento.getN1Lugar() != null && !conocimiento.getN1Lugar().isEmpty()) {
            agregarFila(tabla, "Unidad médica:", conocimiento.getN1Lugar());
        }
        
        document.add(tabla);
    }

    
    private void agregarSeccionConsultasPrenatales(Document document, List<DatosConsultaPrenatal> consultas) {
        document.add(new Paragraph("\n3. CONSULTAS PRENATALES")
            .setBold()
            .setFontSize(14)
            .setMarginTop(10));
        
        for (int i = 0; i < consultas.size(); i++) {
            DatosConsultaPrenatal consulta = consultas.get(i);
            
            document.add(new Paragraph("Consulta #" + (i + 1) + " - " + 
                (consulta.getFechaRegistro() != null ? 
                 consulta.getFechaRegistro().format(DATE_FORMATTER) : "Sin fecha"))
                .setBold()
                .setMarginTop(5));
            
            Table tablaConsulta = new Table(UnitValue.createPercentArray(new float[]{35, 65}));
            tablaConsulta.setWidth(UnitValue.createPercentValue(100));
            
            agregarFila(tablaConsulta, "Peso:", 
                consulta.getPeso() != null ? consulta.getPeso() + " kg" : "No registrado");
            agregarFila(tablaConsulta, "IMC:", 
                consulta.getImc() != null ? String.format("%.2f", consulta.getImc()) : "No registrado");
            agregarFila(tablaConsulta, "Presión arterial:", 
                consulta.getPresionArterial() != null ? consulta.getPresionArterial() + " mmHg" : "No registrada");
            agregarFila(tablaConsulta, "Fondo uterino:", 
                consulta.getFondoUterino() != null ? consulta.getFondoUterino() + " cm" : "No registrado");
            agregarFila(tablaConsulta, "Frecuencia fetal:", 
                consulta.getFrecuenciaFetal() != null ? consulta.getFrecuenciaFetal() + " lpm" : "No registrada");
            
            if (consulta.getMedicamentos() != null && !consulta.getMedicamentos().isEmpty()) {
                agregarFila(tablaConsulta, "Medicamentos:", consulta.getMedicamentos());
            }
            
            if (consulta.getDatosGenerales() != null && !consulta.getDatosGenerales().isEmpty()) {
                agregarFila(tablaConsulta, "Observaciones:", consulta.getDatosGenerales());
            }
            
            document.add(tablaConsulta);
        }
    }
    
    private void agregarSeccionExamenesLaboratorio(Document document, TodosExamenesDTO todosExamenesDTO) {
        document.add(new Paragraph("\n4. EXÁMENES DE LABORATORIO")
            .setBold()
            .setFontSize(14)
            .setMarginTop(10));
        
        agregarSubseccionExamenes(document, "GLUCOSA EN AYUNAS", todosExamenesDTO.getGlucosas());
        agregarSubseccionExamenes(document, "BIOMETRÍA HEMÁTICA", todosExamenesDTO.getBiometricas());
        agregarSubseccionExamenes(document, "EXAMEN GENERAL DE ORINA (EGO)", todosExamenesDTO.getEgos());
        agregarSubseccionExamenes(document, "UROCULTIVO", todosExamenesDTO.getUrocultivos());
        agregarSubseccionExamenes(document, "VDRL (SÍFILIS)", todosExamenesDTO.getVdrls());
        agregarSubseccionExamenes(document, "DETECCIÓN DE VIH Y HEPATITIS B", todosExamenesDTO.getVihs());
        agregarSubseccionExamenes(document, "CREATININA", todosExamenesDTO.getCreatininas());
        agregarSubseccionExamenes(document, "ÁCIDO ÚRICO", todosExamenesDTO.getAcidos());
        agregarSubseccionExamenes(document, "CITOLOGÍA CERVICOVAGINAL", todosExamenesDTO.getCitologias());
        
        agregarSubseccionOtrosExamenes(document, "OTROS EXÁMENES", todosExamenesDTO.getOtrosExamenes());
    }
    
    private void agregarSubseccionExamenes(Document document, String titulo, List<TodosExamenesDTO.ExamenRegistroDTO> examenes) {
        if (examenes == null || examenes.isEmpty()) {
            return;
        }
        
        examenes.sort((e1, e2) -> {
            if (e1.getFecha() == null && e2.getFecha() == null) return 0;
            if (e1.getFecha() == null) return 1;
            if (e2.getFecha() == null) return -1;
            return e2.getFecha().compareTo(e1.getFecha()); 
        });
        
        document.add(new Paragraph(titulo)
            .setBold()
            .setFontSize(12)
            .setMarginTop(10));
        
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{30, 70}));
        tabla.setWidth(UnitValue.createPercentValue(100));
        tabla.setMarginBottom(15);
        
        tabla.addHeaderCell(crearCeldaEncabezadoTabla("Fecha"));
        tabla.addHeaderCell(crearCeldaEncabezadoTabla("Resultado"));
        
        for (TodosExamenesDTO.ExamenRegistroDTO examen : examenes) {
            String fecha = examen.getFecha() != null ? 
                examen.getFecha().format(DATE_FORMATTER) : "Sin fecha";
            String resultado = examen.getResultado() != null && !examen.getResultado().trim().isEmpty() ? 
                examen.getResultado() : "Sin resultado";
            
            tabla.addCell(crearCeldaValor(fecha));
            tabla.addCell(crearCeldaValor(resultado));
        }
        
        document.add(tabla);
    }
    
    private void agregarSubseccionOtrosExamenes(Document document, String titulo, List<TodosExamenesDTO.OtroExamenDTO> otrosExamenes) {
        if (otrosExamenes == null || otrosExamenes.isEmpty()) {
            return;
        }
        
        otrosExamenes.sort((e1, e2) -> {
            if (e1.getFecha() == null && e2.getFecha() == null) return 0;
            if (e1.getFecha() == null) return 1;
            if (e2.getFecha() == null) return -1;
            return e2.getFecha().compareTo(e1.getFecha());
        });
        
        document.add(new Paragraph(titulo)
            .setBold()
            .setFontSize(12)
            .setMarginTop(10));
        
        Table tabla = new Table(UnitValue.createPercentArray(new float[]{25, 25, 50}));
        tabla.setWidth(UnitValue.createPercentValue(100));
        tabla.setMarginBottom(15);
        
        tabla.addHeaderCell(crearCeldaEncabezadoTabla("Tipo de Examen"));
        tabla.addHeaderCell(crearCeldaEncabezadoTabla("Fecha"));
        tabla.addHeaderCell(crearCeldaEncabezadoTabla("Resultado"));
        
        for (TodosExamenesDTO.OtroExamenDTO examen : otrosExamenes) {
            String tipoExamen = examen.getTipoExamen() != null && !examen.getTipoExamen().trim().isEmpty() ? 
                examen.getTipoExamen() : "Sin especificar";
            String fecha = examen.getFecha() != null ? 
                examen.getFecha().format(DATE_FORMATTER) : "Sin fecha";
            String resultado = examen.getResultado() != null && !examen.getResultado().trim().isEmpty() ? 
                examen.getResultado() : "Sin resultado";
            
            tabla.addCell(crearCeldaValor(tipoExamen));
            tabla.addCell(crearCeldaValor(fecha));
            tabla.addCell(crearCeldaValor(resultado));
        }
        
        document.add(tabla);
    }
    
    private void agregarPiePagina(Document document) {
        document.add(new Paragraph("\n\n")
            .setMarginTop(20));
        
        Paragraph pie = new Paragraph("Documento generado automáticamente por el Sistema de Salud Materna.")
            .setFontSize(8)
            .setTextAlignment(TextAlignment.CENTER)
            .setItalic();
        document.add(pie);
    }
    
    private void agregarFila(Table tabla, String titulo, String valor) {
        tabla.addCell(crearCeldaEncabezado(titulo));
        tabla.addCell(crearCeldaValor(valor != null ? valor : "No especificado"));
    }
    
    private Cell crearCeldaEncabezado(String texto) {
        return new Cell().add(new Paragraph(texto))
            .setBold()
            .setPadding(5)
            .setBackgroundColor(ColorConstants.LIGHT_GRAY);
    }
    
    private Cell crearCeldaEncabezadoTabla(String texto) {
        return new Cell().add(new Paragraph(texto))
            .setBold()
            .setPadding(5)
            .setBackgroundColor(ColorConstants.DARK_GRAY)
            .setFontColor(ColorConstants.WHITE)
            .setTextAlignment(TextAlignment.CENTER);
    }
    
    private Cell crearCeldaValor(String texto) {
        return new Cell().add(new Paragraph(texto != null ? texto : ""))
            .setPadding(5);
    }
    
    private String calcularEdad(java.time.LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return "No especificada";
        
        java.time.Period periodo = java.time.Period.between(fechaNacimiento, java.time.LocalDate.now());
        return periodo.getYears() + " años";
    }
}