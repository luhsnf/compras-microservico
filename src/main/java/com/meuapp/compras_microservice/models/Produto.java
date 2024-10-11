package com.meuapp.compras_microservice.models;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Produto {

    private String codigo;
    private String tipo_vinho;
    private double preco;
    private String safra;
    private Integer anoCompra;

}
