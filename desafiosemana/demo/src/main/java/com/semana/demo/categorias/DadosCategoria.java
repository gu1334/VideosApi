package com.semana.demo.categorias;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DadosCategoria (
        @NotBlank(message = "O título é obrigatório.")
        String titulo,
        @NotBlank(message = "A cor é obrigatório.")
        String cor
) {}
