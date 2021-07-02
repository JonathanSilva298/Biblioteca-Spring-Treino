package com.project.biblioteca.repository;

import org.springframework.stereotype.Repository;

import com.project.biblioteca.model.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
	Optional<Usuario> findByLogin(String login);
}
