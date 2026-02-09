package mx.edu.uacm.ws.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.ExamenAcidoEntity;

@Repository
public interface ExamenAcidoRepository extends JpaRepository<ExamenAcidoEntity,Long >{
	
	@Query("Select ea From ExamenAcidoEntity ea Where ea.idUser = :idUser")
	List<ExamenAcidoEntity> findByUsuariaId(@Param("idUser") Long idUSer);
	
	@Query("Select count(ea) > 0 From ExamenAcidoEntity ea Where ea.idUser = :idUser")
	boolean existsByUserId(@Param("idUser") Long idUser);
	
	//List<ExamenAcidoEntity> findIdUsuaria(Long idUser);
}
