package mx.florinda.cardapio;

import java.math.BigDecimal;

public record ItemCardapio(int id, String nome, String descricao,
                           CategoriaCardapio categoria, BigDecimal preco,
                           BigDecimal precoComDesconto) {

    public ItemCardapio alterarPreco(BigDecimal novoPreco) {
        return new ItemCardapio(id, nome, descricao, categoria, novoPreco, precoComDesconto);
    }
}
