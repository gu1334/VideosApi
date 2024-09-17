package com.semana.demo.videos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record DadosVideo(

        @NotBlank
        String titulo,

        @NotBlank
        String descricao,

        @NotBlank
        @URL
        String url ) {
}
