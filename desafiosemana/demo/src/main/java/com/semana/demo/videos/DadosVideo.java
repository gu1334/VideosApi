package com.semana.demo.videos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record DadosVideo(
        @NotNull
        Long categoriaId, // Alterado para Long para aceitar o ID
        @NotBlank(message = "O título é obrigatório.")
        String titulo,
        @NotBlank(message = "Descrição é obrigatório.")
        String descricao,
        @NotBlank(message = "URL é obrigatório.")
        @URL
        String url
) {}
