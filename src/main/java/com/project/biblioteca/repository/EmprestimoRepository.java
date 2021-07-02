package com.project.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.project.biblioteca.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
	@Query("SELECT e FROM Emprestimo e WHERE e.RA = :RA")
	public List <Emprestimo> findByRA(@Param("RA") String RA);
	
	@Query("SELECT e FROM Emprestimo e WHERE e.isbn = :isbn")
	public List <Emprestimo> findByISBN(@Param("isbn") String isbn);
	
	@Query("SELECT e FROM Emprestimo e WHERE e.dataDevolucao = null")
	public List <Emprestimo> findNull(String dataDevolucao);
}
