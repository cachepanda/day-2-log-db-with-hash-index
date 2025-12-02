import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Database {
    public final String filePath;
    private Map<String, Long> hashIndex;

    public Database(String s) {
        this.filePath = s;
        this.hashIndex = new HashMap<>();
        populateHashIndex();
    }

    public void populateHashIndex() {
        try (RandomAccessFile file = new RandomAccessFile(this.filePath, "r")) {
            String row;
            while (true) {
                Long pointer = file.getFilePointer();
                row = file.readLine();
                if (row == null){
                    break;
                }

                String[] record = row.split(",", 2);
                hashIndex.put(record[0], pointer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void set(String key, String value) {
        try (RandomAccessFile file = new RandomAccessFile(this.filePath, "rw")) {
            file.seek(file.length());
            String keyVal = key + "," + value + "\n";
            this.hashIndex.put(key, file.getFilePointer());
            file.write(keyVal.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<String> get(String key) {
        try (RandomAccessFile file = new RandomAccessFile(this.filePath, "r")) {
            if (this.hashIndex.get(key) == null) {
                return Optional.empty();
            }

            file.seek(this.hashIndex.get(key));
            String row = file.readLine();
            String[] record = row.split(",", 2);
            return Optional.of(record[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
