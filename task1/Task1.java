public class Task1 {

    public static void main(String[] args) {

        // создаём две интовых переменных
        // наша n из задания
        int range = -1;

        // наша m из задания
        int step  = -1;

        // проверяем, что параметры командной строки целые числа
        // если неправильные аргументы - выдаём сообщение об ошибке и закрываем программу
        try {
            range = Integer.parseInt(args[0]);
            step = Integer.parseInt(args[1]);

        } catch (NumberFormatException e) {
            // сообщение об ошибке в стандарный поток ошибок sys.err
            System.err.println("Неверные аргументы командной строки. Нужны два целых числа");

            // закрываем программу с кодом 0
            System.exit(0);
        }

        // создаём массив длинны range, т.е. если range 5 -> [1,2,3,4,5]
        int[] arr = new int[range];

        // наполняем массив целыми числами от 1 до range включительно
        for (int i = 0; i < range; i++) {
            arr[i] = i + 1;
        }

        // создаём объект StringBuilder, чтобы накапливать числа для ответа
        StringBuilder resultBuilder = new StringBuilder();

        // создаём переменную, которая будет указывать на яцейку массива
        // где начало интервала или шага
        // начало массива - ячейка с индексом 0
        int currentPosition = 0;

        do {
            // добавляем число, которое начало интервала в наш стрингбилдер
            resultBuilder.append(arr[currentPosition]);

            /*
             в этой строке, собственно, весь алгоритм
             пусть массив будет [1,2,3,4,5], интервал - 3,
             а звёздочка указывает на текущее начало интервала,
             т.е. переменную, которая лежит в массиве по индексу currentPosition
             [*1,2,3,4,5] -> [1,2,*3,4,5] -> [1,2,3,4,*5] -> [1,*2,3,4,5] -> [1,2,3,*4,5] -> [*1,2,3,4,5]
             мы берём наш индекс и прибавляем к нему длину интервала
             так как в джаве индексация начинается с нуля, нужно от результата отнять единицу
             дальше мы берём остаток от деления на длину массива, чтобы не выходить за его рамки
             */
            currentPosition = (currentPosition -1 + step) % range;
        }
        //как только начало нашего интервала оказалось в ячейке с индексом 0, мы выходим из цикла
        while (currentPosition != 0);

        // превращаем результат в строку
        String result = resultBuilder.toString();

        // печатаем результат
        System.out.println(result);
    }
}