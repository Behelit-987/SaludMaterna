package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ExamenesLaboratorio;

@Repository
public interface ExamanesLaboratorioRepository extends JpaRepository<ExamenesLaboratorio, Long>{

	@Query("SELECT e FROM ExamenesLaboratorio e WHERE e.idUser = :idUser")
    List<ExamenesLaboratorio> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(e) > 0 FROM ExamenesLaboratorio e WHERE e.idUser = :idUser")
    boolean existsByUsuariaId(@Param("idUser") Long idUser);
    
    List<ExamenesLaboratorio> findByIdUser(Long idUser);
    
}
