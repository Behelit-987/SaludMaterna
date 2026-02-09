package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ExamenEgoEntity;

@Repository
public interface ExamenEgoRepository extends JpaRepository<ExamenEgoEntity, Long>{

	@Query("SELECT ee FROM ExamenEgoEntity ee WHERE ee.idUser = :idUser")
    List<ExamenEgoEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(ee) > 0 FROM ExamenEgoEntity ee WHERE ee.idUser = :idUser")
    boolean existsByUserId(@Param("idUser") Long idUser);
    
    //List<ExamenEgoEntity> findByIdUser(Long idUser);
    

}
