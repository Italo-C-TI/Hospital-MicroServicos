package br.com.hospital.consultas.dtos;

import java.time.LocalDateTime;

public class ConsultaDTO {
    private String pacienteCPF;    
    private String medicoCRM;    
    private LocalDateTime dataHora;
    
    public ConsultaDTO(String pacienteCPF, String medicoCRM, LocalDateTime dataHora) {
		super();
		this.pacienteCPF = pacienteCPF;
		this.medicoCRM = medicoCRM;
		this.dataHora = dataHora;
	}
	
	public String getPacienteCPF() {
		return pacienteCPF;
	}


	public void setPacienteCPF(String pacienteCPF) {
		this.pacienteCPF = pacienteCPF;
	}


	public String getMedicoCRM() {
		return medicoCRM;
	}


	public void setMedicoCRM(String medicoCRM) {
		this.medicoCRM = medicoCRM;
	}


	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
    
    
}
