package com.semana.demo.controller;

import com.semana.demo.category.Categoria;
import com.semana.demo.category.CategoriaRepository;
import com.semana.demo.exceptions.RecursoNaoEncontradoException;
import com.semana.demo.videos.DadosVideo;
import com.semana.demo.videos.Video;
import com.semana.demo.videos.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/videos")
public class VideosController {

    @Autowired
    private VideoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Cadastrar um novo vídeo
    @PostMapping
    public ResponseEntity<Video> cadastrar(@RequestBody @Valid DadosVideo dados) {
        if (dados.categoriaId() == null) {
            throw new IllegalArgumentException("O ID da categoria não pode ser nulo.");
        }

        Categoria categoria = categoriaRepository.findById(dados.categoriaId())
                .orElseGet(() -> categoriaRepository.findById(1L)
                        .orElseThrow(() -> new EntityNotFoundException("Categoria padrão não encontrada com ID 1")));

        Video video = new Video(dados, categoria);
        repository.save(video);
        return ResponseEntity.status(HttpStatus.CREATED).body(video);
    }

    // Listar todos os vídeos
    @GetMapping
    public ResponseEntity<List<Video>> listar() {
        List<Video> videos = repository.findAll();
        return ResponseEntity.ok(videos);
    }

    // Buscar vídeo por ID
    @GetMapping("/{id}")
    public ResponseEntity<Video> buscarPorId(@PathVariable Long id) {
        Optional<Video> video = repository.findById(id);

        if (video.isPresent()) {
            return ResponseEntity.ok(video.get());
        } else {
            throw new RecursoNaoEncontradoException("Vídeo com ID " + id + " não foi encontrado.");
        }
    }

    // Buscar vídeos por categoria
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Video>> mostrarVideosPorCategoria(@PathVariable Long categoriaId) {
        Optional<Categoria> categoria = categoriaRepository.findById(categoriaId);
        if (categoria.isPresent()) {
            List<Video> videos = repository.findByCategoria(categoria.get());
            return ResponseEntity.ok(videos);
        } else {
            throw new RecursoNaoEncontradoException("Video com categoria de ID " + categoriaId + " não foi encontrado.");
        }
    }

    // Buscar vídeos por título
    @GetMapping("/search")
    public ResponseEntity<List<Video>> listarVideos(@RequestParam(required = false) String titulo) {
        List<Video> videos = (titulo != null && !titulo.isEmpty()) ?
                repository.findByTituloContainingIgnoreCase(titulo) :
                repository.findAll();

        if (videos.isEmpty()) {
            throw new RecursoNaoEncontradoException("Titulo " + titulo + " não encontrado.");
        }

        return ResponseEntity.ok(videos);
    }

    @GetMapping("/page")
    public ResponseEntity<?> paginas(@PageableDefault(size = 5) Pageable pageable) {
        try {
            Page<Video> videos = repository.findAll(pageable);
            return ResponseEntity.ok(videos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }



    // Remover vídeo por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Video> remover(@PathVariable Long id) {
        Optional<Video> video = repository.findById(id);
        if (video.isPresent()) {
            repository.deleteById(id);
            return ResponseEntity.ok(video.get());
        } else {
            throw new RecursoNaoEncontradoException("Vídeo com ID " + id + " não foi encontrado.");
        }
    }

    // Atualizar um vídeo existente
    @PutMapping("/{id}")
    public ResponseEntity<Video> atualizar(@PathVariable Long id, @RequestBody @Valid DadosVideo dados) {
        Optional<Video> videoOptional = repository.findById(id);
        if (videoOptional.isPresent()) {
            Video videoExistente = videoOptional.get();

            if (dados.categoriaId(  ) == null) {
                throw new IllegalArgumentException("O ID da categoria não pode ser nulo.");
            }

            Categoria categoria = categoriaRepository.findById(dados.categoriaId())
                    .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

            videoExistente.setTitulo(dados.titulo());
            videoExistente.setDescricao(dados.descricao());
            videoExistente.setCategoria(categoria);

            Video videoAtualizado = repository.save(videoExistente);
            return ResponseEntity.ok(videoAtualizado);
        } else {
            throw new RecursoNaoEncontradoException("Vídeo com ID " + id + " não foi encontrado.");
        }
    }
}
