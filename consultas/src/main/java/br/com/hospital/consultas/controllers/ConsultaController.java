package br.com.hospital.consultas.controllers;

import br.com.hospital.consultas.dtos.*;
import br.com.hospital.consultas.models.Consulta;
import br.com.hospital.consultas.services.ConsultaService;
import net.bytebuddy.dynamic.loading.PackageDefinitionStrategy.Definition.Undefined;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;
    
    @PostMapping("/agendar")
    public ResponseEntity<?> agendarConsulta(
    		@RequestParam String cpfPaciente,
            @RequestParam(required = false) String crmMedico,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHora) {
        try {

            if (!consultaService.verificaSePacienteAtivo(cpfPaciente)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
                        
            Consulta consulta = null;
            
            if (crmMedico != null) {
            	if(consultaService.verificaSeMedicoAtivo(crmMedico)) {
            		consulta = consultaService.agendarConsulta(cpfPaciente, crmMedico, dataHora);
                }
            }else {
            	consulta = consultaService.agendarConsulta(cpfPaciente, dataHora);

            }
            
            MedicoDTO medicoDTO = consultaService.buscarMedico(consulta.getMedicoCRM());  
            PacienteDTO pacienteDTO	= consultaService.buscarPaciente(cpfPaciente);
            ConsultaDTO response = new ConsultaDTO(pacienteDTO, medicoDTO, dataHora);
            
            return new ResponseEntity<ConsultaDTO>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
        	System.err.println(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancelar/{consultaId}")
    public ResponseEntity<String> cancelarConsulta(@PathVariable Long consultaId, @RequestBody String motivo) {
        try {
            consultaService.cancelarConsulta(consultaId, motivo);
            return new ResponseEntity<>("Consulta cancelada com sucesso.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Erro ao cancelar consulta: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/paciente/{cpf}")
    public ResponseEntity<Page<ConsultaDTO>> listarConsultasPorCpfPaciente(
            @PathVariable String cpf, Pageable pageable) {
        Page<ConsultaDTO> consultas = consultaService.listarConsultasDTOPorCpfPaciente(cpf, pageable);
        
        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }

    @GetMapping("/medico/{crm}")
    public ResponseEntity<Page<ConsultaDTO>> listarConsultasPorCrmMedico(
            @PathVariable String crm, Pageable pageable) {
        Page<ConsultaDTO> consultas = consultaService.listarConsultasDTOPorCrmMedico(crm, pageable);

        return new ResponseEntity<>(consultas, HttpStatus.OK);
    }
}