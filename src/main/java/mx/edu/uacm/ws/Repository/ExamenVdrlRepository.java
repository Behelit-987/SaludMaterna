package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.uacm.ws.Entity.ExamenVdrlEntity;

public interface ExamenVdrlRepository extends JpaRepository<ExamenVdrlEntity, Long>{
	
	@Query("SELECT ev FROM ExamenVdrlEntity ev WHERE ev.idUser = :idUser")
    List<ExamenVdrlEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(ev) > 0 FROM ExamenVdrlEntity ev WHERE ev.idUser = :idUser")
    boolean existsByUserId(@Param("idUser") Long idUser);
    
    //List<ExamenVdrlEntity> findByIdUser(Long idUser);
    

}
