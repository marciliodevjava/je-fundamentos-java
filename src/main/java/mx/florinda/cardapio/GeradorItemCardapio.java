package mx.florinda.cardapio;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeradorItemCardapio {
    public static void main(String[] args) throws IOException {
        DataBase dataBase = new InMeroryDataBase();
        List<ItemCardapio> listaDeCardapio = dataBase.listaDeCardapio();
        String json = new Gson().toJson(listaDeCardapio);

        System.out.println(json);

        Path path = Path.of("itensCardapio.json");
        Files.writeString(path, json);
    }
}
