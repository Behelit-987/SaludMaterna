package mx.edu.uacm.ws.Service;

import mx.edu.uacm.ws.Entity.CuestionarioSalud;
import mx.edu.uacm.ws.Repository.CuestionarioSaludRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuestionarioSaludService {
    
    @Autowired
    private CuestionarioSaludRepository cuestionarioSaludRepository;
    
    public CuestionarioSalud guardarCuestionario(CuestionarioSalud cuestionario) {

    	Optional<CuestionarioSalud> cuestionarioExistente = cuestionarioSaludRepository.findByIdUser(cuestionario.getIdUser());
        
        if (cuestionarioExistente.isPresent()) {
            CuestionarioSalud existente = cuestionarioExistente.get();
            actualizarCuestionarioExistente(existente, cuestionario);
            return cuestionarioSaludRepository.save(existente);
        } else {
            return cuestionarioSaludRepository.save(cuestionario);
        }
    }
    
    private void actualizarCuestionarioExistente(CuestionarioSalud existente, CuestionarioSalud nuevo) {
        existente.setPrimerEmbarazo(nuevo.getPrimerEmbarazo());
        
        // Condiciones generales primer embarazo
        existente.setObesidad(nuevo.getObesidad());
        existente.setSobrepeso(nuevo.getSobrepeso());
        existente.setPresionArterialAlta(nuevo.getPresionArterialAlta());
        existente.setDiabetesMellitus(nuevo.getDiabetesMellitus());
        existente.setEpilepsia(nuevo.getEpilepsia());
        existente.setNefropatia(nuevo.getNefropatia());
        existente.setLupus(nuevo.getLupus());
        existente.setDesnutricion(nuevo.getDesnutricion());
        existente.setEnfermedadTiroidea(nuevo.getEnfermedadTiroidea());
        existente.setOtrasEnfermedades(nuevo.getOtrasEnfermedades());
        existente.setDescripcionOtras(nuevo.getDescripcionOtras());
        
        // Enfermedades del pulmón
        existente.setEnfermedadesPulmon(nuevo.getEnfermedadesPulmon());
        existente.setAsma(nuevo.getAsma());
        existente.setTuberculosis(nuevo.getTuberculosis());
        existente.setEpoc(nuevo.getEpoc());
        existente.setFibrosisQuistica(nuevo.getFibrosisQuistica());
        existente.setNeumoniaRecurrente(nuevo.getNeumoniaRecurrente());
        existente.setOtrasPulmonares(nuevo.getOtrasPulmonares());
        existente.setDescripcionOtrasPulmonares(nuevo.getDescripcionOtrasPulmonares());
        
        // Enfermedades del corazón
        existente.setEnfermedadesCorazon(nuevo.getEnfermedadesCorazon());
        existente.setCardiopatiaCongenita(nuevo.getCardiopatiaCongenita());
        existente.setValvulopatia(nuevo.getValvulopatia());
        existente.setArritmias(nuevo.getArritmias());
        existente.setMiocardiopatia(nuevo.getMiocardiopatia());
        existente.setHipertensionPulmonar(nuevo.getHipertensionPulmonar());
        existente.setOtrasCardiaca(nuevo.getOtrasCardiaca());
        existente.setDescripcionOtrasCardiaca(nuevo.getDescripcionOtrasCardiaca());
        
        // Enfermedades hematológicas
        existente.setEnfermedadesHematologicas(nuevo.getEnfermedadesHematologicas());
        existente.setAnemia(nuevo.getAnemia());
        existente.setTrombocitopenia(nuevo.getTrombocitopenia());
        existente.setHemofilia(nuevo.getHemofilia());
        existente.setLeucemia(nuevo.getLeucemia());
        existente.setAnemiaFalciforme(nuevo.getAnemiaFalciforme());
        existente.setOtrasHematologicas(nuevo.getOtrasHematologicas());
        existente.setDescripcionOtrasHematologicas(nuevo.getDescripcionOtrasHematologicas());
        
        // Enfermedades de Transmisión Sexual
        existente.setEnfermedadesTransmisionSexual(nuevo.getEnfermedadesTransmisionSexual());
        existente.setVih(nuevo.getVih());
        existente.setSifilis(nuevo.getSifilis());
        existente.setGonorrea(nuevo.getGonorrea());
        existente.setClamidia(nuevo.getClamidia());
        existente.setHerpesGenital(nuevo.getHerpesGenital());
        existente.setVph(nuevo.getVph());
        existente.setOtrasEts(nuevo.getOtrasEts());
        existente.setDescripcionOtrasEts(nuevo.getDescripcionOtrasEts());
        
        // Para embarazos anteriores
        existente.setNumeroEmbarazos(nuevo.getNumeroEmbarazos());
        existente.setNumeroPartos(nuevo.getNumeroPartos());
        existente.setNumeroCesareas(nuevo.getNumeroCesareas());
        existente.setNumeroAbortos(nuevo.getNumeroAbortos());
        existente.setNumeroEctopicos(nuevo.getNumeroEctopicos());
        
        // Complicaciones en embarazos anteriores
        existente.setTrastornosHipertensivos(nuevo.getTrastornosHipertensivos());
        existente.setDesprendimientoPlacenta(nuevo.getDesprendimientoPlacenta());
        existente.setPartoPretermino(nuevo.getPartoPretermino());
        existente.setDiabetesGestacional(nuevo.getDiabetesGestacional());
        existente.setRestriccionCrecimiento(nuevo.getRestriccionCrecimiento());
        existente.setMuerteFetal(nuevo.getMuerteFetal());
    }
    
    public Optional<CuestionarioSalud> obtenerCuestionarioPorUsuario(Long idUser) {
        return cuestionarioSaludRepository.findByIdUser(idUser);
    }
    
    public boolean existeCuestionarioPorUsuario(Long idUser) {
        return cuestionarioSaludRepository.existsByIdUser(idUser);
    }
    
    public void eliminarCuestionario(Long idCuestionario) {
        cuestionarioSaludRepository.deleteById(idCuestionario);
    }
}