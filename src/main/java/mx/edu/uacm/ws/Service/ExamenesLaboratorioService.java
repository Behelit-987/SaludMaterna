package mx.edu.uacm.ws.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.edu.uacm.ws.Entity.ExamenesLaboratorio;
import mx.edu.uacm.ws.Entity.ExamenesLaboratorio.Respuesta;
import mx.edu.uacm.ws.Repository.ExamanesLaboratorioRepository;

@Service
public class ExamenesLaboratorioService {
    
    @Autowired
    private ExamanesLaboratorioRepository examenesLaboratorioRepository;
    
    public ExamenesLaboratorio guardarExamenes(ExamenesLaboratorio examenes) {
        return examenesLaboratorioRepository.save(examenes);
    }
    
    public List<ExamenesLaboratorio> obtenerTodosExamenes() {
        return examenesLaboratorioRepository.findAll();
    }
    
    public Optional<ExamenesLaboratorio> obtenerExamenPorId(Long id) {
        return examenesLaboratorioRepository.findById(id);
    }
    
    /*
    public List<ExamenesLaboratorio> obtenerExamenesPorUsuariaId(Long idUsuaria) {
        return examenesLaboratorioRepository.findByUsuariaId(idUsuaria);
    }
    */
    
    public List<ExamenesLaboratorio> obtenerExamenesPorUsuariaId(Long idUser) {
        List<ExamenesLaboratorio> todos = examenesLaboratorioRepository.findAll();
        return todos.stream()
                    .filter(e -> e.getIdUser().equals(idUser))
                    .collect(Collectors.toList());
    }
    
    public boolean existeRegistroPorUsuariaId(Long idUsuaria) {
        return examenesLaboratorioRepository.existsByUsuariaId(idUsuaria);
    }
    
    public void eliminarExamen(Long id) {
        examenesLaboratorioRepository.deleteById(id);
    }
    
    public ExamenesLaboratorio actualizarExamenes(Long idUsuaria, ExamenesLaboratorio examenesActualizados) {
        List<ExamenesLaboratorio> existentes = obtenerExamenesPorUsuariaId(idUsuaria);
        
        if (existentes.isEmpty()) {
            examenesActualizados.setIdUser(idUsuaria);
            return guardarExamenes(examenesActualizados);
        } else {
            ExamenesLaboratorio existente = existentes.get(0);
            actualizarCampos(existente, examenesActualizados);
            return guardarExamenes(existente);
        }
    }
    
    public Optional<ExamenesLaboratorio> obtenerPrimerExamenPorUsuariaId(Long idUser) {
        List<ExamenesLaboratorio> examenes = obtenerExamenesPorUsuariaId(idUser);
        if (!examenes.isEmpty()) {
            return Optional.of(examenes.get(0));
        }
        return Optional.empty();
    }
    
    
    private void actualizarCampos(ExamenesLaboratorio existente, ExamenesLaboratorio actualizado) {
        if (actualizado.getGlucosaAyuno() != null) {
            existente.setGlucosaAyuno(actualizado.getGlucosaAyuno());
        }
        if (actualizado.getFechaGlucosa() != null) {
            existente.setFechaGlucosa(actualizado.getFechaGlucosa());
        }
        if (actualizado.getResultadoGlucosa() != null) {
            existente.setResultadoGlucosa(actualizado.getResultadoGlucosa());
        }
        
        if (actualizado.getBiometricaHematica() != null) {
            existente.setBiometricaHematica(actualizado.getBiometricaHematica());
        }
        if (actualizado.getFechaBiometrica() != null) {
            existente.setFechaBiometrica(actualizado.getFechaBiometrica());
        }
        if (actualizado.getResultadoBiometrica() != null) {
            existente.setResultadoBiometrica(actualizado.getResultadoBiometrica());
        }
        
        if (actualizado.getEgo() != null) {
            existente.setEgo(actualizado.getEgo());
        }
        if (actualizado.getFechaEgo() != null) {
            existente.setFechaEgo(actualizado.getFechaEgo());
        }
        if (actualizado.getResultadoEgo() != null) {
            existente.setResultadoEgo(actualizado.getResultadoEgo());
        }
        
        if (actualizado.getUrocultivo() != null) {
            existente.setUrocultivo(actualizado.getUrocultivo());
        }
        if (actualizado.getFechaUrocultivo() != null) {
            existente.setFechaUrocultivo(actualizado.getFechaUrocultivo());
        }
        if (actualizado.getResultadoUrocultivo() != null) {
            existente.setResultadoUrocultivo(actualizado.getResultadoUrocultivo());
        }
        
        if (actualizado.getVdrl() != null) {
            existente.setVdrl(actualizado.getVdrl());
        }
        if (actualizado.getFechaVdrl() != null) {
            existente.setFechaVdrl(actualizado.getFechaVdrl());
        }
        if (actualizado.getResultadoVdrl() != null) {
            existente.setResultadoVdrl(actualizado.getResultadoVdrl());
        }
        
        if (actualizado.getVihHepatitisB() != null) {
            existente.setVihHepatitisB(actualizado.getVihHepatitisB());
        }
        if (actualizado.getFechaVih() != null) {
            existente.setFechaVih(actualizado.getFechaVih());
        }
        if (actualizado.getResultadoVih() != null) {
            existente.setResultadoVih(actualizado.getResultadoVih());
        }
        
        if (actualizado.getCreatinina() != null) {
            existente.setCreatinina(actualizado.getCreatinina());
        }
        if (actualizado.getFechaCreatinina() != null) {
            existente.setFechaCreatinina(actualizado.getFechaCreatinina());
        }
        if (actualizado.getResultadoCreatinina() != null) {
            existente.setResultadoCreatinina(actualizado.getResultadoCreatinina());
        }
        
        if (actualizado.getAcidoUrico() != null) {
            existente.setAcidoUrico(actualizado.getAcidoUrico());
        }
        if (actualizado.getFechaAcido() != null) {
            existente.setFechaAcido(actualizado.getFechaAcido());
        }
        if (actualizado.getResultadoAcido() != null) {
            existente.setResultadoAcido(actualizado.getResultadoAcido());
        }
        
        if (actualizado.getCitologiaCervicovaginal() != null) {
            existente.setCitologiaCervicovaginal(actualizado.getCitologiaCervicovaginal());
        }
        if (actualizado.getFechaCitologia() != null) {
            existente.setFechaCitologia(actualizado.getFechaCitologia());
        }
        if (actualizado.getResultadoCitologia() != null) {
            existente.setResultadoCitologia(actualizado.getResultadoCitologia());
        }
    }
    
    public ExamenesLaboratorio crearExamenesDefault(Long idUsuaria) {
        ExamenesLaboratorio examenes = new ExamenesLaboratorio();
        examenes.setIdUser(idUsuaria);
        examenes.setGlucosaAyuno(Respuesta.no);
        examenes.setBiometricaHematica(Respuesta.no);
        examenes.setEgo(Respuesta.no);
        examenes.setUrocultivo(Respuesta.no);
        examenes.setVdrl(Respuesta.no);
        examenes.setVihHepatitisB(Respuesta.no);
        examenes.setCreatinina(Respuesta.no);
        examenes.setAcidoUrico(Respuesta.no);
        examenes.setCitologiaCervicovaginal(Respuesta.no);
        
        return examenes;
    }
    
    /*npx tunnelmole 3000*/
}