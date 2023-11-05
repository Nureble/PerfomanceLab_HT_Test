import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Task4 {

    public static void main(String[] args) {

        // парсим путь до файла из параметров командной строки
        Path pathToFile = Paths.get(args[0]);

        // создаём список лонгов
        List<Long> tmp = null;

        try {
            // пробуем прочесть файл построчно,
            // превращаем каждую строку в лонг
            // и кладём в список
            tmp = Files.readAllLines(pathToFile)
                    .stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            // если не смогли прочесть - выбрасываем исключение
            throw new RuntimeException("Неверный путь к файлу.");
        }

        // вычисляем среднее арифметическое
        double average = tmp.stream()
                .mapToLong(x -> x)
                .average()
                .orElseThrow(() -> new RuntimeException("Пустой список!"));

        //округляем до ближайшего целого числа
        long averageInt = Math.round(average);

        // пробегаем по списку чисел
        // считаем для каждого числа модуль от разности этого числа и среднего арифметического
        // суммируем разности
        long result = tmp.stream()
                .mapToLong(number -> Math.abs(averageInt - number))
                .sum();

        // печатаем на экран
        System.out.println(result);
    }
}
