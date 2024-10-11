package com.meuapp.compras_microservice.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuapp.compras_microservice.exception.ClienteNaoEncontradoException;
import com.meuapp.compras_microservice.exception.ProdutoNaoEncontradoException;
import com.meuapp.compras_microservice.models.Cliente;
import com.meuapp.compras_microservice.models.Compra;
import com.meuapp.compras_microservice.models.Produto;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompraService {
    private static final Logger log = LoggerFactory.getLogger(CompraService.class);

    @Autowired
    private DadosService dadosService;

    public List<Compra> listarComprasOrdenadas() {
        List<Cliente> clientes = dadosService.carregarClientes();
        List<Produto> produtos = dadosService.carregarProdutos();

        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            Produto produto = produtos.stream()
                                    .filter(p -> p.getCodigo() != null && p.getCodigo().equals(compra.getCodigo()))
                                    .findFirst()
                                    .orElse(null);
                            double valorTotal = produto != null ? produto.getPreco() * compra.getQuantidade() : 0;

                            return Compra.builder()
                                    .codigo(compra.getCodigo())
                                    .quantidade(compra.getQuantidade())
                                    .cliente(cliente)
                                    .produto(produto)
                                    .valorTotal(valorTotal)
                                    .build();
                        }))
                .sorted(Comparator.comparingDouble(Compra::getValorTotal))
                .collect(Collectors.toList());
    }

    public Compra buscarMaiorCompraPorAno(int ano) {
        List<Cliente> clientes = dadosService.carregarClientes();
        List<Produto> produtos = dadosService.carregarProdutos();

        return clientes.stream()
                .flatMap(cliente -> cliente.getCompras().stream()
                        .map(compra -> {
                            Produto produto = produtos.stream()
                                    .filter(p -> p.getCodigo().equals(compra.getCodigo()))
                                    .filter(p -> p.getAnoCompra() != null && p.getAnoCompra().equals(Integer.parseInt(String.valueOf(ano)))) // Verifica o ano da compra
                                    .findFirst()
                                    .orElse(null);
                            double valorTotal = produto != null ? produto.getPreco() * compra.getQuantidade() : 0;

                            return produto != null ? Compra.builder()
                                    .codigo(compra.getCodigo())
                                    .quantidade(compra.getQuantidade())
                                    .cliente(cliente)
                                    .produto(produto)
                                    .valorTotal(valorTotal)
                                    .build() : null;
                        }))
                .filter(Objects::nonNull)
                .max(Comparator.comparingDouble(Compra::getValorTotal))
                .orElse(null);
    }

    public List<Cliente> listarClientesMaisFieis() {
        List<Cliente> clientes = dadosService.carregarClientes();

        return clientes.stream()
                .map(cliente -> {
                    double total = cliente.getCompras().stream()
                            .mapToDouble(compra -> compra.getValorTotal())
                            .sum();
                    int quantidade = cliente.getCompras().size();
                    cliente.setValorTotal(total);
                    cliente.setQuantidadeCompras(quantidade);
                    return cliente;
                })
                .sorted(Comparator.comparingDouble(Cliente::getValorTotal).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Produto> recomendarVinho(String cpf, String tipo) {
        Cliente cliente = dadosService.carregarClientePorCpf(cpf);

        if (cliente == null) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado");
        }

        Map<String, Long> tipoVinhoCount = cliente.getCompras().stream()
                .map(compra -> compra.getProduto().getTipo_vinho())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        String tipoVinhoMaisComprado = Collections.max(tipoVinhoCount.entrySet(),
                Map.Entry.comparingByValue()).getKey();

        List<Produto> produtos = dadosService.carregarProdutos().stream()
                .filter(produto -> produto.getTipo_vinho().equals(tipoVinhoMaisComprado))
                .collect(Collectors.toList());

        if (produtos.isEmpty()) {
            throw new ProdutoNaoEncontradoException("Nenhum produto encontrado para recomendação");
        }

        return Collections.singletonList(produtos.get(new Random().nextInt(produtos.size()))); // Retorna um produto aleatório
    }
}
