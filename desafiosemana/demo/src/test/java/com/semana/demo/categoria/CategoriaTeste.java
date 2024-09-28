package com.semana.demo.categoria;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import com.semana.demo.categorias.Categoria;
import com.semana.demo.categorias.DadosCategoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CategoriaTeste {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCategoriaAttributes() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setTitulo("Ação");
        categoria.setCor("#FF0000");

        assertEquals(1L, categoria.getId());
        assertEquals("Ação", categoria.getTitulo());
        assertEquals("#FF0000", categoria.getCor());
    }

    @Test
    public void testCategoriaValidation() {
        Categoria categoria = new Categoria();

        categoria.setId(1L);
        categoria.setTitulo(null); // Título nulo para testar a validação
        categoria.setCor(null); // Cor nula para testar a validação

        Set<ConstraintViolation<Categoria>> violations = validator.validate(categoria);
        assertFalse(violations.isEmpty());
        assertEquals(2, violations.size()); // Verifique o número de violações
    }

    @Test
    public void testDadosCategoriaMapping() {
        DadosCategoria dadosCategoria = new DadosCategoria("Drama", "#00FF00");
        Categoria categoria = new Categoria(dadosCategoria);

        assertEquals("Drama", categoria.getTitulo());
        assertEquals("#00FF00", categoria.getCor());
    }
}
