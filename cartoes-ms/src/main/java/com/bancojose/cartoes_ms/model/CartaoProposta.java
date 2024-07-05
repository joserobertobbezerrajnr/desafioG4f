package com.bancojose.cartoes_ms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoProposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double limiteProposto;
    private String status; // PENDING, APPROVED, REJECTED
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
