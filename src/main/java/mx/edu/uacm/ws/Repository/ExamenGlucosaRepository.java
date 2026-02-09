package mx.edu.uacm.ws.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.edu.uacm.ws.Entity.ExamenGlucosaEntity;

@Repository
public interface ExamenGlucosaRepository extends JpaRepository<ExamenGlucosaEntity, Long> {
    
    @Query("SELECT eg FROM ExamenGlucosaEntity eg WHERE eg.idUser = :idUser")
    List<ExamenGlucosaEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT eg FROM ExamenGlucosaEntity eg WHERE eg.idUser = :idUser")
    List<ExamenGlucosaEntity> findByUserId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(eg) > 0 FROM ExamenGlucosaEntity eg WHERE eg.idUser = :idUser")
    boolean existsById(@Param("idUser") Long idUser);
}