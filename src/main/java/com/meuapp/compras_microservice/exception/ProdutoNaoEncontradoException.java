package com.meuapp.compras_microservice.exception;

public class ProdutoNaoEncontradoException extends RuntimeException {
    public ProdutoNaoEncontradoException(String codigo) {
        super("Produto com código " + codigo + " não encontrado.");
    }
}