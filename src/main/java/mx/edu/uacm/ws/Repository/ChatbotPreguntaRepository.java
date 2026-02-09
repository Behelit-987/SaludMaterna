package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ChatbotPregunta;

@Repository
public interface ChatbotPreguntaRepository extends JpaRepository<ChatbotPregunta, Long> {
    
    List<ChatbotPregunta> findByCategoriaAndActivoTrue(ChatbotPregunta.CategoriaPregunta categoria);
    
    List<ChatbotPregunta> findByActivoTrue();
    
    @Query("SELECT c FROM ChatbotPregunta c WHERE c.activo = true AND " +
           "(LOWER(c.palabrasClave) LIKE LOWER(CONCAT('%', :termino, '%')) OR " +
           "LOWER(c.preguntaPatron) LIKE LOWER(CONCAT('%', :termino, '%')))")
    List<ChatbotPregunta> buscarPorTerminoSimple(@Param("termino") String termino);
}