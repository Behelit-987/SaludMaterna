package mx.edu.uacm.ws.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uacm.ws.Entity.ConocimientoGeneral;
import mx.edu.uacm.ws.Entity.Usuaria;
import mx.edu.uacm.ws.Repository.ConocimientoGeneralRepository;
import mx.edu.uacm.ws.Repository.UsuariaRepository;

@Service
public class ConocimientoService {

    @Autowired
    private ConocimientoGeneralRepository conocimientoRepository;
    
    @Autowired
    private UsuariaRepository usuariaRepository;
    
    public List<ConocimientoGeneral> findAllByUserId(Long userId) {
        return conocimientoRepository.findByIdUser(userId);
    }
    
    public Optional<ConocimientoGeneral> findById(Long id) {
        return conocimientoRepository.findById(id);
    }
    
    public Optional<ConocimientoGeneral> findByUserId(Long userId) {
        List<ConocimientoGeneral> conocimientos = conocimientoRepository.findByIdUser(userId);
        return conocimientos.isEmpty() ? Optional.empty() : Optional.of(conocimientos.get(0));
    }
    
    public ConocimientoGeneral saveOrUpdate(ConocimientoGeneral conocimiento) {
        return conocimientoRepository.save(conocimiento);
    }
    
    public void deleteById(Long id) {
        conocimientoRepository.deleteById(id);
    }
    
    public Optional<Usuaria> findUsuariaById(Long userId) {
        return usuariaRepository.findById(userId);
    }
    
    public PerfilDTO getPerfilCompleto(Long userId) {
        Optional<Usuaria> usuariaOpt = usuariaRepository.findById(userId);
        List<ConocimientoGeneral> conocimientos = conocimientoRepository.findByIdUser(userId);
        
        if (usuariaOpt.isEmpty()) {
            return null;
        }
        
        Usuaria usuaria = usuariaOpt.get();
        ConocimientoGeneral conocimiento = conocimientos.isEmpty() ? null : conocimientos.get(0);
        
        return new PerfilDTO(usuaria, conocimiento);
    }
    
    // DTO para transferir datos del perfil completo
    public static class PerfilDTO {
        private Usuaria usuaria;
        private ConocimientoGeneral conocimiento;
        
        public PerfilDTO(Usuaria usuaria, ConocimientoGeneral conocimiento) {
            this.usuaria = usuaria;
            this.conocimiento = conocimiento;
        }
        
        public Usuaria getUsuaria() {
            return usuaria;
        }
        
        public ConocimientoGeneral getConocimiento() {
            return conocimiento;
        }
    }
    
    public Usuaria actualizarUsuaria(Usuaria usuaria) {
        if (usuaria.getIdUser() == null) {
            throw new RuntimeException("La usuaria no tiene ID");
        }
        
        Optional<Usuaria> existente = usuariaRepository.findById(usuaria.getIdUser());
        if (existente.isEmpty()) {
            throw new RuntimeException("Usuaria no encontrada");
        }
        
        usuaria.setEmail(existente.get().getEmail());
        usuaria.setPasswordHash(existente.get().getPasswordHash());
        usuaria.setFechaRegistro(existente.get().getFechaRegistro());
        
        return usuariaRepository.save(usuaria);
    }
}