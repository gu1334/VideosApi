package com.semana.demo.video;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;


import com.semana.demo.categorias.Categoria;
import com.semana.demo.videos.Video;

import org.junit.Test;


import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VideoTest {
    @Test
    public void test() {



        Video video = new Video();
        Categoria categoria = new Categoria();

        video.setTitulo("Um maluco no pedaço");
        video.setDescricao("serie sobre comedia");
        video.setUrl("https://www.url.com.br/");


        categoria.setId(1l);
        video.setCategoria(categoria);

        assertEquals(categoria, video.getCategoria());
        assertEquals("Um maluco no pedaço", video.getTitulo());
        assertEquals("serie sobre comedia", video.getDescricao());
        assertEquals("https://www.url.com.br/", video.getUrl());


    }
    @Test
    public void testValidations() {
        // Criar Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        // Instância inválida de Video
        Video video = new Video();
        Categoria categoria = new Categoria();
        categoria.setId(1l);


        video.setCategoria(categoria);
        video.setTitulo(null);
        video.setDescricao(null);
        video.setUrl(null);

        // Validar o objeto
        Set<ConstraintViolation<Video>> violations = validator.validate(video);

        // Verificar se há violações
        assertFalse(violations.isEmpty());  // Deve haver violações, já que o título é nulo
    }
}
