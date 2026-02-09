package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ExamenCitologiaEntity;

@Repository
public interface ExamenCitologiaRepository extends JpaRepository<ExamenCitologiaEntity, Long>{

	
	@Query("SELECT ec FROM ExamenCitologiaEntity ec WHERE ec.idUser = :idUser")
    List<ExamenCitologiaEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(ec) > 0 FROM ExamenCitologiaEntity ec WHERE ec.idUser = :idUser")
    boolean existsByUserId(@Param("idUser") Long idUser);
    
    //List<ExamenCitologiaEntity> findByIdUser(Long idUser);
    

}
