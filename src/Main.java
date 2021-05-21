import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Contains ideas from:
 *  https://stackoverflow.com/questions/16943102/java-download-zip-file-from-url
 *  https://stackoverflow.com/questions/9324933/what-is-a-good-java-library-to-zip-unzip-files
 */
public class Main {

    public static void main(String[] args) {

        try {
            URL url = new URL("https://github.com/brunompc/aula-15-exceptions/archive/refs/heads/master.zip");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            String baseFolder = "c:\\filmes\\";
            String outputFile = baseFolder + "YOLO_666.zip";
            try (InputStream stream = con.getInputStream()) {
                Files.copy(stream, Paths.get(outputFile));
            }

            // Unzip it to the same folder
            extractFolder(outputFile, baseFolder);

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

    private static void extractFolder(String zipFile,String extractFolder)
    {
        try
        {
            int BUFFER = 2048;
            File file = new File(zipFile);

            ZipFile zip = new ZipFile(file);
            String newPath = extractFolder;

            new File(newPath).mkdir();
            Enumeration zipFileEntries = zip.entries();

            // Process each entry
            while (zipFileEntries.hasMoreElements())
            {
                // grab a zip file entry
                ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
                String currentEntry = entry.getName();

                File destFile = new File(newPath, currentEntry);
                //destFile = new File(newPath, destFile.getName());
                File destinationParent = destFile.getParentFile();

                // create the parent directory structure if needed
                destinationParent.mkdirs();

                if (!entry.isDirectory())
                {
                    BufferedInputStream is = new BufferedInputStream(zip.getInputStream(entry));
                    int currentByte;
                    // establish buffer for writing file
                    byte data[] = new byte[BUFFER];

                    // write the current file to disk
                    FileOutputStream fos = new FileOutputStream(destFile);
                    BufferedOutputStream dest = new BufferedOutputStream(fos,
                            BUFFER);

                    // read and write until last byte is encountered
                    while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, currentByte);
                    }
                    dest.flush();
                    dest.close();
                    is.close();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

}
