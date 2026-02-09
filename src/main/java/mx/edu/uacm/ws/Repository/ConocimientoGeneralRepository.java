package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ConocimientoGeneral;


@Repository
public interface ConocimientoGeneralRepository extends JpaRepository<ConocimientoGeneral, Long>{
	@Query("SELECT c FROM ConocimientoGeneral c WHERE c.idUser = :idUser")
	List<ConocimientoGeneral> findByUsuariaId(@Param("idUser") Long idUser);
	
	List<ConocimientoGeneral> findByIdUser(Long idUser);
}

/*

    @Query("SELECT COUNT(e) > 0 FROM ExamenesLaboratorio e WHERE e.idUser = :idUser")
    boolean existsByUsuariaId(@Param("idUser") Long idUser);
    
*/