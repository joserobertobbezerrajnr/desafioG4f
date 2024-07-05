package com.bancojose.emissor_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bancojose.emissor_ms.service.EmailService;

@RestController
@RequestMapping("/emissao")
public class EmissorController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/enviar-email")
    public ResponseEntity<String> enviarEmail(
            @RequestParam String emailDestino,
            @RequestParam String nomeCliente,
            @RequestParam String numeroCartao,
            @RequestParam String validade,
            @RequestParam String cvv) {
        String assunto = "Seu Cartão de Crédito Foi Emitido";
        String mensagem = String.format("Olá %s,\n\nSeu cartão de crédito foi emitido com sucesso!\n\n" +
                        "Detalhes do Cartão:\nNúmero: %s\nValidade: %s\nCVV: %s\n\n" +
                        "Obrigado por escolher nosso banco!",
                nomeCliente, numeroCartao, validade, cvv);

        emailService.sendSimpleMessage(emailDestino, assunto, mensagem);

        return ResponseEntity.ok("Email enviado com sucesso!");
    }
}
