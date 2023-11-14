package br.com.hospital.consultas.models;

import javax.persistence.Embeddable;

@Embeddable
public class Desmarcar {
	private Boolean desmarcado;
	private String Motivo;
	
	public Desmarcar(Boolean desmarcado, String motivo) {
		super();
		this.desmarcado = desmarcado;
		Motivo = motivo;
	}
	
	public Desmarcar() {}

	public Boolean getDesmarcado() {
		return desmarcado;
	}

	public void setDesmarcado(Boolean desmarcado) {
		this.desmarcado = desmarcado;
	}

	public String getMotivo() {
		return Motivo;
	}

	public void setMotivo(String motivo) {
		Motivo = motivo;
	}
	
	
}
