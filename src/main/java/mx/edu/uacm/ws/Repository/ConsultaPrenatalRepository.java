package mx.edu.uacm.ws.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ConsultaPrenatal;

@Repository
public interface ConsultaPrenatalRepository extends JpaRepository<ConsultaPrenatal, Long> {
    
	@Query(value = "SELECT cp.* FROM ConsultaPrenatales cp " +
            "WHERE cp.id_last IN (SELECT lm.id_last FROM last_mestruacion lm WHERE lm.id_User = :idUser)", nativeQuery = true)
	List<ConsultaPrenatal> findByUserId(@Param("idUser") Long idUser);

	@Query(value = "SELECT cp.* FROM ConsultaPrenatales cp " +
            "WHERE cp.fecha_consulta BETWEEN :startDate AND :endDate " +
            "AND cp.id_last IN (SELECT lm.id_last FROM last_mestruacion lm WHERE lm.id_User = :idUser)", nativeQuery = true)
	List<ConsultaPrenatal> findByUserIdAndDateRange(@Param("idUser") Long idUser, 
                                            @Param("startDate") LocalDate startDate, 
                                            @Param("endDate") LocalDate endDate);
}