package com.meuapp.compras_microservice.controllers;

import com.meuapp.compras_microservice.models.Cliente;
import com.meuapp.compras_microservice.models.Compra;
import com.meuapp.compras_microservice.models.Produto;
import com.meuapp.compras_microservice.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ComprarController {

    @Autowired
    private CompraService compraService;

    @GetMapping("/compras")
    public ResponseEntity<List<Compra>> listarCompras() {
        compraService.listarComprasOrdenadas();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/maior-compra/{ano}")
    public  ResponseEntity<Compra> maiorCompra(@PathVariable int ano) {
        Compra maiorCompra = compraService.buscarMaiorCompraPorAno(ano);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/clientes-fieis")
    public ResponseEntity<List<Cliente>> listarClientesFieis() {
        compraService.listarClientesMaisFieis();
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/recomendacao/{cpf}/{tipo}")
    public ResponseEntity<Produto> recomendarProduto(@PathVariable String cpf, @PathVariable String tipo) {
        compraService.recomendarVinho(cpf, tipo);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
