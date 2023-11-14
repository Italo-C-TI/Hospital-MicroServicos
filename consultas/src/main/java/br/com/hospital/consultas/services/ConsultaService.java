package br.com.hospital.consultas.services;

import br.com.hospital.consultas.dtos.ConsultaDTO;
import br.com.hospital.consultas.dtos.EmailDTO;
import br.com.hospital.consultas.dtos.PacienteDTO;
import br.com.hospital.consultas.dtos.MedicoDTO;
import br.com.hospital.consultas.models.Consulta;
import br.com.hospital.consultas.models.Desmarcar;

import br.com.hospital.consultas.repositories.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Random;
import com.google.gson.Gson;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;



@Service
public class ConsultaService {

    private final int ANTECEDENCIA_MINUTOS = 30;
    private final int HORA_ABERTURA = 7;
    private final int HORA_FECHAMENTO = 19;

    @Autowired
    private ConsultaRepository consultaRepository;

    private static final String HOSPITAL_EMAIL = "costa.italo.ti@gmail.com"; 
    private static final String BUSCAR_PACIENTE_URL = "http://localhost:8082/pacientes/buscar/"; 
    private static final String BUSCAR_MEDICO_URL = "http://localhost:8081/medicos/buscar/"; 
    private static final String VERIFICA_SE_MEDICO_ATIVO = "http://localhost:8081/medicos/verifica-se-ativo/"; 
    private static final String VERIFICA_SE_PACIENTE_ATIVO = "http://localhost:8082/pacientes/verifica-se-ativo/"; 
    private static final String EMAIL_SERVICE_URL = "http://localhost:8080/sending-email"; 
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public Consulta agendarConsulta(String pacienteCPF, String medicoCRM, LocalDateTime dataHora) {
        validarHorarioConsulta(dataHora);
        validarAntecedenciaMinima(dataHora);
        validarConsultaDuplicada(pacienteCPF, dataHora);
        validarMedicoDisponivel(medicoCRM, dataHora);
        validarPacienteAtivo(pacienteCPF);

        Consulta consulta = new Consulta(pacienteCPF,medicoCRM ,dataHora);
        try {
            sendEmailToPaciente(pacienteCPF, consulta, medicoCRM);
            sendEmailToMedico(medicoCRM, consulta, pacienteCPF);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return consultaRepository.save(consulta);
    }

    public Consulta agendarConsulta(String pacienteCPF, LocalDateTime dataHora) {
        validarHorarioConsulta(dataHora);
        validarAntecedenciaMinima(dataHora);
        validarConsultaDuplicada(pacienteCPF, dataHora);
        validarPacienteAtivo(pacienteCPF);

        String medicoCRM = encontrarMedicoDisponivel(dataHora);

        if (medicoCRM == null) {
            throw new IllegalArgumentException("Não há médicos disponíveis para o horário selecionado.");
        }

        Consulta consulta = new Consulta();
        consulta.setPacienteCPF(pacienteCPF);
        consulta.setMedicoCRM(medicoCRM);
        consulta.setDataHora(dataHora);
        
        try {
            sendEmailToPaciente(pacienteCPF, consulta, medicoCRM);
            sendEmailToMedico(medicoCRM, consulta, pacienteCPF);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return consultaRepository.save(consulta);
    }

    public void cancelarConsulta(Long consultaId, String motivo) {
        Optional<Consulta> consultaOptional = consultaRepository.findById(consultaId);
 
        if (consultaOptional.isPresent()) {
            Consulta consulta = consultaOptional.get();
            Desmarcar desmarcar = new Desmarcar(true, motivo);
            consulta.setDesmarcar(desmarcar);
        } else {
            throw new IllegalArgumentException("Consulta não encontrada.");
        }
    }

    private void validarHorarioConsulta(LocalDateTime dataHora) {
        int hora = dataHora.getHour();
        int minutos = dataHora.getMinute();

        if (hora < HORA_ABERTURA || hora > HORA_FECHAMENTO || (hora == HORA_FECHAMENTO && minutos != 0)) {
            throw new IllegalArgumentException("Horário fora do horário de funcionamento da clínica.");
        }
    }

    private void validarAntecedenciaMinima(LocalDateTime dataHora) {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime antecedenciaMinima = agora.plusMinutes(ANTECEDENCIA_MINUTOS);

        if (dataHora.isBefore(antecedenciaMinima)) {
            throw new IllegalArgumentException("O agendamento deve ser feito com pelo menos 30 minutos de antecedência.");
        }
    }

    private void validarConsultaDuplicada(String pacienteCPF, LocalDateTime dataHora) {
        List<Consulta> consultas = consultaRepository.findByPacienteCPFAndDataHora(pacienteCPF, dataHora);
  
        if (consultas.size() > 1) {
            for (Consulta consulta : consultas) {
                if (consulta.getDataHora().equals(dataHora) && consulta.getDesmarcar().getDesmarcado() == false) {
                    throw new IllegalArgumentException("Já existe uma consulta marcada para o mesmo horário.");
                }
            }
        }
    }

    private void validarMedicoDisponivel(String medicoCRM, LocalDateTime dataHora) {
        List<Consulta> consultas = consultaRepository.findByMedicoCRMAndDataHora(medicoCRM, dataHora);

        if (consultas.size() > 1) {
            throw new IllegalArgumentException("O médico já possui consulta agendada para o mesmo horário.");
        }
    }

    private void validarPacienteAtivo(String pacienteCPF) {
        Boolean isPacienteAtivo = verificaSePacienteAtivo(pacienteCPF);
        if (!isPacienteAtivo) {
            throw new IllegalArgumentException("Não é possível agendar consulta para um paciente inativo.");
        }
    }
    
    public Page<ConsultaDTO> listarConsultasDTOPorCpfPaciente(String cpf, Pageable pageable) {
    	 Page<Consulta> consultas =  consultaRepository.findByPacienteCpf(cpf, pageable);
      	PacienteDTO pacienteDTO = buscarPaciente(cpf);
    	 
         Page<ConsultaDTO> consultasDTO = consultas.map(consulta -> {
         	MedicoDTO medicoDTO = buscarMedico(consulta.getMedicoCRM());
            ConsultaDTO consultaDTO = new ConsultaDTO(pacienteDTO,medicoDTO, consulta.getDataHora());
         
             return consultaDTO;
         });
         
         return consultasDTO;
    }
    
    public Page<ConsultaDTO> listarConsultasDTOPorCrmMedico(String crm, Pageable pageable){
        Page<Consulta> consultas = consultaRepository.findByMedicoCrm(crm, pageable);
        MedicoDTO medicoDTO = buscarMedico(crm); 
        
        Page<ConsultaDTO> consultasDTO = consultas.map(consulta -> {
        	PacienteDTO pacienteDTO = buscarPaciente(consulta.getPacienteCPF());
            ConsultaDTO consultaDTO = new ConsultaDTO(pacienteDTO,medicoDTO, consulta.getDataHora());
        
            return consultaDTO;
        });
        
        return consultasDTO;
    }
    
    private void sendEmail(EmailDTO email) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(EMAIL_SERVICE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(emailToJsonString(email)))
                .build();
        
        System.out.println(request);

        CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());

        response.thenAccept(res -> System.out.println(res.body()))
                .join();
    }
    
    public PacienteDTO buscarPaciente(String cpf) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BUSCAR_PACIENTE_URL + cpf))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                return new ObjectMapper().readValue(httpResponse.body(), PacienteDTO.class);
            } else {
                System.out.println("Erro na solicitação: " + httpResponse.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public MedicoDTO buscarMedico(String crm) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BUSCAR_MEDICO_URL + crm))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
            	System.out.println(new ObjectMapper().readValue(httpResponse.body(), MedicoDTO.class));
                return new ObjectMapper().readValue(httpResponse.body(), MedicoDTO.class);
            } else {
                System.out.println("Erro na solicitação: " + httpResponse.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Boolean verificaSePacienteAtivo(String cpf) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(VERIFICA_SE_PACIENTE_ATIVO + cpf))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                return new ObjectMapper().readValue(httpResponse.body(), Boolean.class);
            } else {
                System.out.println("Erro na solicitação: " + httpResponse.statusCode());
                return Boolean.FALSE;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Boolean verificaSeMedicoAtivo(String crm) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(VERIFICA_SE_MEDICO_ATIVO + crm))
                    .GET()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 200) {
                return new ObjectMapper().readValue(httpResponse.body(), Boolean.class);
            } else {
                System.out.println("Erro na solicitação: " + httpResponse.statusCode());
                return Boolean.FALSE;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void sendEmailToPaciente(String pacienteCPF, Consulta consulta, String medicoCRM) throws IOException, InterruptedException {
        PacienteDTO pacienteDTO = buscarPaciente(pacienteCPF);
        MedicoDTO medico = buscarMedico(medicoCRM);
        
    	EmailDTO emailForPaciente = new EmailDTO("Hospital", HOSPITAL_EMAIL, pacienteDTO.getEmail(), "Consulta marcada",
                "Olá " + pacienteDTO.getNome() + " Confirmamos que sua consulta está marcada para: " + consulta.getDataHora() + " com o médico " + medico.getNome());

        sendEmail(emailForPaciente);
    }

    private void sendEmailToMedico(String medicoCRM, Consulta consulta, String pacienteCPF) throws IOException, InterruptedException {
        MedicoDTO medico = buscarMedico(medicoCRM);
        PacienteDTO paciente = buscarPaciente(pacienteCPF);
        
    	EmailDTO emailForMedico = new EmailDTO("Hospital", HOSPITAL_EMAIL , paciente.getEmail(), "Consulta marcada",
                "Olá " + medico.getNome() + " Confirmamos que sua consulta está marcada para: " + consulta.getDataHora() + " com o paciente: " + paciente.getNome());

        System.out.println(emailForMedico);
        sendEmail(emailForMedico);
    }
    
    private String emailToJsonString(EmailDTO email) {
        Gson gson = new Gson();
        System.out.println(gson.toJson(email));
        return gson.toJson(email);
    }
    
  public String encontrarMedicoDisponivel(LocalDateTime dataHora) {
	try {
	      List<String> crmMedicosDisponiveis = consultaRepository.findCrmMedicosDisponiveis(dataHora);

	      if (crmMedicosDisponiveis.size() > 0) {
	          Random random = new Random();
	          int index = random.nextInt(crmMedicosDisponiveis.size());
	          String medicoCRM = crmMedicosDisponiveis.get(index);
	          
	          return medicoCRM;
	      }
	} catch (Exception e) {
	      throw new RuntimeException("Nenhum médico disponível na data e hora especificadas.");
	}
	return null;

  }

}
