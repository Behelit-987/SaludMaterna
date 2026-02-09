package mx.edu.uacm.ws.Service;

import mx.edu.uacm.ws.DTO.TodosExamenesDTO;
import mx.edu.uacm.ws.Entity.*;
import mx.edu.uacm.ws.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TodosExamenesService {
    
    @Autowired private ExamenGlucosaRepository glucosaRepository;
    @Autowired private ExamenBiometricaRepository biometricaRepository;
    @Autowired private ExamenEgoRepository egoRepository;
    @Autowired private ExamenUrocultivoRepository urocultivoRepository;
    @Autowired private ExamenVdrlRepository vdrlRepository;
    @Autowired private ExamenVihRepository vihRepository;
    @Autowired private ExamenCreatininaRepository creatininaRepository;
    @Autowired private ExamenAcidoRepository acidoRepository;
    @Autowired private ExamenCitologiaRepository citologiaRepository;
    @Autowired private OtrosExamenesRepository otrosExamenesRepository;
    
    public TodosExamenesDTO obtenerTodosExamenesPorUsuario(Long idUser) {
        TodosExamenesDTO dto = new TodosExamenesDTO();
        dto.setIdUser(idUser);
        
        dto.setGlucosas(convertirGlucosas(glucosaRepository.findByUsuariaId(idUser)));
        dto.setBiometricas(convertirBiometricas(biometricaRepository.findByUsuariaId(idUser)));
        dto.setEgos(convertirEgos(egoRepository.findByUsuariaId(idUser)));
        dto.setUrocultivos(convertirUrocultivos(urocultivoRepository.findByUsuariaId(idUser)));  // ← CAMBIADO
        dto.setVdrls(convertirVdrls(vdrlRepository.findByUsuariaId(idUser)));
        dto.setVihs(convertirVihs(vihRepository.findByUsuariaId(idUser)));
        dto.setCreatininas(convertirCreatininas(creatininaRepository.findByUsuariaId(idUser)));
        dto.setAcidos(convertirAcidos(acidoRepository.findByUsuariaId(idUser)));
        dto.setCitologias(convertirCitologias(citologiaRepository.findByUsuariaId(idUser)));
        dto.setOtrosExamenes(convertirOtrosExamenes(otrosExamenesRepository.findByUsuariaId(idUser)));
        
        return dto;
    }
    
    public Map<String, Object> guardarTodosExamenes(TodosExamenesDTO dto) {
        Map<String, Object> resultado = new HashMap<>();
        Long idUser = dto.getIdUser();
        
        try {
            if (dto.getGlucosas() != null && !dto.getGlucosas().isEmpty()) {
                List<ExamenGlucosaEntity> glucosas = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getGlucosas()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenGlucosaEntity entity = new ExamenGlucosaEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdGlucosa(registro.getId());
                        }
                        entity.setFechaGlucosa(registro.getFecha());
                        entity.setResultadoGlucosa(registro.getResultado());
                        entity.setIdUser(idUser);
                        glucosas.add(entity);
                    }
                }
                if (!glucosas.isEmpty()) {
                    glucosaRepository.saveAll(glucosas);
                }
            }
            
            if (dto.getBiometricas() != null && !dto.getBiometricas().isEmpty()) {
                List<ExamenBiometricaEntity> biometricas = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getBiometricas()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenBiometricaEntity entity = new ExamenBiometricaEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdBiometrica(registro.getId());
                        }
                        entity.setFechaBiometrica(registro.getFecha());
                        entity.setResultadoBiometrica(registro.getResultado());
                        entity.setIdUser(idUser);
                        biometricas.add(entity);
                    }
                }
                if (!biometricas.isEmpty()) {
                    biometricaRepository.saveAll(biometricas);
                }
            }
            
            if (dto.getEgos() != null && !dto.getEgos().isEmpty()) {
                List<ExamenEgoEntity> egos = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getEgos()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenEgoEntity entity = new ExamenEgoEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdEgo(registro.getId());
                        }
                        entity.setFechaEgo(registro.getFecha());
                        entity.setResultadoEgo(registro.getResultado());
                        entity.setIdUser(idUser);
                        egos.add(entity);
                    }
                }
                if (!egos.isEmpty()) {
                    egoRepository.saveAll(egos);
                }
            }
            
            if (dto.getUrocultivos() != null && !dto.getUrocultivos().isEmpty()) {
                List<ExamenUrocultivoEntity> urocultivos = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getUrocultivos()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenUrocultivoEntity entity = new ExamenUrocultivoEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdUrocultivo(registro.getId());
                        }
                        entity.setFechaUrocultivo(registro.getFecha());
                        entity.setResultadoUrocultivo(registro.getResultado());
                        entity.setIdUser(idUser);
                        urocultivos.add(entity);
                    }
                }
                if (!urocultivos.isEmpty()) {
                    urocultivoRepository.saveAll(urocultivos);
                }
            }
            
            if (dto.getVdrls() != null && !dto.getVdrls().isEmpty()) {
                List<ExamenVdrlEntity> vdrls = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getVdrls()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenVdrlEntity entity = new ExamenVdrlEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdVdrl(registro.getId());
                        }
                        entity.setFechaVdrl(registro.getFecha());
                        entity.setResultadoVdrl(registro.getResultado());
                        entity.setIdUser(idUser);
                        vdrls.add(entity);
                    }
                }
                if (!vdrls.isEmpty()) {
                    vdrlRepository.saveAll(vdrls);
                }
            }
            
            if (dto.getVihs() != null && !dto.getVihs().isEmpty()) {
                List<ExamenVihEntity> vihs = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getVihs()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenVihEntity entity = new ExamenVihEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdVih(registro.getId());
                        }
                        entity.setFechaVih(registro.getFecha());
                        entity.setResultadoVih(registro.getResultado());
                        entity.setIdUser(idUser);
                        vihs.add(entity);
                    }
                }
                if (!vihs.isEmpty()) {
                    vihRepository.saveAll(vihs);
                }
            }
            
            if (dto.getCreatininas() != null && !dto.getCreatininas().isEmpty()) {
                List<ExamenCreatininaEntity> creatininas = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getCreatininas()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenCreatininaEntity entity = new ExamenCreatininaEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdCretinina(registro.getId());
                        }
                        entity.setFechaCreatinina(registro.getFecha());
                        entity.setResultadoCreatinina(registro.getResultado());
                        entity.setIdUser(idUser);
                        creatininas.add(entity);
                    }
                }
                if (!creatininas.isEmpty()) {
                    creatininaRepository.saveAll(creatininas);
                }
            }
            
            if (dto.getAcidos() != null && !dto.getAcidos().isEmpty()) {
                List<ExamenAcidoEntity> acidos = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getAcidos()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenAcidoEntity entity = new ExamenAcidoEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdAcido(registro.getId());
                        }
                        entity.setFechaAcido(registro.getFecha());
                        entity.setResultadoAcido(registro.getResultado());
                        entity.setIdUser(idUser);
                        acidos.add(entity);
                    }
                }
                if (!acidos.isEmpty()) {
                    acidoRepository.saveAll(acidos);
                }
            }
            
            if (dto.getCitologias() != null && !dto.getCitologias().isEmpty()) {
                List<ExamenCitologiaEntity> citologias = new ArrayList<>();
                for (TodosExamenesDTO.ExamenRegistroDTO registro : dto.getCitologias()) {
                    if (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty())) {
                        ExamenCitologiaEntity entity = new ExamenCitologiaEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdCitologia(registro.getId());
                        }
                        entity.setFechaCitologia(registro.getFecha());
                        entity.setResultadoCitologia(registro.getResultado());
                        entity.setIdUser(idUser);
                        citologias.add(entity);
                    }
                }
                if (!citologias.isEmpty()) {
                    citologiaRepository.saveAll(citologias);
                }
            }
            
            if (dto.getOtrosExamenes() != null && !dto.getOtrosExamenes().isEmpty()) {
                List<OtrosExamenesEntity> otrosExamenes = new ArrayList<>();
                for (TodosExamenesDTO.OtroExamenDTO registro : dto.getOtrosExamenes()) {
                    if ((registro.getTipoExamen() != null && !registro.getTipoExamen().trim().isEmpty()) ||
                        (registro.getFecha() != null || (registro.getResultado() != null && !registro.getResultado().trim().isEmpty()))) {
                        OtrosExamenesEntity entity = new OtrosExamenesEntity();
                        if (registro.getId() != null && registro.getId() > 0) {
                            entity.setIdOtros(registro.getId());
                        }
                        entity.setTipoExamen(registro.getTipoExamen());
                        entity.setFechaExamen(registro.getFecha());
                        entity.setResultadoExamen(registro.getResultado());
                        entity.setIdUser(idUser);
                        otrosExamenes.add(entity);
                    }
                }
                if (!otrosExamenes.isEmpty()) {
                    otrosExamenesRepository.saveAll(otrosExamenes);
                }
            }
            
            resultado.put("success", true);
            resultado.put("message", "Todos los exámenes guardados correctamente");
            
        } catch (Exception e) {
            resultado.put("success", false);
            resultado.put("message", "Error al guardar: " + e.getMessage());
            throw e;
        }
        
        return resultado;
    }
    
    public void eliminarRegistro(String tipo, Long id) {
        switch (tipo.toLowerCase()) {
            case "glucosa":
                glucosaRepository.deleteById(id);
                break;
            case "biometrica":
                biometricaRepository.deleteById(id);
                break;
            case "ego":
                egoRepository.deleteById(id);
                break;
            case "urocultivo":
                urocultivoRepository.deleteById(id);
                break;
            case "vdrl":
                vdrlRepository.deleteById(id);
                break;
            case "vih":
                vihRepository.deleteById(id);
                break;
            case "creatinina":
                creatininaRepository.deleteById(id);
                break;
            case "acido":
                acidoRepository.deleteById(id);
                break;
            case "citologia":
                citologiaRepository.deleteById(id);
                break;
            case "otro":
                otrosExamenesRepository.deleteById(id);
                break;
            default:
                throw new IllegalArgumentException("Tipo no válido: " + tipo);
        }
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirGlucosas(List<ExamenGlucosaEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenGlucosaEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdGlucosa());
            dto.setFecha(entity.getFechaGlucosa());
            dto.setResultado(entity.getResultadoGlucosa());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirBiometricas(List<ExamenBiometricaEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenBiometricaEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdBiometrica());
            dto.setFecha(entity.getFechaBiometrica());
            dto.setResultado(entity.getResultadoBiometrica());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirEgos(List<ExamenEgoEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenEgoEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdEgo());
            dto.setFecha(entity.getFechaEgo());
            dto.setResultado(entity.getResultadoEgo());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirUrocultivos(List<ExamenUrocultivoEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenUrocultivoEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdUrocultivo());
            dto.setFecha(entity.getFechaUrocultivo());
            dto.setResultado(entity.getResultadoUrocultivo());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirVdrls(List<ExamenVdrlEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenVdrlEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdVdrl());
            dto.setFecha(entity.getFechaVdrl());
            dto.setResultado(entity.getResultadoVdrl());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirVihs(List<ExamenVihEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenVihEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdVih());
            dto.setFecha(entity.getFechaVih());
            dto.setResultado(entity.getResultadoVih());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirCreatininas(List<ExamenCreatininaEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenCreatininaEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdCretinina());
            dto.setFecha(entity.getFechaCreatinina());
            dto.setResultado(entity.getResultadoCreatinina());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirAcidos(List<ExamenAcidoEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenAcidoEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdAcido());
            dto.setFecha(entity.getFechaAcido());
            dto.setResultado(entity.getResultadoAcido());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.ExamenRegistroDTO> convertirCitologias(List<ExamenCitologiaEntity> entities) {
        List<TodosExamenesDTO.ExamenRegistroDTO> registros = new ArrayList<>();
        for (ExamenCitologiaEntity entity : entities) {
            TodosExamenesDTO.ExamenRegistroDTO dto = new TodosExamenesDTO.ExamenRegistroDTO();
            dto.setId(entity.getIdCitologia());
            dto.setFecha(entity.getFechaCitologia());
            dto.setResultado(entity.getResultadoCitologia());
            registros.add(dto);
        }
        return registros;
    }
    
    private List<TodosExamenesDTO.OtroExamenDTO> convertirOtrosExamenes(List<OtrosExamenesEntity> entities) {
        List<TodosExamenesDTO.OtroExamenDTO> registros = new ArrayList<>();
        for (OtrosExamenesEntity entity : entities) {
            TodosExamenesDTO.OtroExamenDTO dto = new TodosExamenesDTO.OtroExamenDTO();
            dto.setId(entity.getIdOtros());
            dto.setTipoExamen(entity.getTipoExamen());
            dto.setFecha(entity.getFechaExamen());
            dto.setResultado(entity.getResultadoExamen());
            registros.add(dto);
        }
        return registros;
    }
}