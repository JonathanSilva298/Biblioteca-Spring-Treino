package com.project.biblioteca.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.project.biblioteca.model.Livro;
import com.project.biblioteca.repository.LivroRepository;

@Controller
@RequestMapping("/livros")
public class LivroController {
	@Autowired
	LivroRepository livroRepository;

	@GetMapping("/consultar")
	public ModelAndView retornaFormDeConsultaTodosLivros() {
		ModelAndView modelAndView = new ModelAndView("consultarLivro");
		modelAndView.addObject("livros", livroRepository.findAll());
		return modelAndView;
	}

	@GetMapping("/cadastrar")
	public ModelAndView retornaFormDeCadastroDe(Livro livro) {
		ModelAndView mv = new ModelAndView("cadastrarLivro");
		mv.addObject("livro", livro);
		return mv;
	}

	@GetMapping("/edit/{isbn}") 
	public ModelAndView retornaFormParaEditarLivro(@PathVariable("isbn") String isbn) {
		ModelAndView modelAndView = new ModelAndView("atualizarLivro");
		modelAndView.addObject("livro", livroRepository.findByIsbn(isbn));
		return modelAndView; 
	}

	@GetMapping("/delete/{id}")
	public ModelAndView excluiNoFormDeConsultaLivro(@PathVariable("id") Long id) {
		livroRepository.deleteById(id);
		ModelAndView modelAndView = new ModelAndView("consultarLivro");
		modelAndView.addObject("livros", livroRepository.findAll());
		return modelAndView;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid Livro livro, BindingResult result) {
		ModelAndView modelAndView = new ModelAndView("consultarLivro");
		if (result.hasErrors()) {
			modelAndView.setViewName("cadastrarLivro");
		} else {
			try {
				livroRepository.save(livro);
				modelAndView.addObject("livros", livroRepository.findAll());
			} catch (Exception e) { 
				modelAndView.setViewName("cadastrarLivro");
				modelAndView.addObject("message", "Livro ja cadastrado");
			}
		}
		return modelAndView;
	}

	@PostMapping("/update/{id}")
	public ModelAndView atualizaLivro(@PathVariable("id") Long id, @Valid Livro livro, BindingResult result) {
		if (result.hasErrors()) {
			livro.setId(id);
			return new ModelAndView("atualizarLivro");
		}
		
		Livro umLivro = livroRepository.findById(id).get();
		umLivro.setAutor(livro.getAutor());
		umLivro.setIsbn(livro.getIsbn());
		umLivro.setTitulo(livro.getTitulo());
		livroRepository.save(umLivro);
		ModelAndView modelAndView = new ModelAndView("consultarLivro");
		modelAndView.addObject("livros", livroRepository.findAll());
		return modelAndView;
	}
}