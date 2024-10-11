package com.meuapp.compras_microservice.service;

import com.meuapp.compras_microservice.exception.ClienteNaoEncontradoException;
import com.meuapp.compras_microservice.models.Cliente;
import com.meuapp.compras_microservice.models.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class DadosService {

    private final WebClient webClient;

    @Autowired
    public DadosService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Produto> carregarProdutos() {
        String url = "https://rgr3viiqdl8sikgv.public.blob.vercelstorage.com/produtos-mnboX5IPl6VgG390FECTKqHsD9SkLS.json";
        Produto[] produtos = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Produto[].class)
                .block(); // Usar block() para aguardar a resposta
        return Arrays.asList(produtos);
    }

    public List<Cliente> carregarClientes() {
        String url = "https://rgr3viiqdl8sikgv.public.blob.vercelstorage.com/clientes-Vz1U6aR3GTsjb3W8BRJhcNKmA81pVh.json";
        Cliente[] clientes = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(Cliente[].class)
                .block(); // Usar block() para aguardar a resposta
        return Arrays.asList(clientes);
    }

    public Cliente carregarClientePorCpf(String cpf) {
        List<Cliente> clientes = carregarClientes();
        return clientes.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElseThrow(() -> new ClienteNaoEncontradoException(cpf));
    }

}
