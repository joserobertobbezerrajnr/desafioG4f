package com.bancojose.cartoes_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "emissor-ms", url = "http://localhost:8081/emissao")
public interface EmissorClient {
    @PostMapping("/enviar-email")
    void enviarEmail(
            @RequestParam("emailDestino") String emailDestino,
            @RequestParam("nomeCliente") String nomeCliente,
            @RequestParam("numeroCartao") String numeroCartao,
            @RequestParam("validade") String validade,
            @RequestParam("cvv") String cvv);
}
