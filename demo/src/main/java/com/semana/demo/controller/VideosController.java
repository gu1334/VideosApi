package com.semana.demo.controller;

import com.semana.demo.videos.DadosVideo;
import com.semana.demo.videos.Video;
import com.semana.demo.videos.VideoRepository;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("videos")
public class VideosController {  // Corrigido para seguir a convenção de nomenclatura CamelCase

    @Autowired
    private VideoRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity<Video> cadastrar(@RequestBody @Valid DadosVideo dados) {
        Video video = new Video(dados);
        try {
            Video savedVideo = repository.save(video);
            return new ResponseEntity<>(savedVideo, HttpStatus.CREATED); // Retorna o objeto Video salvo e um status 201 Created
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Transactional
    public ResponseEntity<List<Video>> listar() {
        try {
            return new ResponseEntity<>(repository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Video> getById(@PathVariable Integer id) {
        Optional<Video> video = repository.findById(Long.valueOf(id));
        if (video.isPresent()) {
            return new ResponseEntity<>(video.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Video> remover(@PathVariable Integer id) {
        Optional<Video> video = repository.findById(Long.valueOf(id));
        if (video.isPresent()) {
            repository.deleteById(Long.valueOf(id));
            return new ResponseEntity<>(video.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Video> atualizar(@PathVariable Integer id, @RequestBody @Valid DadosVideo dados) {
        Optional<Video> videoOptional = repository.findById(Long.valueOf(id));
        if (videoOptional.isPresent()) {
            Video videoExistente = videoOptional.get();

            videoExistente.setTitulo(dados.titulo());
            videoExistente.setDescricao(dados.descricao());


            Video videoAtualizado = repository.save(videoExistente);

            return new ResponseEntity<>(videoAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
