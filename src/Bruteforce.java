import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bruteforce {
    private final CaesarCipher caesarCipher = new CaesarCipher();
    private final Scanner scanner = new Scanner(System.in);

    public void bruteforce() throws IOException {
        System.out.println("Введите полный путь к файлу");
        String path = scanner.nextLine();
        Path newPath = PathHelper.buildFileName(path, "_bruteforce");
        try (BufferedReader reader = Files.newBufferedReader(Path.of(path));
             BufferedWriter writer = Files.newBufferedWriter(newPath)){

            StringBuilder builder = new StringBuilder();
            List<String> list = new ArrayList<>();
            while (reader.ready()){
                String string = reader.readLine();
                builder.append(string).append(System.lineSeparator());
                list.add(string);

            }
            for (int i = 0; i < caesarCipher.alphabetLength(); i++) {
                String decrypt = caesarCipher.decrypt(builder.toString(), i);
                if(isValidateText(decrypt)){
                    for (String string : list) {
                        String encrypt = caesarCipher.decrypt(string, i);
                        writer.write(encrypt + System.lineSeparator());
                    }

                    System.out.println("Содержимое было расшифровано, ключ расшифровки К = " + i);
                    break;
                }
            }
        }
    }

    private boolean isValidateText(String text){
        boolean isValidate = false;
        for (String word : text.split(" ")){
            if(word.length() > 28){
                return false;
            }
        }
        if(text.contains(". ") || text.contains(", ") || text.contains("! ") || text.contains("? ")){
            isValidate = true;
        }
        while (isValidate){
            System.out.println(text.substring(0, Math.min(text.length(), 1000)));
            System.out.println("Текст корректно расшифрован? (Y/N)");
            String answer = scanner.nextLine();
            if(answer.equalsIgnoreCase("Y")){
                return true;
            }else if(answer.equalsIgnoreCase("N")){
                isValidate = false;
            }else {
                System.out.println("Нужно выбрать только Y/N");
            }
        }

        return false;
    }


}
