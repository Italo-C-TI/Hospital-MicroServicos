package br.com.hospital.medicos.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageImpl;

import br.com.hospital.medicos.dtos.MedicoCreateDTO;
import br.com.hospital.medicos.dtos.MedicoDTO;
import br.com.hospital.medicos.models.Medico;
import br.com.hospital.medicos.services.MedicoService;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
    private final MedicoService medicoService;

    @Autowired
    public MedicoController(MedicoService medicoService) {
        this.medicoService = medicoService;
    }
    
    @GetMapping("/listar")
    public Page<MedicoDTO> listarMedicos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String ordenacao) {
        Pageable pageable = PageRequest.of(page , size, Sort.by(ordenacao));
        Page<Medico> paginaMedicos = medicoService.listarMedicos(pageable);
        
        List<MedicoDTO> medicosDTO = paginaMedicos
                .stream()
                .map(medico -> new MedicoDTO(medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade()))
                .collect(Collectors.toList());
        
        return new PageImpl<>(medicosDTO, pageable, paginaMedicos.getTotalElements());

    }
    
    @PostMapping("/criar")
    public ResponseEntity<?> cadastrarMedico(@RequestBody MedicoCreateDTO medico) {
    	  try {
    	        medicoService.cadastrarMedico(medico);
    	        return new ResponseEntity<>(HttpStatus.CREATED);
    	    } catch (IllegalArgumentException e) {
    	        return ResponseEntity.badRequest().body(e.getMessage());
    	    }
    }
   
    @PutMapping("/alterar/{crm}")
    public ResponseEntity<?> atualizarMedico(@PathVariable String crm, @RequestBody Medico novoMedico) {
  	  try {
	        medicoService.atualizarMedico(crm, novoMedico);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    } catch (IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
    }
    
    @DeleteMapping("/medicos/{crm}")
    public ResponseEntity<?> excluirMedico(@PathVariable String crm) {
    	  try {
  	        medicoService.inativarMedico(crm);
  	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  	    } catch (IllegalArgumentException e) {
  	        return ResponseEntity.badRequest().body(e.getMessage());
  	    }
    }

}