package com.project.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.project.biblioteca.model.Aluno;
import com.project.biblioteca.model.Endereco;
import com.project.biblioteca.repository.AlunoRepository;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	AlunoRepository alunoRepository;

	@GetMapping("/consultar")
	public ModelAndView retornaFormDeConsultaTodosAlunos() {
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("alunos", alunoRepository.findAll());
		return modelAndView;
	}

	@GetMapping("/cadastrar")
	public ModelAndView retornaFormDeCadastroDe(Aluno aluno) {
		ModelAndView mv = new ModelAndView("cadastrarAluno");
		mv.addObject("aluno", aluno);
		return mv;
	}

	@GetMapping("/edit/{ra}") 
	public ModelAndView retornaFormParaEditarAluno(@PathVariable("ra") String ra) {
		ModelAndView modelAndView = new ModelAndView("atualizarAluno");
		modelAndView.addObject("aluno", alunoRepository.findByRA(ra)); 
		return modelAndView; 
	}

	@GetMapping("/delete/{id}")
	public ModelAndView excluiNoFormDeConsultaAluno(@PathVariable("id") Long id) {
		alunoRepository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		modelAndView.addObject("alunos", alunoRepository.findAll());
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Aluno aluno, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarAluno");
		} else {
			try {
				String endereco = obtemEndereco(aluno.getCEP());
				if (endereco != "") {
					aluno.setEndereco(endereco);
					alunoRepository.save(aluno);
					modelAndView.addObject("alunos", alunoRepository.findAll());
				}
			} catch (Exception e) { 
				modelAndView.setViewName("cadastrarAluno");
				modelAndView.addObject("message", "Dados invalidos");
			}
		}
		return modelAndView;
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualizaAluno(@PathVariable("id") Long id, @Valid Aluno aluno, BindingResult result) {
		if (result.hasErrors()) {
			aluno.setId(id);
			return new ModelAndView("atualizarAluno");
		}
		ModelAndView modelAndView = new ModelAndView("consultarAluno");
		Aluno umAluno = alunoRepository.findById(id).get();
		umAluno.setNome(aluno.getNome());
		umAluno.setRA(aluno.getRA());
		umAluno.setEmail(aluno.getEmail());
		umAluno.setCEP(aluno.getCEP());
		try {
			String endereco = obtemEndereco(umAluno.getCEP());
			umAluno.setEndereco(endereco);
			
			alunoRepository.save(umAluno);
			modelAndView.addObject("alunos", alunoRepository.findAll());

		} catch (Exception e) { 
			modelAndView.setViewName("atualizarAluno");
			modelAndView.addObject("message", "Dados invalidos");
		}

		return modelAndView;
	}

	public String obtemEndereco(String CEP) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{CEP}/json/";
		Endereco endereco = template.getForObject(url, Endereco.class, CEP);
		return endereco.getLogradouro();
	}
}
