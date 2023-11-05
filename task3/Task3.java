import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Task3 {

    private static Map<Integer, String> parseValues(String jsonValues) {
        // хэшмапа для хранения пар id : value
        Map<Integer, String> dict = new HashMap<>();

        // ищем индекс первого вхождения подстроки id в нашей строке с json
        int pointer = jsonValues.indexOf("\"id\"");

        // ищем, пока находится id
        while (pointer != -1) {
            int nextColonIdx = jsonValues.indexOf(":", pointer);
            int nextCommaIdx = jsonValues.indexOf(",", nextColonIdx);

            // находим id
            int id = Integer.parseInt(jsonValues.substring(nextColonIdx + 1, nextCommaIdx).trim());

            int nextValueIdx = jsonValues.indexOf("\"value\"", nextCommaIdx);
            int nextColonAfterValue = jsonValues.indexOf(":", nextValueIdx);
            int quotMarks1 = jsonValues.indexOf("\"", nextColonAfterValue);
            int quotMarks2 = jsonValues.indexOf("\"", quotMarks1 + 1);

            // находим значение value
            String value = jsonValues.substring(quotMarks1 + 1, quotMarks2);

            // кладём в хэшмапу как ключ и value как значение
            dict.put(id, value);

            // ищем следующий индекс подстроки id
            pointer = jsonValues.indexOf("\"id\"", quotMarks2 + 1);
        }

        // возвращаем хэшмапу
        return dict;
    }

    private static String putValuesIntoJSON(String json, Map<Integer, String> map) {
        StringBuilder builder = new StringBuilder(json);

        int pointer = builder.indexOf("\"id\"");

        while (pointer != -1) {

            int nextColonIdx = builder.indexOf(":", pointer);
            int nextCommaIdx = builder.indexOf(",", nextColonIdx);

            // парсим id
            int id = Integer.parseInt(builder.substring(nextColonIdx + 1, nextCommaIdx).trim());

            // получаем значение, соответствующее id
            String value = map.get(id);
            if (value == null) {
                pointer = builder.indexOf("\"id\"", nextCommaIdx);
                continue;
            }

            int valueIdx = builder.indexOf("\"value\"", nextCommaIdx + 1);

            int nextColonIdx2 = builder.indexOf(":", valueIdx);

            // индекс открывающей кавычки, куда мы должны вставить значение
            int nextQuot = builder.indexOf("\"", nextColonIdx2);

            // вставляем его после открывающей кавычки
            builder.insert(nextQuot + 1, value);

            // ищем следующее вхождение подстроки id
            pointer = builder.indexOf("\"id\"", nextQuot + value.length() + 1);
        }

        // возвращаем строку с готовым json внутри
        return builder.toString();
    }

    public static void main(String[] args) {

        // tests.json
        Path pathToTests = Paths.get(args[0]);

        // values.json
        Path pathToValues = Paths.get(args[1]);

        String tests = null;
        String values = null;
        try {

            // пробуем прочитать файлы в виде строки и присвоить содержимого каждого файла своей строке
            tests = Files.lines(pathToTests).collect(Collectors.joining());
            values = Files.lines(pathToValues).collect(Collectors.joining());

        } catch (IOException e) {
            throw new RuntimeException("Файлы не найдены!");
        }

        // получаем хэшмапу id на value
        Map<Integer, String> idsToValues = parseValues(values);

        // получаем строку с json внутри
        String resultJSON = putValuesIntoJSON(tests, idsToValues);

        try {
            // пробуем записать файл
            Files.write(Paths.get(".report.json"),
                    resultJSON.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException("Не смог записать файл.");
        }

    }
}
