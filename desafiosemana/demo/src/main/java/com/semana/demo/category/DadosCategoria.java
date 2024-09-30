package com.semana.demo.category;

import jakarta.validation.constraints.NotBlank;

public record DadosCategoria (
        @NotBlank(message = "O título é obrigatório.")
        String titulo,
        @NotBlank(message = "A cor é obrigatório.")
        String cor
) {}
