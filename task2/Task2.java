import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Task2 {

    static class Circle {
        private final double x;
        private final double y;
        private final double radius;

        public Circle(double x, double y, double radius) {
            this.x = x;
            this.y = y;
            this.radius = radius;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getRadius() {
            return radius;
        }

        // 0 -> точка на окружности
        // 1 -> точка внутри
        // 2 -> точка снаружи
        public int testCoordinate (Point point) {

            // так как дробные числа нельзя сравнивать напрямую через == введём величину эпсилон
            // чтобы компенсировать ошибки округления
            final double epsilon = 0.000001D;

            // вычисляем расстояния на числовой прямой между x1 и x2, y1 и y2
            double deltaX = Math.abs(this.getX() - point.getX());
            double deltaY = Math.abs(this.getY() - point.getY());

            // гипотенуза и есть расстояние между точками
            double distanceBetweenPointAndRadius = Math.hypot(deltaX, deltaY);

            // вычисляем разность между радиусом и расстоянием между точками
            double difference = distanceBetweenPointAndRadius - this.getRadius();

            // если модуль разности меньше эпсилона, то точка на окружности
            if (Math.abs(difference) < epsilon) {return 0;}

            // если радиус оказался больше расстояния между точками, то точка внутри окружности
            if (difference < 0.0) {return 1;}

            // во всех остальных случаях точка вне окружности
            return 2;
        }
    }

    static class Point {
        private final double x;
        private final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
        public double getX() {
            return this.x;
        }
        public double getY() {
            return this.y;
        }
    }

    public static void main(String[] args) {

        // парсим из параметров ввода пути файлов
        Path circleCoordinates = Paths.get(args[0]);
        Path pointsCoordinates = Paths.get(args[1]);

        // готовим два списка для строкам, которые выкачаем из файлов
        // один для окружности, другой для мнодества точек
        List<String> circleCoords = null;
        List<String> points = null;

        try {
            // пытаемся выкачать содержимое файлов построчно и заполнить ими списки
            circleCoords = Files.readAllLines(circleCoordinates);
            points = Files.readAllLines(pointsCoordinates);
        } catch (IOException e) {
            throw new RuntimeException("Файлы не могут быть прочитаны.");
        }

        // из первого списка парсим координаты для окружности
        double circleX = Double.parseDouble(circleCoords.get(0).split("\\s")[0]);
        double circleY = Double.parseDouble(circleCoords.get(0).split("\\s")[1]);
        double circleRadius = Double.parseDouble(circleCoords.get(1));

        // создаём объект окружности
        Circle circle = new Circle(circleX, circleY, circleRadius);

        // проходим по списку со строками, получанными из файла с координатами строчек
        // парсим координаты, превращаем строки в даблы и кладём их в конструктор точек
        // получаем на каждую строку по объекту Point
        // на каждую точку вызываем у круга метод testCoordinate, который возвращает 0, 1 или 2
        // печатаем на экран с переводом строки
        points.stream()
                .map((String str) -> new Point(Double.parseDouble(str.split("\\s")[0]),
                        Double.parseDouble(str.split("\\s")[1])))
                .map(circle::testCoordinate)
                .forEach(System.out::println);
    }
}
