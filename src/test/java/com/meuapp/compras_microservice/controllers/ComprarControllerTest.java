package com.meuapp.compras_microservice.controllers;

import com.meuapp.compras_microservice.models.Cliente;
import com.meuapp.compras_microservice.models.Compra;
import com.meuapp.compras_microservice.models.Produto;
import com.meuapp.compras_microservice.service.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class ComprarControllerTest {

    @InjectMocks
    private ComprarController comprarController;

    @Mock
    private CompraService compraService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListarCompras() {
        // Crie instâncias reais ou mocks de Compra
        Compra compra1 =  Compra.builder().build();
        // Configure os atributos de compra1 conforme necessário

        Compra compra2 = Compra.builder().build();
        // Configure os atributos de compra2 conforme necessário

        List<Compra> compras = Arrays.asList(compra1, compra2);

        // Certifique-se de usar thenReturn() corretamente
        when(compraService.listarComprasOrdenadas()).thenReturn(compras);

        ResponseEntity<List<Compra>> response = comprarController.listarCompras();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void testMaiorCompra() {
        int ano = 2023;
        Compra maiorCompra = Compra.builder().build();
        when(compraService.buscarMaiorCompraPorAno(ano)).thenReturn(maiorCompra);

        ResponseEntity<Compra> response = comprarController.maiorCompra(ano);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull(); // Ajuste conforme necessário
    }

    @Test
    public void testListarClientesFieis() {
        Cliente cliente1 = Cliente.builder().build();
        Cliente cliente2 =  Cliente.builder().build();
        List<Cliente> clientesFieis = Arrays.asList(cliente1, cliente2);
        when(compraService.listarClientesMaisFieis()).thenReturn(clientesFieis);

        ResponseEntity<List<Cliente>> response = comprarController.listarClientesFieis();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull(); // Ajuste conforme necessário
    }

    @Test
    public void testRecomendarProduto() {
        String cpf = "12345678901";
        String tipo = "tinto";
        Produto produto1 = Produto.builder().build();
        Produto produto2 =  Produto.builder().build();
        List<Produto> produto = Arrays.asList(produto1, produto2);
        // Configure o produto conforme necessário

        when(compraService.recomendarVinho(cpf, tipo)).thenReturn(produto);

        ResponseEntity<Produto> response = comprarController.recomendarProduto(cpf, tipo);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
