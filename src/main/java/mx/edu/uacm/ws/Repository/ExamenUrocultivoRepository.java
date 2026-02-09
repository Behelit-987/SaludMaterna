package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ExamenUrocultivoEntity;

@Repository
public interface ExamenUrocultivoRepository extends JpaRepository<ExamenUrocultivoEntity, Long>{
	
	@Query("SELECT eu FROM ExamenUrocultivoEntity eu WHERE eu.idUser = :idUser")
    List<ExamenUrocultivoEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(eu) > 0 FROM ExamenUrocultivoEntity eu WHERE eu.idUser = :idUser")
    boolean existeByUsuariaId(@Param("idUser") Long idUser);
    
    //List<ExamenUrocultivoEntity> findByUsuariaId(Long idUser);
    

}
