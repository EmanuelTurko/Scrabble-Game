package test;

import java.io.*;

public class BookScrabbleHandler implements ClientHandler {

    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        reader = new BufferedReader(new InputStreamReader(inFromClient));
        writer = new PrintWriter(outToClient, true);

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                    boolean result;
                if (line.startsWith("Q")) {
                    String[] tokens = line.substring(2).split(",");
                    result = DictionaryManager.get().query(tokens);
                    } else {
                    String[] tokens = line.substring(2).split(",");
                    result = DictionaryManager.get().challenge(tokens);
                    }
                    writer.println(result);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error closing resources", e);
        }
    }
}
