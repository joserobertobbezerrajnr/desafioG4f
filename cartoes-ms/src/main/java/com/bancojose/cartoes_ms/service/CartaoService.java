package com.bancojose.cartoes_ms.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bancojose.cartoes_ms.client.EmissorClient;
import com.bancojose.cartoes_ms.model.CartaoProposta;
import com.bancojose.cartoes_ms.model.Cliente;
import com.bancojose.cartoes_ms.repository.CartaoPropostaRepository;
import com.bancojose.cartoes_ms.repository.ClienteRepository;

@Service
public class CartaoService {
    @Autowired
    private CartaoPropostaRepository cartaoPropostaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private EmissorClient emissorClient;

    public CartaoProposta criarProposta(Long clienteId, Double limiteProposto) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
            CartaoProposta proposta = new CartaoProposta();
            proposta.setCliente(cliente);
            proposta.setLimiteProposto(limiteProposto);
            proposta.setStatus("PENDING");
            return cartaoPropostaRepository.save(proposta);
        }
        throw new RuntimeException("Cliente não encontrado");
    }

    public List<CartaoProposta> listarPropostas() {
        return cartaoPropostaRepository.findAll();
    }

    public CartaoProposta aprovarProposta(Long propostaId) {
        Optional<CartaoProposta> propostaOpt = cartaoPropostaRepository.findById(propostaId);
        if (propostaOpt.isPresent()) {
            CartaoProposta proposta = propostaOpt.get();
            proposta.setStatus("APPROVED");
            cartaoPropostaRepository.save(proposta);

            // Gerar dados do cartão
            String numeroCartao = gerarNumeroCartao();
            String validade = "12/28";  // Exemplo
            String cvv = gerarCVV();

            // Enviar notificação por e-mail
            emissorClient.enviarEmail(proposta.getCliente().getEmail(), proposta.getCliente().getNome(), numeroCartao, validade, cvv);

            return proposta;
        }
        throw new RuntimeException("Proposta não encontrada");
    }

    public CartaoProposta rejeitarProposta(Long propostaId) {
        Optional<CartaoProposta> propostaOpt = cartaoPropostaRepository.findById(propostaId);
        if (propostaOpt.isPresent()) {
            CartaoProposta proposta = propostaOpt.get();
            proposta.setStatus("REJECTED");
            return cartaoPropostaRepository.save(proposta);
        }
        throw new RuntimeException("Proposta não encontrada");
    }
    
    private String gerarNumeroCartao() {
        Random rand = new Random();
        return String.format("%04d %04d %04d %04d", 
                             rand.nextInt(10000), 
                             rand.nextInt(10000), 
                             rand.nextInt(10000), 
                             rand.nextInt(10000));
    }

    private String gerarCVV() {
        Random rand = new Random();
        return String.format("%03d", rand.nextInt(1000));
    }
}
