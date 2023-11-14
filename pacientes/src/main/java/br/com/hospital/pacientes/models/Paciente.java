package br.com.hospital.pacientes.models;

import br.com.hospital.pacientes.dtos.PacienteCreateDTO;
import javax.persistence.*;

@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;
    private boolean ativo;
    
    @Embedded
    private Endereco endereco;

	public Paciente(String nome, String email, String telefone, String cpf, Endereco endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.cpf = cpf;
		this.endereco = endereco;
		this.ativo = true;
	}
	
	public Paciente(PacienteCreateDTO pacienteCreateDTO) {
		super();
		this.nome = pacienteCreateDTO.getNome();
		this.email = pacienteCreateDTO.getEmail();
		this.telefone = pacienteCreateDTO.getTelefone();
		this.cpf = pacienteCreateDTO.getCpf();
		this.endereco = pacienteCreateDTO.getEndereco();
		this.ativo = true;
	}
	
	public Paciente () {}

	public Long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
    
	
}