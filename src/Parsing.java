import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Parsing {
    private final Scanner scanner = new Scanner(System.in);
    private final Map<Character, Integer> mapEncrypted = new HashMap<>();
    private final Map<Character, Integer> mapStatistic = new HashMap<>();
    private final Map<Character, Character> mapDecrypted = new HashMap<>();

    public void parse() throws IOException {
        System.out.println("Введите путь к файлу для разшифровки");
        String pathEncrypted = scanner.nextLine();

        System.out.println("Введите путь к файлу для набора статистики");
        String pathStatistic = scanner.nextLine();

        Path parsing = PathHelper.buildFileName(pathEncrypted, "_parsing");

        List<Map.Entry<Character, Integer>> listEncrypted = mapToList(fillMapValue(mapEncrypted, pathEncrypted));
        List<Map.Entry<Character, Integer>> listStatistic = mapToList(fillMapValue(mapStatistic, pathStatistic));

        if(listEncrypted.size() <= listStatistic.size()){
            for (int i = 0; i < listEncrypted.size(); i++) {
                mapDecrypted.put(listEncrypted.get(i).getKey(), listStatistic.get(i).getKey());
            }
        }else {
            System.out.println("Размер файла статистики должен быть больше зашифрованного файла" + System.lineSeparator());
        }
        try(BufferedReader reader = Files.newBufferedReader(Paths.get(pathEncrypted));
            BufferedWriter writer = Files.newBufferedWriter(parsing)){
            while (reader.ready()){
                StringBuilder builder = new StringBuilder();
                String string = reader.readLine();
                for (char encryptedChar : string.toCharArray()) {
                    Character decryptedChar = mapDecrypted.get(encryptedChar);
                    builder.append(decryptedChar);
                }
                writer.write(builder + System.lineSeparator());
            }
        }
        System.out.println("Содержимое было расшифровано" + System.lineSeparator());
    }

    private Map<Character, Integer> fillMapValue(Map<Character, Integer> map, String path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path))) {
            StringBuilder builder = new StringBuilder();
            while (reader.ready()) {
                String string = reader.readLine();
                builder.append(string);
            }
            for (char aChar : builder.toString().toCharArray()) {
                if (!(map.containsKey(aChar))) {
                    map.put(aChar, 1);
                } else {
                    map.put(aChar, map.get(aChar) + 1);
                }
            }
        }
        return map;
    }
    private List<Map.Entry<Character, Integer>> mapToList(Map<Character, Integer> map){
        Set<Map.Entry<Character, Integer>> set = map.entrySet();
        List<Map.Entry<Character, Integer>> list = new ArrayList<>(set);
        Comparator<Map.Entry<Character, Integer>> comparator = (o1, o2) -> o2.getValue() - o1.getValue();
        list.sort(comparator);
        return list;
    }

}
