package io.github.devopmarkz.produtoservice.repositories;

import io.github.devopmarkz.produtoservice.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Optional<Produto> findByNome(String nome);

}
