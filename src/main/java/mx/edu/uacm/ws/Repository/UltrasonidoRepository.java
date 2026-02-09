package mx.edu.uacm.ws.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.Ultrasonido;

@Repository
public interface UltrasonidoRepository extends JpaRepository<Ultrasonido, Long>{
	@Query(value = "SELECT u.* FROM Ultrasonido u " +
            "WHERE u.id_last IN (SELECT lm.id_last FROM last_mestruacion lm WHERE lm.id_User = :idUser)", nativeQuery = true)
	List<Ultrasonido> findByUserId(@Param("idUser") Long idUser);

	@Query(value = "SELECT cp.* FROM Ultrasonido u " +
            "WHERE u.cita_ultrasonido BETWEEN :startDate AND :endDate " +
            "AND u.id_last IN (SELECT lm.id_last FROM last_mestruacion lm WHERE lm.id_User = :idUser)", nativeQuery = true)
	List<Ultrasonido> findByUserIdAndDateRange(@Param("idUser") Long idUser, 
                                            @Param("startDate") LocalDate startDate, 
                                            @Param("endDate") LocalDate endDate);
}
