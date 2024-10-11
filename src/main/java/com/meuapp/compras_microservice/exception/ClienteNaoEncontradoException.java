package com.meuapp.compras_microservice.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente com CPF " + cpf + " não encontrado.");
    }
}