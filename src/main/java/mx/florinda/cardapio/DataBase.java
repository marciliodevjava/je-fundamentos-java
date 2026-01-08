package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface DataBase {
    List<ItemCardapio> add();

    List<ItemCardapio> listaDeCardapio();

    Optional<ItemCardapio> getById(int num);

    boolean alterarPrecoItemCardapio(int id, BigDecimal preco);

    int totalItemCardapio();

    void adicionaItemCardapio(ItemCardapio itemCardapio);
}
