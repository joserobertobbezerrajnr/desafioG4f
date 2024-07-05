package com.bancojose.cartoes_ms.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bancojose.cartoes_ms.client.EmissorClient;
import com.bancojose.cartoes_ms.model.CartaoProposta;
import com.bancojose.cartoes_ms.model.Cliente;
import com.bancojose.cartoes_ms.repository.CartaoPropostaRepository;
import com.bancojose.cartoes_ms.repository.ClienteRepository;

class CartaoServiceTest {

    @Mock
    private CartaoPropostaRepository cartaoPropostaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmissorClient emissorClient;

    @InjectMocks
    private CartaoService cartaoService;

    public CartaoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCriarProposta() {
        Long clienteId = 1L;
        Double limiteProposto = 5000.0;
        Cliente cliente = new Cliente(clienteId, "Teste Cliente", "teste@cliente.com", "12345678901");

        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));

        CartaoProposta proposta = cartaoService.criarProposta(clienteId, limiteProposto);

        assertNotNull(proposta);
        assertEquals(cliente, proposta.getCliente());
        assertEquals(limiteProposto, proposta.getLimiteProposto());
        assertEquals("PENDING", proposta.getStatus());
        verify(cartaoPropostaRepository, times(1)).save(proposta);
    }

    @Test
    void testAprovarProposta() {
        Long propostaId = 1L;
        Cliente cliente = new Cliente(1L, "Teste Cliente", "teste@cliente.com", "12345678901");
        CartaoProposta proposta = new CartaoProposta(propostaId, 5000.0, "PENDING", cliente);

        when(cartaoPropostaRepository.findById(propostaId)).thenReturn(Optional.of(proposta));

        CartaoProposta propostaAprovada = cartaoService.aprovarProposta(propostaId);

        assertNotNull(propostaAprovada);
        assertEquals("APPROVED", propostaAprovada.getStatus());
        verify(cartaoPropostaRepository, times(1)).save(proposta);
        verify(emissorClient, times(1)).enviarEmail(eq(cliente.getEmail()), eq(cliente.getNome()), anyString(), eq("12/28"), anyString());
    }

    @Test
    void testRejeitarProposta() {
        Long propostaId = 1L;
        Cliente cliente = new Cliente(1L, "Teste Cliente", "teste@cliente.com", "12345678901");
        CartaoProposta proposta = new CartaoProposta(propostaId, 5000.0, "PENDING", cliente);

        when(cartaoPropostaRepository.findById(propostaId)).thenReturn(Optional.of(proposta));

        CartaoProposta propostaRejeitada = cartaoService.rejeitarProposta(propostaId);

        assertNotNull(propostaRejeitada);
        assertEquals("REJECTED", propostaRejeitada.getStatus());
        verify(cartaoPropostaRepository, times(1)).save(proposta);
    }
}
