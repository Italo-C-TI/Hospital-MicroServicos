package br.com.hospital.medicos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.hospital.medicos.dtos.MedicoCreateDTO;
import br.com.hospital.medicos.models.Medico;
import br.com.hospital.medicos.repositories.MedicoRepository;
import javax.persistence.EntityNotFoundException;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public void cadastrarMedico(MedicoCreateDTO medicoCreateDTO) {
        if (medicoCreateDTO.getNome() == null || medicoCreateDTO.getEmail() == null || medicoCreateDTO.getCrm() == null || medicoCreateDTO.getEspecialidade() == null) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos (nome, email,crm e especialidade).");
        }

        if (medicoRepository.existsByEmail(medicoCreateDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (medicoRepository.existsByCrm(medicoCreateDTO.getCrm())) {
            throw new IllegalArgumentException("CRM já cadastrado.");
        }

        Medico medico = new Medico(medicoCreateDTO);
        medico.setAtivo(true);
        
        medicoRepository.save(medico);
    }

    public Page<Medico> listarMedicos(Pageable pageable) {
        return medicoRepository.findAll(pageable);
    }

    public void atualizarMedico(String crm, Medico medicoAtualizado) {
        Medico medicoExistente = medicoRepository.findByCrm(crm)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado."));


        if(medicoAtualizado.getEmail() != null) {
            throw new IllegalArgumentException("Não é permitido alterar o e-mail.");
        }

        if(medicoAtualizado.getCrm() != null) {
            throw new IllegalArgumentException("Não é permitido alterar o CRM.");

        }
 
        if(medicoAtualizado.getEspecialidade() != null) {
            throw new IllegalArgumentException("Não é permitido alterar a especialidade.");
        }

        
        if (medicoAtualizado.getNome() != null) {
            medicoExistente.setNome(medicoAtualizado.getNome());
        }
        if (medicoAtualizado.getTelefone() != null) {
            medicoExistente.setTelefone(medicoAtualizado.getTelefone());
        }
        if (medicoAtualizado.getEndereco() != null) {
            medicoExistente.setEndereco(medicoAtualizado.getEndereco());
        }

        medicoRepository.save(medicoExistente);
    }

    public void inativarMedico(String crm) {
        Medico medicoExistente = medicoRepository.findByCrm(crm)
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado."));

        medicoExistente.setAtivo(false);
        medicoRepository.save(medicoExistente);
    }
    
//    public Medico encontrarMedicoDisponivel(LocalDateTime dataHora) {
//
//        List<Medico> medicosDisponiveis = medicoRepository.findMedicosDisponiveis(dataHora);
//
//        if (!medicosDisponiveis.isEmpty()) {
//            Random random = new Random();
//            int index = random.nextInt(medicosDisponiveis.size());
//            Medico medico = medicosDisponiveis.get(index);
//            
//            return medico;
//        }
//
//        throw new RuntimeException("Nenhum médico disponível na data e hora especificadas.");
//    }
}