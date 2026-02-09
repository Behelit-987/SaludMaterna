package mx.edu.uacm.ws.Repository;

import mx.edu.uacm.ws.Entity.DatosConsultaPrenatal;
import mx.edu.uacm.ws.Entity.LastMestruacion;
import mx.edu.uacm.ws.Entity.ConsultaPrenatal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatosConsultaPrenatalRepository extends JpaRepository<DatosConsultaPrenatal, Long> {
    
    List<DatosConsultaPrenatal> findByIdLast(LastMestruacion idLast);
    
    Optional<DatosConsultaPrenatal> findByIdPrenatal(ConsultaPrenatal idPrenatal);
    
    boolean existsByIdPrenatal(ConsultaPrenatal idPrenatal);
    
    @Query("SELECT d FROM DatosConsultaPrenatal d WHERE d.idLast.id_last = :idLast")
    List<DatosConsultaPrenatal> findByIdLastId(@Param("idLast") Long idLast);
    
    @Query("SELECT d FROM DatosConsultaPrenatal d WHERE d.idPrenatal.idPrenatal = :idPrenatal")
    Optional<DatosConsultaPrenatal> findByIdPrenatalId(@Param("idPrenatal") Long idPrenatal);
    
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
           "FROM DatosConsultaPrenatal d WHERE d.idPrenatal.idPrenatal = :idPrenatal")
    boolean existsByIdPrenatalId(@Param("idPrenatal") Long idPrenatal);
        
    @Query("SELECT d FROM DatosConsultaPrenatal d " +
           "WHERE d.idLast.idUser = :idUser")
    List<DatosConsultaPrenatal> findByIdUserId(@Param("idUser") Long idUser);
    
    @Query("SELECT d FROM DatosConsultaPrenatal d " +
           "WHERE d.idPrenatal.idPrenatal = :idPrenatal " +
           "AND d.idLast.idUser = :idUser")
    Optional<DatosConsultaPrenatal> findByUserAndPrenatalId(
            @Param("idUser") Long idUser, 
            @Param("idPrenatal") Long idPrenatal);
    
    @Query(value = "SELECT d.* FROM datos_consultaprenatal d " +
                   "JOIN last_mestruacion lm ON d.id_last = lm.id_last " +
                   "WHERE lm.id_User = :idUser AND d.id_Prenatal = :idPrenatal", 
           nativeQuery = true)
    Optional<DatosConsultaPrenatal> findByUserAndPrenatalNative(
            @Param("idUser") Long idUser, 
            @Param("idPrenatal") Long idPrenatal);
}