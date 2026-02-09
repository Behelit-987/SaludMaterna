package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ExamenCreatininaEntity;

@Repository
public interface ExamenCreatininaRepository extends JpaRepository<ExamenCreatininaEntity, Long>{
	
	@Query("SELECT ce FROM ExamenCreatininaEntity ce WHERE ce.idUser = :idUser")
    List<ExamenCreatininaEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(ce) > 0 FROM ExamenCreatininaEntity ce WHERE ce.idUser = :idUser")
    boolean existsByUserId(@Param("idUser") Long idUser);
    
   // List<ExamenCreatininaEntity> findByIdUser(Long idUser);

}
