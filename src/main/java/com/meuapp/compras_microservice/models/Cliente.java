package com.meuapp.compras_microservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@Builder
@AllArgsConstructor
public class Cliente {

    private String nome;
    private String cpf;
    private List<Compra> compras;
    private double valorTotal; // Novo atributo
    private int quantidadeCompras;
}
