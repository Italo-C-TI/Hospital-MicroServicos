package br.com.hospital.medicos.dtos;

public class MedicoDTO {
	private String nome;
    private String email;
    private String crm;
    private String especialidade;
    
	public MedicoDTO(String nome, String email, String crm, String especialidade) {
		super();
		this.nome = nome;
		this.email = email;
		this.crm = crm;
		this.especialidade = especialidade;
	}
	
	public MedicoDTO() {}
	
	
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

}