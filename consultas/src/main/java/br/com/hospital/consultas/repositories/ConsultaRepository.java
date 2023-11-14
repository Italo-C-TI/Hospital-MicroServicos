package br.com.hospital.consultas.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.hospital.consultas.models.Consulta;


@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByPacienteCPFAndDataHoraBetween(String pacienteCPF, LocalDateTime inicio, LocalDateTime fim);
    List<Consulta> findByMedicoCRMAndDataHora(String medicoCRM, LocalDateTime dataHora);
    
    @Query("SELECT c FROM Consulta c WHERE c.pacienteCPF = :cpf")
    Page<Consulta> findByPacienteCpf(@Param("cpf") String cpf, Pageable pageable);
    @Query("SELECT c FROM Consulta c WHERE c.medicoCRM = :crm")
    Page<Consulta> findByMedicoCrm(@Param("crm") String crm, Pageable pageable);

}