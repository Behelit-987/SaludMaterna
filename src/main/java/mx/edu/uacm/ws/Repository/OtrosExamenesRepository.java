package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.uacm.ws.Entity.OtrosExamenesEntity;

public interface OtrosExamenesRepository extends JpaRepository<OtrosExamenesEntity, Long>{
	
	@Query("SELECT oe FROM OtrosExamenesEntity oe WHERE oe.idUser = :idUser")
    List<OtrosExamenesEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(oe) > 0 FROM OtrosExamenesEntity oe WHERE oe.idUser = :idUser")
    boolean existsByUserId(@Param("idUser") Long idUser);
    
    //List<OtrosExamenesEntity> findByIdUser(Long idUser);
    

}
