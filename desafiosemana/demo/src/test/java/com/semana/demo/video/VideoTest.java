package com.semana.demo.video;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import com.semana.demo.category.Categoria;
import com.semana.demo.videos.Video;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VideoTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testVideoAttributes() {
        Video video = new Video();
        Categoria categoria = new Categoria();

        video.setTitulo("Um maluco no pedaço");
        video.setDescricao("serie sobre comedia");
        video.setUrl("https://www.url.com.br/");

        categoria.setId(1L);
        video.setCategoria(categoria);

        assertEquals(categoria, video.getCategoria());
        assertEquals("Um maluco no pedaço", video.getTitulo());
        assertEquals("serie sobre comedia", video.getDescricao());
        assertEquals("https://www.url.com.br/", video.getUrl());
    }

    @Test
    public void testVideoValidation() {
        Video video = new Video();
        Categoria categoria = new Categoria();
        categoria.setId(1L);

        video.setCategoria(categoria);
        video.setTitulo(null);
        video.setDescricao(null);
        video.setUrl(null);

        Set<ConstraintViolation<Video>> violations = validator.validate(video);
        assertFalse(violations.isEmpty());
        assertEquals(3, violations.size()); // Verifique o número de violações
    }
}
