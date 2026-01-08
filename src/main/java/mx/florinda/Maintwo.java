package mx.florinda;


import mx.florinda.cardapio.CategoriaCardapio;
import mx.florinda.cardapio.DataBase;
import mx.florinda.cardapio.ItemCardapio;
import mx.florinda.cardapio.SQLDataBase;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Maintwo {
    private static final Logger LOGGER = Logger.getLogger(Maintwo.class.getName());

    public static void main(String[] args) {
        DataBase sqlDataBase = new SQLDataBase();
        sqlDataBase.adicionaItemCardapio(new ItemCardapio(0, "Acareje", "Comida da Bahia", CategoriaCardapio.PRATOS_PRINCIPAIS, BigDecimal.valueOf(35.00), BigDecimal.valueOf(30.99)));
        int total = sqlDataBase.totalItemCardapio();
        List<ItemCardapio> list = sqlDataBase.listaDeCardapio();
        list.forEach(System.out::println);
        LOGGER.fine(String.format("Total: %d", total));
    }
}