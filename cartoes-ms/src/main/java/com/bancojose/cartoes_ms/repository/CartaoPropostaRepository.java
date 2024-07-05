package com.bancojose.cartoes_ms.repository;

import com.bancojose.cartoes_ms.model.CartaoProposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoPropostaRepository extends JpaRepository<CartaoProposta, Long> {
	
}
