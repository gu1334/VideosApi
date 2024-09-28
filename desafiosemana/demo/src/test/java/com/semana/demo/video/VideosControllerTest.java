package com.semana.demo.video;

import com.semana.demo.videos.DadosVideo;
import com.semana.demo.videos.Video;
import com.semana.demo.videos.VideoRepository;
import com.semana.demo.controller.VideosController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VideosControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @InjectMocks
    private VideosController videosController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeCadastrar() {
        // Dados do vídeo a ser cadastrado
        DadosVideo dadosVideo = new DadosVideo(5L, "Um maluco no pedaço", "Série de comédia", "https://www.url.com.br/");

        // Mock do retorno do repositório ao salvar o vídeo
        Video videoMock = new Video();
        when(videoRepository.save(any(Video.class))).thenReturn(videoMock);

        // Executa o método de cadastro
        ResponseEntity<Video> response = videosController.cadastrar(dadosVideo);

        // Verifica se o status HTTP é CREATED (201)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica se o vídeo retornado é o esperado (mockado)
        assertEquals(videoMock, response.getBody());

        // Verifica se o método save foi chamado uma vez com qualquer instância de Video
        verify(videoRepository, times(1)).save(any(Video.class));
    }
}
