package com.meuapp.compras_microservice.service;

import com.meuapp.compras_microservice.exception.ClienteNaoEncontradoException;
import com.meuapp.compras_microservice.models.Cliente;
import com.meuapp.compras_microservice.models.Compra;
import com.meuapp.compras_microservice.models.Produto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CompraServiceTest {

    @InjectMocks
    private CompraService compraService;

    @Mock
    private DadosService dadosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarComprasOrdenadas() {
        Cliente cliente = Cliente.builder().build(); // Preencher com dados de teste
        cliente.setCompras(Arrays.asList(Compra.builder().build()));

        Produto produto =  Produto.builder().build();
        when(dadosService.carregarClientes()).thenReturn(Collections.singletonList(cliente));
        when(dadosService.carregarProdutos()).thenReturn(Collections.singletonList(produto));

        List<Compra> compras = compraService.listarComprasOrdenadas();

        assertNotNull(compras);
        assertFalse(compras.isEmpty());
    }

    @Test
    void testRecomendarVinho_ClienteNaoEncontrado() {
        String cpf = "12345678901";
        String tipo = "tinto";

        when(dadosService.carregarClientePorCpf(cpf)).thenReturn(null);

        assertThrows(ClienteNaoEncontradoException.class, () -> {
            compraService.recomendarVinho(cpf, tipo);
        });
    }

}
