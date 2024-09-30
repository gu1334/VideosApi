package com.semana.demo.video;

import com.semana.demo.videos.DadosVideo;
import com.semana.demo.videos.Video;
import com.semana.demo.videos.VideoRepository;
import com.semana.demo.category.Categoria;
import com.semana.demo.category.CategoriaRepository;
import com.semana.demo.controller.VideosController;
import com.semana.demo.exceptions.RecursoNaoEncontradoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class VideosControllerTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private VideosController videosController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeCadastrar() {
        DadosVideo dadosVideo = new DadosVideo(3L, "Um maluco no pedaço", "Série de comédia", "https://www.url.com.br/");
        Categoria categoriaMock = new Categoria();
        when(categoriaRepository.findById(dadosVideo.categoriaId())).thenReturn(Optional.of(categoriaMock));
        Video videoMock = new Video(dadosVideo, categoriaMock);
        when(videoRepository.save(any(Video.class))).thenReturn(videoMock);

        ResponseEntity<Video> response = videosController.cadastrar(dadosVideo);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(videoMock, response.getBody());
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    public void testeListar() {
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        Video video1 = new Video(1L, "Título do vídeo 1", "Descrição do vídeo 1", "https://www.url.com.br/", categoriaMock);
        Video video2 = new Video(2L, "Título do vídeo 2", "Descrição do vídeo 2", "https://www.url.com.br/", categoriaMock);
        List<Video> listaVideos = List.of(video1, video2);
        when(videoRepository.findAll()).thenReturn(listaVideos);

        ResponseEntity<List<Video>> response = videosController.listar();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(listaVideos, response.getBody());
        verify(videoRepository, times(1)).findAll();
    }

    @Test
    public void testeBuscarPorId() {
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(2L);
        Video video = new Video(2L, "Título do vídeo 2", "Descrição do vídeo 2", "https://www.url.com.br/", categoriaMock);
        when(videoRepository.findById(2L)).thenReturn(Optional.of(video));

        ResponseEntity<Video> response = videosController.buscarPorId(2L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(video, response.getBody());
        verify(videoRepository, times(1)).findById(2L);
    }

    @Test
    public void testeBuscarPorIdNotFound() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> videosController.buscarPorId(1L));
    }

    @Test
    public void testeMostrarVideosPorCategoria() {
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(2L);
        Video video1 = new Video(1L, "Título do vídeo 1", "Descrição do vídeo 1", "https://www.url.com.br/", categoriaMock);
        Video video2 = new Video(2L, "Título do vídeo 2", "Descrição do vídeo 2", "https://www.url.com.br/", categoriaMock);
        List<Video> videosMock = List.of(video1, video2);
        when(categoriaRepository.findById(categoriaMock.getId())).thenReturn(Optional.of(categoriaMock));
        when(videoRepository.findByCategoria(categoriaMock)).thenReturn(videosMock);

        ResponseEntity<List<Video>> response = videosController.mostrarVideosPorCategoria(categoriaMock.getId());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(videosMock, response.getBody());
        verify(videoRepository, times(1)).findByCategoria(categoriaMock);
    }

    @Test
    public void testeMostrarVideosPorCategoriaNotFound() {
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> videosController.mostrarVideosPorCategoria(1L));
    }

    @Test
    public void testeListarVideosPorTitulo() {
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        Video video1 = new Video(1L, "Título do vídeo 1", "Descrição do vídeo 1", "https://www.url.com.br/", categoriaMock);
        List<Video> videosMock = List.of(video1);
        when(videoRepository.findByTituloContainingIgnoreCase("Título")).thenReturn(videosMock);

        ResponseEntity<List<Video>> response = videosController.listarVideos("Título");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(videosMock, response.getBody());
        verify(videoRepository, times(1)).findByTituloContainingIgnoreCase("Título");
    }

    @Test
    public void testeListarVideosPorTituloNotFound() {
        when(videoRepository.findByTituloContainingIgnoreCase("Título")).thenReturn(List.of());
        assertThrows(RecursoNaoEncontradoException.class, () -> videosController.listarVideos("Título"));
    }

    @Test
    public void testeRemover() {
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        Video video = new Video(1L, "Título do vídeo", "Descrição do vídeo", "https://www.url.com.br/", categoriaMock);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));

        ResponseEntity<Video> response = videosController.remover(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(video, response.getBody());
        verify(videoRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testeRemoverNotFound() {
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> videosController.remover(1L));
    }

    @Test
    public void testeAtualizar() {
        Categoria categoriaMock = new Categoria();
        categoriaMock.setId(1L);
        Video video = new Video(1L, "Título antigo", "Descrição antiga", "https://www.url.com.br/", categoriaMock);
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        DadosVideo dadosVideo = new DadosVideo(1L, "Título novo", "Descrição nova", "https://www.url.com.br/");
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));
        when(videoRepository.save(video)).thenReturn(video);

        ResponseEntity<Video> response = videosController.atualizar(1L, dadosVideo);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(video, response.getBody());
        assertEquals("Título novo", video.getTitulo());
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    public void testeAtualizarNotFound() {
        DadosVideo dadosVideo = new DadosVideo(1L, "Título novo", "Descrição nova", "https://www.url.com.br/");
        when(videoRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> videosController.atualizar(1L, dadosVideo));
    }
}
