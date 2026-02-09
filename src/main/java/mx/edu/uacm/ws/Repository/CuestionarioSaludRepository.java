package mx.edu.uacm.ws.Repository;

import mx.edu.uacm.ws.Entity.CuestionarioSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuestionarioSaludRepository extends JpaRepository<CuestionarioSalud, Long> {
    
    Optional<CuestionarioSalud> findByIdUser(Long idUser);
    
    @Query("SELECT COUNT(c) > 0 FROM CuestionarioSalud c WHERE c.idUser = :idUser")
    boolean existsByIdUser(@Param("idUser") Long idUser);
}