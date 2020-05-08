import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class WeatherServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        String configString = readLineByLine("src/main/java/ForecastConfig");
        JsonParser jsonParser = new JsonParser();
        JsonArray jsonArray = (JsonArray) jsonParser.parse(configString);

        CountryManager cityMangaer = new CountryManager(jsonArray);

        ForecastManager forecastManager = new ForecastManager(cityMangaer);

        Runnable forecastNotifier = new ForecastNotifier(forecastManager, cityMangaer);
        Thread notifierThread = new Thread(forecastNotifier);
        notifierThread.start();




        Server server = ServerBuilder.forPort(12321).addService( new WeatherService(forecastManager, cityMangaer)).build();
        server.start();

        System.out.println("Server started at " + server.getPort());

        server.awaitTermination();
    }

    private static String readLineByLine(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
