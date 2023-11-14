package br.com.hospital.consultas.models;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private String pacienteCPF;
    
    @ManyToOne
    private String medicoCRM;    
    private LocalDateTime dataHora;
    private Desmarcar desmarcar = new Desmarcar(false, "");
    
	public Consulta(String pacienteCPF, String medicoCRM, LocalDateTime dataHora) {
		super();
		this.pacienteCPF = pacienteCPF;
		this.medicoCRM = medicoCRM;
		this.dataHora = dataHora;
	}
    
	public Consulta() {}

	public Long getId() {
		return id;
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

	public Desmarcar getDesmarcar() {
		return desmarcar;
	}

	public void setDesmarcar(Desmarcar desmarcar) {
		this.desmarcar = desmarcar;
	}
    
}