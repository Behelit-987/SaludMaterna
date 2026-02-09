package mx.edu.uacm.ws;

import mx.edu.uacm.ws.Service.PDFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/app-salud-materna")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @GetMapping("/generar/{idUser}")
    public ResponseEntity<Resource> generarPDF(@PathVariable Long idUser) {
        try {
            byte[] pdfBytes = pdfService.generarInformeCompleto(idUser);
            
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=informe-prenatal-" + idUser + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(resource);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @GetMapping("/vista/{idUser}")
    public ResponseEntity<Resource> verPDF(@PathVariable Long idUser) {
        try {
            byte[] pdfBytes = pdfService.generarInformeCompleto(idUser);
            
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "inline; filename=informe-prenatal-" + idUser + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .contentLength(pdfBytes.length)
                    .body(resource);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}