package com.project.biblioteca.service;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.biblioteca.controller.EmprestimoController;
import com.project.biblioteca.model.Aluno;
import com.project.biblioteca.model.Emprestimo;
import com.project.biblioteca.model.Livro;
import com.project.biblioteca.repository.AlunoRepository;
import com.project.biblioteca.repository.EmprestimoRepository;
import com.project.biblioteca.repository.LivroRepository;

@Service
public class EmprestimoServico {
	Logger logger = LogManager.getLogger(EmprestimoServico.class);
	@Autowired
	private EmprestimoRepository emprestimoRepository;
	@Autowired
	private LivroRepository livroRepository;
	@Autowired
	private AlunoRepository alunoRepository;

	public String save(Emprestimo emprestimo) {
		String mensagem = "";
		try {
			List<Emprestimo> emprestimos = findByRA(emprestimo.getRA());
			if (emprestimosEmAberto(emprestimos) == false
					&& existeAlunoLivro(emprestimo.getRA(), emprestimo.getIsbn()) == true) {
				logger.info(
						"======================> achou livro/aluno no db e nao existe emprestimo em aberto/cadastrado");
				emprestimo.setDataEmprestimo(DateTime.now());
				emprestimoRepository.save(emprestimo);
				mensagem = "Emprestimo registrado";
			} else {
				logger.info("======================> não achou livro/aluno no db");
				mensagem = "Livro/Aluno não localizado ou emprestimo em aberto";
			}
		} catch (Exception e) {
			logger.info("erro nao esperado no cadastro de emprestimo ===> " + e.getMessage());
			mensagem = "Erro não esperado contacte o administrador";
		}
		return mensagem;
	}
	
	public Emprestimo findById(Long id) {
		return emprestimoRepository.findById(id).get();
	}
	
	public String devolver(Long id) {
		String mensagem = "";
		Emprestimo emprestimo = findById(id);
		if(emprestimo.getDataDevolucao() == null) {
		emprestimo.setDataDevolucao(DateTime.now());
		emprestimoRepository.save(emprestimo);
		if(emprestimo.verificaAtraso() <= 0) {
			mensagem = "Entrega realizada com adiantamento de " + emprestimo.verificaAtraso() * (-1) + " dias !";
		}else {
			mensagem = "Entrega realizada com atraso de " + emprestimo.verificaAtraso() + " dias !";
		}	
		}else {
			mensagem = "Empréstimo Já devolvido!";
		}
		
		return mensagem;
	}

	public Iterable<Emprestimo> findAll() {
		return emprestimoRepository.findAll();
	}

	public void deleteById(Long id) {
		emprestimoRepository.deleteById(id);
	}

	public List<Emprestimo> findByRA(String ra) {
		return emprestimoRepository.findByRA(ra);
	}
	
	public List<Emprestimo> findByISBN(String isbn) {
		return emprestimoRepository.findByISBN(isbn);
	}
	
	public List<Emprestimo> findNull(String dataDevolucao) {
		return emprestimoRepository.findNull(dataDevolucao);
	}

	public boolean emprestimosEmAberto(List<Emprestimo> emprestimos) {
		boolean existe = false;
		for (Emprestimo umEmprestimo : emprestimos) {
			if (umEmprestimo.getDataDevolucao() == null) {
				existe = true;
			}
		}
		logger.info("=====================> achei emprestimos em aberto?" + existe);
		return existe;
	}

	public boolean existeAlunoLivro(String ra, String isbn) {
		Aluno aluno = alunoRepository.findByRA(ra);
		Livro livro = livroRepository.findByIsbn(isbn);
		if (aluno != null && livro != null) {
			return true;
		}
		return false;
	}
}
