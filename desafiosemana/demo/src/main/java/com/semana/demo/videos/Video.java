package com.semana.demo.videos;

import com.semana.demo.categorias.Categoria;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "banco")
@Entity(name = "video")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private String url;

    @ManyToOne
    @JoinColumn(name = "categoria_id") // Nome correto da coluna que armazena a referência
    private Categoria categoria; // Renomeado para 'categoria' em vez de 'categoriaId'

    // Construtor com DadosVideo
    public Video(DadosVideo dados, Categoria categoria) {
        this.titulo = dados.titulo();
        this.descricao = dados.descricao();
        this.url = dados.url();
        this.categoria = categoria; // Atribuir diretamente a categoria recebida

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
