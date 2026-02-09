package mx.edu.uacm.ws.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.edu.uacm.ws.Entity.Usuaria;

@Repository
public interface UsuariaRepository extends JpaRepository<Usuaria, Long> {
    Optional<Usuaria> findByEmail(String email);
    boolean existsByEmail(String email);
    
}