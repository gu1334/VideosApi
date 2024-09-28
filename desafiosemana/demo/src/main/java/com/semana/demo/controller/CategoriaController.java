package com.semana.demo.controller;

import com.semana.demo.categorias.Categoria;
import com.semana.demo.categorias.CategoriaRepository;
import com.semana.demo.categorias.DadosCategoria;
import com.semana.demo.videos.Video;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Cadastrar uma nova categoria
    @PostMapping
    @Transactional
    public ResponseEntity<Categoria> cadastrar(@RequestBody @Valid DadosCategoria dadosCategoria) {
        try {
            Categoria categoria = new Categoria(dadosCategoria);
            Categoria categoriaSalvo = categoriaRepository.save(categoria);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalvo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Listar todas as categorias
    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        try {
            List<Categoria> categorias = categoriaRepository.findAll();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> getById(@PathVariable Long id) {
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Buscar categorias por título (opcional)
    @GetMapping("/search")
    public ResponseEntity<List<Categoria>> listarCategorias(@RequestParam(required = false) String titulo) {
        try {
            List<Categoria> categorias = (titulo != null && !titulo.isEmpty()) ?
                    categoriaRepository.findByTituloContainingIgnoreCase(titulo) :
                    categoriaRepository.findAll();
            return ResponseEntity.ok(categorias);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Remover uma categoria pelo ID
    @DeleteMapping("/{categoriaId}")
    public ResponseEntity<Categoria> remover(@PathVariable Long categoriaId) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        if (categoria.isPresent()) {
            categoriaRepository.deleteById(categoriaId);
            return ResponseEntity.ok(categoria.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Modificar uma categoria existente
    @PutMapping("/{categoriaId}")
    public ResponseEntity<Categoria> modificar(@PathVariable Long categoriaId, @RequestBody DadosCategoria dadosCategoria) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        if (categoria.isPresent()) {
            Categoria categoriaSalvo = categoria.get();

            categoriaSalvo.setTitulo(dadosCategoria.titulo()); // Atualiza o campo título
            categoriaSalvo.setCor(dadosCategoria.cor()); // Atualiza o campo cor

            Categoria categoriaAtualizado = categoriaRepository.save(categoriaSalvo);
            return ResponseEntity.ok(categoriaAtualizado);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
