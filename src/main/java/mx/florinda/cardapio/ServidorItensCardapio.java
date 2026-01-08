package mx.florinda.cardapio;

import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;

public class ServidorItensCardapio {

    public static void main(String[] args) throws Exception {

        InetSocketAddress inetSocketAddress = new InetSocketAddress(8081);
        HttpServer httpServer = HttpServer.create(inetSocketAddress, 0);

        httpServer.createContext("/itensCardapio.json", exchange -> {

            Path path = Path.of("itensCardapio.json");
            String json = Files.readString(path);

            byte[] bytes = json.getBytes();

            exchange.getResponseHeaders().add("Content-Type", "application/json; charset=utf-8");
            exchange.sendResponseHeaders(200, bytes.length);

            OutputStream responseBody = exchange.getResponseBody();
            responseBody.write(bytes);
            responseBody.close();
        });

        System.out.println("Servidor HTTP rodando em http://localhost:8081");
        httpServer.start();
    }
}
