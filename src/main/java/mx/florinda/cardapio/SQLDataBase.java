package mx.florinda.cardapio;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SQLDataBase implements DataBase {
    private final String PASSWORD = "1234567890";
    private final String USER_NAME = "root";
    private final String HOST = "jdbc:mysql://localhost:3306/cardapio";

    @Override
    public List<ItemCardapio> add() {
        return List.of();
    }

    @Override
    public List<ItemCardapio> listaDeCardapio() {
        String sql = "select id, nome, descricao, categoria, preco, preco_promocional from cardapio.item_cardapio ";
        List<ItemCardapio> list = new ArrayList<>();
        try (Connection root = DriverManager.getConnection(HOST, USER_NAME, PASSWORD);
             PreparedStatement ps = root.prepareStatement(sql);
             ResultSet querry = ps.executeQuery()) {

            while (querry.next()) {
                int id = querry.getInt("id");
                String nome = querry.getString("nome");
                String descricao = querry.getString("descricao");
                CategoriaCardapio categoriaCardapio = CategoriaCardapio.valueOf(querry.getString("categoria"));
                BigDecimal preco = querry.getBigDecimal("preco");
                BigDecimal precoPromocional = querry.getBigDecimal("preco_promocional");
                list.add(new ItemCardapio(id, nome, descricao, categoriaCardapio, preco, precoPromocional));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return list;
    }

    @Override
    public int totalItemCardapio() {
        String sql = "select count(*) total from cardapio.item_cardapio ";
        int total = 0;
        try (Connection root = DriverManager.getConnection(HOST, USER_NAME, PASSWORD);
             PreparedStatement ps = root.prepareStatement(sql);
             ResultSet querry = ps.executeQuery()) {

            if (querry.next()) {
                total = querry.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return total;
    }

    @Override
    public void adicionaItemCardapio(ItemCardapio item) {
        String sql = """
                    INSERT INTO cardapio.item_cardapio
                    (nome, descricao, categoria, preco, preco_promocional)
                    VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DriverManager.getConnection(HOST, USER_NAME, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.nome());
            ps.setString(2, item.descricao());
            ps.setString(3, item.categoria().name());
            ps.setBigDecimal(4, item.preco());
            ps.setBigDecimal(5, item.precoComDesconto());

            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean alterarPrecoItemCardapio(int id, BigDecimal preco) {
        return true;
    }

    @Override
    public Optional<ItemCardapio> getById(int num) {
        return Optional.empty();
    }

}
