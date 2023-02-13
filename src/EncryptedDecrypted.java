import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class EncryptedDecrypted {
    private final Scanner scanner = new Scanner(System.in);
    private final CaesarCipher caesarCipher = new CaesarCipher();

    public void encryptedDecrypted(boolean flag) throws IOException {
        System.out.println("Введите полный путь к файлу для его " + (flag ? "расшифровки" : "зашифровки"));
        String path = scanner.nextLine();
        System.out.println("Введите ключ");
        int key = Integer.parseInt(scanner.nextLine());
        Path newPath = PathHelper.buildFileName(path, flag ? "_encrypted" : "_decrypted");
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path));
             BufferedWriter writer = Files.newBufferedWriter(newPath)) {
            while (reader.ready()) {
                String string = reader.readLine();
                String encryptedDecrypted = flag ? caesarCipher.encrypt(string, key) : caesarCipher.decrypt(string, key);
                writer.write(encryptedDecrypted + System.lineSeparator());
            }
        }
        System.out.println("Cодержимое файла " + newPath.getFileName() + (flag? " зашифровано" : " расшифровано") +
                System.lineSeparator());

    }
}
