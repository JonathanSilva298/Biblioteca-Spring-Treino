package com.project.biblioteca.model;

public class Endereco {
	private String CEP;
	private String logradouro;
	private String complemento;
	private String bairro;
	private String localidade;
	private String uf;

	public Endereco(String CEP, String logradouro, String complemento, String bairro, String localidade, String uf) {
		this.CEP = CEP;
		this.logradouro = logradouro;
		this.complemento = complemento;
		this.bairro = bairro;
		this.localidade = localidade;
		this.uf = uf;
	}

	public Endereco() {
	}

	public String getCEP() {
		return CEP;
	}

	public void setCep(String CEP) {
		this.CEP = CEP;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
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

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public String toString() {
		return "Endereco{" + "CEP='" + CEP + '\'' + ", logradouro='" + logradouro + '\'' + ", complemento='"
				+ complemento + '\'' + ", bairro='" + bairro + '\'' + ", localidade='" + localidade + '\'' + ", uf='"
				+ uf + '\'' + '}';
	}
}