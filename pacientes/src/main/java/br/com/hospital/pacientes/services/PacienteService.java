package br.com.hospital.pacientes.services;

import br.com.hospital.pacientes.dtos.PacienteCreateDTO;
import br.com.hospital.pacientes.models.Paciente;
import br.com.hospital.pacientes.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente cadastrarPaciente(PacienteCreateDTO pacienteCreateDTO) {
        if (pacienteCreateDTO.getNome() == null || pacienteCreateDTO.getEmail() == null || pacienteCreateDTO.getTelefone() == null ||
        		pacienteCreateDTO.getCpf() == null || pacienteCreateDTO.getEndereco() == null) {
            throw new IllegalArgumentException("Todos os campos obrigatórios devem ser preenchidos (nome, email , telefone , cpf, endereco).");
        }
        
        if(	pacienteCreateDTO.getEndereco().getCep() == null ||pacienteCreateDTO.getEndereco().getCidade() == null ||
        		pacienteCreateDTO.getEndereco().getUf() == null || pacienteCreateDTO.getEndereco().getBairro() == null ||
        				pacienteCreateDTO.getEndereco().getLogradouro() == null) {
            throw new IllegalArgumentException("Todos os campos obrigatórios do endereço devem ser preenchidos (logradouro, bairro, cidade, UF e CEP).");

        }
        
        if (pacienteRepository.existsByEmail(pacienteCreateDTO.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        if (pacienteRepository.existsByCpf(pacienteCreateDTO.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado.");
        }
        
        Paciente paciente = new Paciente(pacienteCreateDTO);

        return pacienteRepository.save(paciente);
    }

    public Page<Paciente> listarPacientes(Pageable pageable) {
        return pacienteRepository.findAll(pageable);
    }

    public Paciente atualizarPaciente(String cpf, Paciente novoPaciente) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findByCpf(cpf);
        if (!pacienteOptional.isPresent()) {
            throw new IllegalArgumentException("Paciente não encontrado.");
        }

        Paciente pacienteExistente = pacienteOptional.get();

        if (novoPaciente.getEmail() != null || novoPaciente.getCpf() != null) {
            throw new IllegalArgumentException("Não é permitido alterar e-mail e/ou CPF.");
        }

        if (novoPaciente.getNome() != null) {
            pacienteExistente.setNome(novoPaciente.getNome());
        }
        if (novoPaciente.getTelefone() != null) {
            pacienteExistente.setTelefone(novoPaciente.getTelefone());
        }
        if (novoPaciente.getEndereco() != null) {
            pacienteExistente.setEndereco(novoPaciente.getEndereco());
        }

        return pacienteRepository.save(pacienteExistente);
    }

    public void excluirPaciente(String cpf) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findByCpf(cpf);
        if (!pacienteOptional.isPresent()) {
            throw new IllegalArgumentException("Paciente não encontrado.");
        }

        Paciente paciente = pacienteOptional.get();
        paciente.setAtivo(false);

        pacienteRepository.save(paciente);
    }
    
    public Paciente buscarPaciente(String cpf) {
        Optional<Paciente> pacienteOptional = pacienteRepository.findByCpf(cpf);
        if (!pacienteOptional.isPresent() || !pacienteOptional.get().isAtivo()) {
            throw new IllegalArgumentException("Paciente não encontrado.");
        }

        Paciente paciente = pacienteOptional.get();
        
        return paciente;
    }
}