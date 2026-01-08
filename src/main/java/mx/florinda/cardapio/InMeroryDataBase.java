package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMeroryDataBase implements DataBase {
    private List<ItemCardapio> list = new ArrayList<>();

    public InMeroryDataBase() {
        this.add();
    }

    @Override
    public List<ItemCardapio> add() {
        list.add(new ItemCardapio(1, "Feijoada", "Feijoada com carne suinas, calabresa e bacon", CategoriaCardapio.PRATOS_PRINCIPAIS, new BigDecimal("29.99"), new BigDecimal("25.99")));
        list.add(new ItemCardapio(2, "Frango", "Frando", CategoriaCardapio.PRATOS_PRINCIPAIS, new BigDecimal("14.99"), new BigDecimal("13.99")));
        list.add(new ItemCardapio(3, "Coca", "Refrigerante", CategoriaCardapio.BEBIDAS, new BigDecimal("5.99"), null));
        list.add(new ItemCardapio(4, "Guarana Anthatica", "Refrigerante", CategoriaCardapio.BEBIDAS, new BigDecimal("5.99"), null));
        list.add(new ItemCardapio(5, "Pepsi", "Refrigerante", CategoriaCardapio.BEBIDAS, new BigDecimal("5.99"), null));
        list.add(new ItemCardapio(6, "Fant - Laranja", "Refrigerante", CategoriaCardapio.BEBIDAS, new BigDecimal("4.99"), null));
        list.add(new ItemCardapio(7, "Fant - Uva", "Refrigerante", CategoriaCardapio.BEBIDAS, new BigDecimal("4.99"), null));
        return list;
    }

    @Override
    public List<ItemCardapio> listaDeCardapio() {
        return list;
    }

    @Override
    public Optional<ItemCardapio> getById(int num) {
        return this.list
                .stream()
                .filter(f -> f.id() == num)
                .findFirst();
    }

    @Override
    public boolean alterarPrecoItemCardapio(int id, BigDecimal preco) {
        ItemCardapio itemCardapio = this.getById(id).orElse(null);
        if (itemCardapio == null) return false;
        ItemCardapio novo = itemCardapio.alterarPreco(preco);
        List<ItemCardapio> list = this.listaDeCardapio();
        list.remove(itemCardapio);
        list.add(novo);
        return true;
    }

    @Override
    public int totalItemCardapio() {
        return 0;
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio itemCardapio) {
        if (list.contains(itemCardapio)) {
            return;
        }
        list.add(itemCardapio);
        System.out.println(list);
    }
}
