package com.semana.demo.videos;

import com.semana.demo.categorias.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {
    // Modificado para buscar vídeos pela instância de Categoria
    List<Video> findByCategoria(Categoria categoria);

    List<Video> findByTituloContainingIgnoreCase(String titulo);
}
