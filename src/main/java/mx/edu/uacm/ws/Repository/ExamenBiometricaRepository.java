package mx.edu.uacm.ws.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import mx.edu.uacm.ws.Entity.ExamenBiometricaEntity;

@Repository
public interface ExamenBiometricaRepository extends JpaRepository<ExamenBiometricaEntity, Long> {

    @Query("SELECT eb FROM ExamenBiometricaEntity eb WHERE eb.idUser = :idUser")
    List<ExamenBiometricaEntity> findByUsuariaId(@Param("idUser") Long idUser);
    
    @Query("SELECT COUNT(eb) > 0 FROM ExamenBiometricaEntity eb WHERE eb.idUser = :idUser")
    boolean existeUsuariaId(@Param("idUser") Long idUser);
    
    //List<ExamenBiometricaEntity> findByIdUser(Long idUser);
    
    /*
    @Query("SELECT eb FROM ExamenBiometricaEntity eb WHERE eb.idUser = :idUser")
    List<ExamenBiometricaEntity> findByUserId(@Param("idUser") Long idUser);
    */
}