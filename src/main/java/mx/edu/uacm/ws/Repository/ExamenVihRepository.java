package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mx.edu.uacm.ws.Entity.ExamenVihEntity;

public interface ExamenVihRepository extends JpaRepository<ExamenVihEntity, Long>{
	
	@Query("SELECT ve FROM ExamenVihEntity ve WHERE ve.idUser = :idUser")
    List<ExamenVihEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(ve) > 0 FROM ExamenVihEntity ve WHERE ve.idUser = :idUser")
    boolean existsByUserId(@Param("idUser") Long idUser);
    
    //List<ExamenVihEntity> findByIdUsuaria(Long idUser);
    
}
