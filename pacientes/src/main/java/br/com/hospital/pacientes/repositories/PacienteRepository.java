package br.com.hospital.pacientes.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.hospital.pacientes.models.Paciente;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
	   boolean existsByEmail(String email);
	   boolean existsByCpf(String cpf);
	   Optional<Paciente> findByCpf(String cpf);
	   Page<Paciente> findAll(Pageable pageable);
}