package mx.florinda.cardapio;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class ClienteItemCardapio {
    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("http://localhost:8081/itensCardapio.json");
        try (Scanner scanner = new Scanner(url.openStream(), "UTF-8");) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
