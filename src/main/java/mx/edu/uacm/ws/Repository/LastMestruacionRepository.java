package mx.edu.uacm.ws.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.LastMestruacion;

@Repository
public interface LastMestruacionRepository extends JpaRepository<LastMestruacion, Long> {
    
	//Optional<LastMestruacion> findByIdUser(Long idUser);
	@Query("SELECT lm FROM LastMestruacion lm WHERE lm.idUser = :idUser")
    Optional<LastMestruacion> findByUserId(@Param("idUser") Long idUser);
}