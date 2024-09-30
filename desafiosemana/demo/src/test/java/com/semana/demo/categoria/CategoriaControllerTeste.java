package com.semana.demo.categoria;

import com.semana.demo.category.Categoria;
import com.semana.demo.category.CategoriaRepository;
import com.semana.demo.category.DadosCategoria;
import com.semana.demo.controller.CategoriaController;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaControllerTeste {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testeCadastrar() {
        // Dados da categoria a ser cadastrada
        DadosCategoria dadosCategoria = new DadosCategoria("Ação", "#FF0000");

        // Mock do repositório para retornar a categoria cadastrada
        Categoria categoriaMock = new Categoria();
        categoriaMock.setTitulo("Ação");
        categoriaMock.setCor("#FF0000");

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaMock);

        // Executa o método de cadastro
        ResponseEntity<Categoria> response = categoriaController.cadastrar(dadosCategoria);

        // Verifica se o status HTTP é CREATED (201)
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verifica se a categoria retornada é a esperada (mockada)
        assertEquals(categoriaMock, response.getBody());

        // Verifica se o método save foi chamado uma vez com qualquer instância de Categoria
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    public void testeListar() {
        // Mock da lista de categorias que o repositório deve retornar
        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("Terror");
        categoria1.setCor("preto");

        Categoria categoria2 = new Categoria();
        categoria2.setTitulo("Comédia");
        categoria2.setCor("amarelo");

        List<Categoria> listaCategorias = List.of(categoria1, categoria2);

        // Mock do repositório para retornar a lista de categorias
        when(categoriaRepository.findAll()).thenReturn(listaCategorias);

        // Executa o método listar
        ResponseEntity<List<Categoria>> response = categoriaController.listar();

        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a lista retornada é a esperada
        assertEquals(listaCategorias, response.getBody());

        // Verifica se o método findAll foi chamado uma vez
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    public void testeBuscarPorId() {
        // Mock da categoria
        Categoria categoriaMock = new Categoria();
        categoriaMock.setTitulo("Ação");
        categoriaMock.setCor("#FF0000");

        // Mock do repositório para retornar a categoria quando buscada pelo ID
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));

        // Executa o método buscarPorId no controlador
        ResponseEntity<Categoria> response = categoriaController.getById(1L);

        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a categoria retornada é a esperada
        assertEquals(categoriaMock, response.getBody());

        // Verifica se o método findById foi chamado uma vez
        verify(categoriaRepository, times(1)).findById(1L);
    }

    @Test
    public void testeBuscarPorIdCategoriaNaoEncontrada() {
        // Mock do repositório para não encontrar a categoria
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaController.getById(1L);
        });
    }

    @Test
    public void testeListarCategoriasPorTitulo() {
        // Mock da lista de categorias que o repositório deve retornar
        Categoria categoria1 = new Categoria();
        categoria1.setTitulo("Ação");
        categoria1.setCor("#FF0000");

        Categoria categoria2 = new Categoria();
        categoria2.setTitulo("Comédia");
        categoria2.setCor("#00FF00");

        List<Categoria> listaCategorias = List.of(categoria1, categoria2);

        // Mock do repositório para retornar a lista de categorias
        when(categoriaRepository.findByTituloContainingIgnoreCase("Ação")).thenReturn(List.of(categoria1));

        // Executa o método listarCategorias com um título específico
        ResponseEntity<List<Categoria>> response = categoriaController.listarCategorias("Ação");

        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a lista retornada é a esperada
        assertEquals(List.of(categoria1), response.getBody());

        // Verifica se o método findByTituloContainingIgnoreCase foi chamado uma vez
        verify(categoriaRepository, times(1)).findByTituloContainingIgnoreCase("Ação");
    }

    @Test
    public void testeRemover() {
        // Mock da categoria
        Categoria categoriaMock = new Categoria();
        categoriaMock.setTitulo("Ação");
        categoriaMock.setCor("#FF0000");

        // Mock do repositório para retornar a categoria quando buscada pelo ID
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));

        // Executa o método remover
        ResponseEntity<Categoria> response = categoriaController.remover(1L);

        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a categoria retornada é a esperada
        assertEquals(categoriaMock, response.getBody());

        // Verifica se o método deleteById foi chamado uma vez
        verify(categoriaRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testeRemoverCategoriaNaoEncontrada() {
        // Mock do repositório para não encontrar a categoria
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica se a exceção é lançada
        assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaController.remover(1L);
        });
    }

    @Test
    public void testeModificar() {
        // Mock da categoria existente
        Categoria categoriaMock = new Categoria();
        categoriaMock.setTitulo("Ação");
        categoriaMock.setCor("#FF0000");

        DadosCategoria dadosCategoria = new DadosCategoria("Ação Atualizada", "#00FF00");

        // Mock do repositório para retornar a categoria quando buscada pelo ID
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoriaMock));

        // Mock do repositório para salvar a categoria atualizada
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoriaMock);

        // Executa o método modificar
        ResponseEntity<Categoria> response = categoriaController.modificar(1L, dadosCategoria);

        // Verifica se o status HTTP é OK (200)
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verifica se a categoria retornada é a esperada
        assertEquals(categoriaMock, response.getBody());

        // Verifica se o método save foi chamado uma vez
        verify(categoriaRepository, times(1)).save(any(Categoria.class));
    }

    @Test
    public void testeModificarCategoriaNaoEncontrada() {
        // Mock do repositório para não encontrar a categoria
        when(categoriaRepository.findById(1L)).thenReturn(Optional.empty());

        // Dados da categoria
        DadosCategoria dadosCategoria = new DadosCategoria("Ação Atualizada", "#00FF00");

        // Verifica se a exceção é lançada
        assertThrows(RecursoNaoEncontradoException.class, () -> {
            categoriaController.modificar(1L, dadosCategoria);
        });
    }
}
