package mx.florinda;


import com.google.gson.Gson;
import mx.florinda.cardapio.CategoriaCardapio;
import mx.florinda.cardapio.InMeroryDataBase;
import mx.florinda.cardapio.ItemCardapio;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InMeroryDataBase dataBase = new InMeroryDataBase();
        List<ItemCardapio> list = dataBase.listaDeCardapio();

        list.stream().forEach(l -> {
            System.out.println(new Gson().toJson(l));
        });

        //saber quais categorias que eu tenho no cardapio



        Set<String> setTipos = list.stream()
                .map(ItemCardapio::categoria)
                .map(CategoriaCardapio::name).collect(Collectors.toSet());

        List<String> listTipos = list.stream()
                .map(ItemCardapio::categoria)
                .map(CategoriaCardapio::name)
                .collect(Collectors.toList());


        System.out.println(setTipos);
        System.out.println(listTipos);

        System.out.println(new Gson().toJson(dataBase.getById(1).orElse(null)));
    }
}