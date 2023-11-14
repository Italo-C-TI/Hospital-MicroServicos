package br.com.hospital.medicos.repositories;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.hospital.medicos.models.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Long> {
	   boolean existsByEmail(String email);
	   boolean existsByCrm(String crm);
	   Page<Medico> findAll(Pageable pageable);
	   Optional<Medico> findByCrm(String crm);
	    	   
}