package br.com.hospital.pacientes.models;

import javax.persistence.*;

@Embeddable
public class Endereco {
	private String logradouro;
    private String número;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    
    
    public Endereco(String logradouro, String número, String complemento, String bairro, String cidade, String uf,
			String cep) {
		super();
		this.logradouro = logradouro;
		this.número = número;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cep = cep;
	}
    
    public Endereco() {}

    
	public String getLogradouro() {
 		return logradouro;
 	}
 	public void setLogradouro(String logradouro) {
 		this.logradouro = logradouro;
 	}
 	public String getNúmero() {
 		return número;
 	}
 	public void setNúmero(String número) {
 		this.número = número;
 	}
 	public String getComplemento() {
 		return complemento;
 	}
 	public void setComplemento(String complemento) {
 		this.complemento = complemento;
 	}
 	public String getBairro() {
 		return bairro;
 	}
 	public void setBairro(String bairro) {
 		this.bairro = bairro;
 	}
 	public String getCidade() {
 		return cidade;
 	}
 	public void setCidade(String cidade) {
 		this.cidade = cidade;
 	}
 	public String getUf() {
 		return uf;
 	}
 	public void setUf(String uf) {
 		this.uf = uf;
 	}
 	public String getCep() {
 		return cep;
 	}
 	public void setCep(String cep) {
 		this.cep = cep;
 	}

}