package pt.ulusofona.aed.deisiRockstar2021;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        try {

            URL url = new URL("https://github.com/brunompc/aula-15-exceptions/archive/refs/heads/master.zip");

            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            String outputFile = "c:\\filmes\\" + "YOLO.zip";

            try (InputStream stream = con.getInputStream()) {
                Files.copy(stream, Paths.get(outputFile));
            }

        }
        catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }
        catch(FileAlreadyExistsException e) {
            System.out.println("O ficheiro de destino já existe");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
