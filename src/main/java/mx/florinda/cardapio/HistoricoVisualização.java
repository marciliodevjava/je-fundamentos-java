package mx.florinda.cardapio;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HistoricoVisualização {
    private final DataBase dataBase;
    private final Map<ItemCardapio, LocalDateTime> visualizacao = new HashMap<>();

    public HistoricoVisualização(InMeroryDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void registrarVisualizacao(int id) {
        ItemCardapio itemCardapio = dataBase.getById(id).orElse(null);
        if (this.verify(itemCardapio)) this.visualizacao.put(itemCardapio, LocalDateTime.now());
    }

    private boolean verify(ItemCardapio itemCardapio) {
        if (itemCardapio == null) return false;
        return true;
    }

    public Map<ItemCardapio, LocalDateTime> getVisualizacao() {
        return visualizacao;
    }

    public void removerItemCardapio(int i) {
        if (this.visualizacao.entrySet().removeIf(e -> e.getKey().id() == i)) {
            System.out.println(String.format("Item removido com sucesso %s", i));
        } else {
            String.format("Item %s não foi removido", i);
        }

    }
}
