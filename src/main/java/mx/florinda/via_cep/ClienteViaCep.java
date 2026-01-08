package mx.florinda.via_cep;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class ClienteViaCep {
    public static void main(String[] args) throws IOException {

        URL url = new URL("https://viacep.com.br/ws/01001000/json/");
        try (Scanner scanner = new Scanner(url.openStream(), "UTF-8");){
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
