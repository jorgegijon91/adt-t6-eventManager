import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Venue;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String BASE_URL = "http://localhost:8080/api" ;
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {

        //1. Obtener todos los venues
        ArrayList<Venue> venues = getAllVenues();
        for (Venue venue : venues) {
            System.out.println(venue);
        }
        //2. Obtener un venue por id
        System.out.println("==== Venue con ID 1 ====");
        Venue venueObtenido = getVenueById(1L);
        System.out.println(venueObtenido);
        //3. Enviar un venue
        Venue venue = new Venue("Teatro Arango", "Gijon", null);
        Venue storedVenue = postVenue(venue);

        System.out.println("Venue creado: " + storedVenue);
    }

    private static Venue getVenueById(long id) {

        ArrayList<Venue> resultado = new ArrayList<>();
        //Url de la peticion
        String url = BASE_URL + "/venues/{id}";

        //Contactar con nuestra api con HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url.replace("{id}", String.valueOf(id))))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<Venue>() {
                });
            } else {
                System.out.println("Error al obtener el venue");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado.get(0);
    }

    //Array de venue para obtenerlos todos
    public static ArrayList<Venue> getAllVenues() {
        ArrayList<Venue> resultado = new ArrayList<>();
        //Url de la peticion
        String url = BASE_URL + "/venues" ;
        //Contactar con nuestra api con HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                resultado = objectMapper.readValue(response.body(), new TypeReference<ArrayList<Venue>>() {
                });
                System.out.println("Resultado obtenido");
            } else {
                System.out.println("Error al obtener los venues");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static Venue postVenue(Venue venue) {
        //Url de la peticion
        String url = BASE_URL + "/venues" ;
        Venue result = null;
        //Cuerpo de la solicitud
        try {
            String requestBody = objectMapper.writeValueAsString(venue);
            //Construir nuestra solicitud
            HttpRequest request = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                 result = objectMapper.readValue(response.body(),
                        Venue.class);
                System.out.println("Resultado obtenido");
            } else {
                System.out.println("Error al obtener los venues");
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
