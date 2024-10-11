package com.meuapp.compras_microservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
public class Compra {

    private String codigo;
    private int quantidade;
    private Cliente cliente;
    private Produto produto;
    private double valorTotal;

}
