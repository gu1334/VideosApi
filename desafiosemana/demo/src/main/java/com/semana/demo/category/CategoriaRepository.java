package com.semana.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByTituloContainingIgnoreCase(String titulo);
}
