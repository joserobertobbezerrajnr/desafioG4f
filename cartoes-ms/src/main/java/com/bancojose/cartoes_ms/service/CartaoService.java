package com.bancojose.cartoes_ms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return cartaoPropostaRepository.save(proposta);
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
}
