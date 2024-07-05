package com.bancojose.cartoes_ms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bancojose.cartoes_ms.model.CartaoProposta;
import com.bancojose.cartoes_ms.service.CartaoService;

@RestController
@RequestMapping("/propostas")
public class CartaoController {
    @Autowired
    private CartaoService cartaoService;

    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<CartaoProposta> criarProposta(@PathVariable Long clienteId, @RequestParam Double limite) {
        return ResponseEntity.ok(cartaoService.criarProposta(clienteId, limite));
    }

    @GetMapping
    public ResponseEntity<List<CartaoProposta>> listarPropostas() {
        return ResponseEntity.ok(cartaoService.listarPropostas());
    }

    @PutMapping("/{propostaId}/aprovar")
    public ResponseEntity<CartaoProposta> aprovarProposta(@PathVariable Long propostaId) {
        return ResponseEntity.ok(cartaoService.aprovarProposta(propostaId));
    }

    @PutMapping("/{propostaId}/rejeitar")
    public ResponseEntity<CartaoProposta> rejeitarProposta(@PathVariable Long propostaId) {
        return ResponseEntity.ok(cartaoService.rejeitarProposta(propostaId));
    }
}
