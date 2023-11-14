package br.com.hospital.medicos.models;


import br.com.hospital.medicos.dtos.MedicoCreateDTO;
import javax.persistence.*;

@Entity
@Table(name = "medicos")
public class Medico {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String nome;
    private String email;
    private String telefone;
    private String crm;
    private String especialidade;
    private boolean ativo;

    @Embedded
    private Endereco endereco;
    
    public Medico(String nome, String email, String telefone, String crm, String especialidade,
			Endereco endereco) {
		super();
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
		this.crm = crm;
		this.especialidade = especialidade;
		this.endereco = endereco;
		this.ativo = true;
	}
    
    public Medico(MedicoCreateDTO medicoCreateDTO) {
		super();
		this.nome = medicoCreateDTO.getNome();
		this.email = medicoCreateDTO.getEmail();
		this.telefone = medicoCreateDTO.getTelefone();
		this.crm = medicoCreateDTO.getCrm();
		this.especialidade = medicoCreateDTO.getEspecialidade();
		this.endereco = medicoCreateDTO.getEndereco();
		this.ativo = true;
	}
  
    
    public Medico() {}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCrm() {
		return crm;
	}

	public void setCrm(String crm) {
		this.crm = crm;
	}

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
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