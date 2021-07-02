package com.project.biblioteca.model;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Aluno {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotNull
	@Size(min = 13, max = 13, message = "RA deve ter 13 caracteres")
	@Column(unique = true) 
	private String RA;
	@NotNull
	@Size(min = 1, max = 100, message = "Nome deve ter entre 1 e 100 caracteres")
	private String nome;
	@NotNull
	@Size(min = 1, max = 50, message = "Email deve ter entre 1 e 50 caracteres")
	private String email;
	@NotNull
	@Size(min = 8, max = 8, message = "CEP deve ter 8 caracteres")
	private String CEP;
	
	private String endereco;
	
	public Aluno() {
	}

	public Aluno(String r, String n, String e, String c, String end) {
		this.RA = r;
		this.nome = n;
		this.email = e;
		this.CEP = c;
		this.endereco = end;
	}
	

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getRA() {
		return RA;
	}

	public void setRA(String ra) {
		this.RA = ra;
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
	public String getCEP() {
		return CEP;
	}

	public void setCEP(String cep) {
		this.CEP = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
