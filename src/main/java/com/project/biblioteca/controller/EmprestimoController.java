package com.project.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.biblioteca.model.Emprestimo;
import com.project.biblioteca.service.EmprestimoServico;


@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {
	@Autowired
	EmprestimoServico emprestimoServico;

	@GetMapping("/registrar")
	public ModelAndView registrarEmprestimo(Emprestimo emprestimo) {
		ModelAndView mv = new ModelAndView("registrarEmprestimo");
		mv.addObject("emprestimo", emprestimo);
		return mv;
	}

	@GetMapping("/consultar")
	public ModelAndView retornaFormDeConsultaTodosEmprestimos() {
		ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
		modelAndView.addObject("emprestimos", emprestimoServico.findAll());
		return modelAndView;
	}

	@GetMapping("/devolucao")
	public ModelAndView retornaFormDeDevolucao(Emprestimo emprestimo) {
		ModelAndView modelAndView = new ModelAndView("emprestimosPorRa");
		for (Emprestimo emprest : emprestimoServico.findAll()) {
			modelAndView.addObject("emprestimos", emprestimoServico.findNull(emprest.getDataDevolucao()));
		}
		modelAndView.addObject("emprestimo", emprestimo);
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Emprestimo emprestimo, BindingResult result) {
		String mensagem = "";
		ModelAndView modelAndView = new ModelAndView("registrarEmprestimo");
		if (result.hasErrors()) {
			modelAndView.setViewName("registrarEmprestimo");
		} else {
			mensagem = emprestimoServico.save(emprestimo);
			modelAndView.addObject("message", mensagem);
		}
		return modelAndView;
	}

	@PostMapping("/consultara")
	public ModelAndView consultara(@RequestParam("RA") String RA) {
		ModelAndView modelAndView = new ModelAndView("emprestimosPorRa");
			modelAndView.addObject("emprestimos", emprestimoServico.findByRA(RA));
			modelAndView.addObject("emprestimo", new Emprestimo());
		return modelAndView;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		emprestimoServico.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("consultarEmprestimo");
		modelAndView.addObject("emprestimos", emprestimoServico.findAll());
		return modelAndView;
	}

	@GetMapping("/devolver/{id}")
	public ModelAndView devolver(@PathVariable("id") Long id) {
		String mensagem = emprestimoServico.devolver(id);
		ModelAndView modelAndView = new ModelAndView("emprestimosPorRa");
		for (Emprestimo emprestimo : emprestimoServico.findAll()) {
			modelAndView.addObject("emprestimos", emprestimoServico.findNull(emprestimo.getDataDevolucao()));
		}
		modelAndView.addObject("message", mensagem);
		modelAndView.addObject("emprestimo", new Emprestimo());
		return modelAndView;
	}
}