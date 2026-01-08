package mx.florinda.via_cep;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ClienteViaCepTwo {
    public static void main(String[] args) throws Exception {

        try (HttpClient httpClient = HttpClient.newHttpClient()){
            HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("https://viacep.com.br/ws/01001000/json/")).build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            int status = httpResponse.statusCode();
            String body = httpResponse.body();
            System.out.println(status);
            System.out.println(body);
        }
    }
}
