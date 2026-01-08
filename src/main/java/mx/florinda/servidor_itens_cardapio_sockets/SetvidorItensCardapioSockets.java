package mx.florinda.servidor_itens_cardapio_sockets;

import com.google.gson.Gson;
import mx.florinda.cardapio.ItemCardapio;
import mx.florinda.cardapio.SQLDataBase;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class SetvidorItensCardapioSockets {

    private static final SQLDataBase dataBase = new SQLDataBase();
    ;
    static Logger logger = Logger.getLogger(SetvidorItensCardapioSockets.class.getName());

    public static void main(String[] args) throws Exception {
        try (ExecutorService executorService = Executors.newFixedThreadPool(50)) {
            while (true) {
                try (ServerSocket serverSocket = new ServerSocket(8081)) {
                    logger.info("-------------------------- Servidor subiu --------------------------");

                    Socket cliente = serverSocket.accept();
                    executorService.execute(() -> tratarRequisicao(cliente));

                }
            }
        }
    }

    private static void tratarRequisicao(Socket cliente) {
        try (cliente) {
            InputStream clienteIS = cliente.getInputStream();
            StringBuilder requestBuilder = new StringBuilder();

            int data;
            do {
                data = clienteIS.read();
                requestBuilder.append((char) data);
            } while (clienteIS.available() > 0);
            String request = requestBuilder.toString();
            System.out.println(request);

            Thread.sleep(50);

            String[] requestChunks = request.split("\r\n\n");
            String requestLineAndHeadrs = requestChunks[0];
            String[] requestLineAndHeadrsChunks = requestLineAndHeadrs.split("\r\n\r\n");
            String requestLine = requestLineAndHeadrsChunks[0];
            String[] requestLineChunks = requestLine.split(" ");

            // metodo (GET/POST)
            String method = requestLineChunks[0];
            String requestURI = requestLineChunks[1];

            logger.info("--------------------------------------------------------------------");
            logger.finest(method);
            logger.finest(requestURI);

            OutputStream clienteOS = cliente.getOutputStream();
            PrintStream clienteOut = new PrintStream(clienteOS);

            if (method.equals("GET") && requestURI.equals("/itensCardapio.json")) {
                Path path = Path.of("itensCardapio.json");
                String json = Files.readString(path);

                clienteOut.println("HTTP/1.1 200 OK");
                clienteOut.println("Content-type: application/json charset=UTF-8\r\n\r\n");
                clienteOut.println(json);
                clienteOut.println("\r\n");
            } else if (method.equals("GET") && requestURI.equals("/itens-Cardapio")) {
                List<ItemCardapio> list = dataBase.listaDeCardapio();
                String midiaType = "application/json";

                byte[] body;
                Gson gson = new Gson();
                String json = gson.toJson(list);
                body = json.getBytes(StandardCharsets.UTF_8);

                clienteOS.write("HTTP/1.1 200 OK\r\n".getBytes(StandardCharsets.UTF_8));
                clienteOS.write(("Content-type: " + midiaType + "; charset=UTF-8\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                clienteOS.write(body);

            } else if (method.equals("GET") && requestURI.equals("/itens-Cardapio/total")) {
                int size = dataBase.totalItemCardapio();
                clienteOut.println("HTTP/1.1 200 OK");
                clienteOut.println("Content-type: application/json charset=UTF-8\r\n\r\n");
                clienteOut.println(size);
                clienteOut.println("\r\n");

            } else if (method.equals("POST") && requestURI.equals("/itens-cardapio")) {

                if (requestChunks.length == -1) {
                    clienteOut.println("HTTP/1.1 400 Bad Request");
                    return;
                }

                int indexBody = request.indexOf("{");
                String body = requestChunks[0].substring(indexBody);
                Gson gson = new Gson();
                ItemCardapio novoItemCardapio = gson.fromJson(body, ItemCardapio.class);
                dataBase.adicionaItemCardapio(novoItemCardapio);
                clienteOut.println("HTTP/1.1 201 OK");

            } else if (method.equals("GET") && requestURI.equals("/")) {
                List<ItemCardapio> list = dataBase.listaDeCardapio();
                StringBuilder htmlTodosItens = new StringBuilder();
                for (ItemCardapio item : list) {
                    String desconto = "";
                    String promocao = "";
                    String preco = String.valueOf(item.preco());
                    String precoReal;
                    if (item.precoComDesconto() != null) {
                        precoReal = String.valueOf(item.precoComDesconto());
                        desconto = String.valueOf("<s>R$ %s</s>".formatted(preco));
                    } else {
                        precoReal = preco;
                    }
                    if (item.precoComDesconto() != null) {
                        promocao = "<mark>Em promoção</mark>";
                    }
                    String htmlItem = """
                                <article>
                                    <kbd>%s</kbd>
                                    <h3>%s</h3>
                                    <p>%s</p>
                                    %s
                                    %s
                                    <s>%s</s>
                                </article>
                            """.formatted(item.categoria().name().replace("_", " "), item.nome(), item.descricao(), promocao, precoReal, desconto);
                    htmlTodosItens.append(htmlItem);
                }
                String html = """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>Florinda Eats - Cardápio</title>
                            <link
                                    rel="stylesheet"
                                    href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.1.1/css/pico.min.css"
                            >
                        </head>
                        <body>
                        <header class="container">
                            <hgroup>
                                <h1>Florinda Eats</h1>
                                <p>O sabor da Vila direto pra você</p>
                            </hgroup>
                        </header>
                        <main class="container">
                            <h2>Cardápio</h2>
                            %s
                        </main>
                        <footer class="container">
                            <p>
                                <small>
                                    <em>Preços de acordo com %s - %s</em>
                                </small>
                            </p>
                            <p>
                                <strong>Florinda Eats</strong> Todos os direitos reservados %s - %s
                            </p>
                        </footer>
                        </body>
                        </html>
                        """.formatted(htmlTodosItens.toString(), String.valueOf(LocalDate.now()).replace("-", "/"), formaterHours(), formaterMonth(), Year.now());

                clienteOut.println("HTTP/1.1 200 OK");
                clienteOut.println("Content-type: text/html charset=UTF-8\r\n\r\n");
                clienteOut.println(html);
                clienteOut.println("\r\r\n");
            } else {
                clienteOut.println("HTTP/1.1 404 Not Found");
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static Object formaterMonth() {
        return String.valueOf(LocalDate.now()
                .getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"))).toUpperCase();
    }

    private static Object formaterHours() {
        LocalTime agora = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return agora.format(formatter);
    }
}