package com.kata;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Scanner scanner = new Scanner(System.in);

        //Создаем строку с примером
        System.out.println("Введите пример: " + '\n');
        String numsToCount = scanner.nextLine();

        //Вставляем ее в калькулятор
        System.out.printf("Ответ: %s", calc(numsToCount));
    }
    public static String calc(String input) {

        int result;
        RomeNumber romeNumber = new RomeNumber();

        //Создаем массив, куда вставляем элементы примера (число 1, знак, число 2)
        String[] toCount = input.split(" ");

        //Проверка на соответсвие примера условию задания (2 операнда, 1 оператор)
        if (toCount.length < 3) {
            throw new RuntimeException("Строка не является математической операцией");
        } else if (toCount.length > 3) {
            throw new RuntimeException("Формат математической операции не удовлетворяет заданию " +
                    "- два операнда и один оператор");
        }

        String number1 = toCount[0];
        String sign = toCount[1];
        String number2 = toCount[2];


        // Проверка на соответствие операндов числам и одинаковым системам исчисления

        boolean isRome = true;
        if (!romeNumber.rA.containsKey(number1) || !romeNumber.rA.containsKey(number2)) {
            isRome = false;
            try {
                Integer.parseInt(number1);
                Integer.parseInt(number2);
            } catch (NumberFormatException e1) {
                throw new RuntimeException("Есть операнд(ы) не числового формата или " +
                        "оба операнда принадлежат разным числовым системам исчисления");
            }
        }

        // Конвертация в числа
        int intNumber1;
        int intNumber2;

        if (isRome) {
            intNumber1 = romeNumber.rA.get(number1);
            intNumber2 = romeNumber.rA.get(number2);
        } else {
            intNumber1 = Integer.parseInt(number1);
            intNumber2 = Integer.parseInt(number2);
        }

        // Проверка на принадлежность заданному диапазону
        int limit = 10;
        if (intNumber1>limit || intNumber2>limit){
            throw new RuntimeException("Операнд(ы) не принадлежат заданному диапазону 0-10");
        }


        //Проверка на соответсвие примера условию задания (оператор)

        if(!sign.equals("+") && !sign.equals("-") && !sign.equals("/") && !sign.equals("*")){

            throw new RuntimeException("Оператор не соответствует указанным (+, -, /, *)");
        }


        //Выполняем вычисления

        switch (sign){
            case "+":
                result = intNumber1 + intNumber2;
                break;
            case "-":
                result = intNumber1 - intNumber2;
                break;
            case "*":
                result = intNumber1 * intNumber2;
                break;
            case "/":
                //Условие деления на ноль - исключение
                try{
                    result = intNumber1 / intNumber2;
                } catch (ArithmeticException e){
                    System.out.println("Делить на ноль нельзя!");
                    return "его нет :)";
                }
                break;
            default:
                //исключение
                throw new RuntimeException("Что-то пошло не так, упс...");
        }

        //Преобразуем результат
        int dozens;
        int ending;
        String dozen = "";
        String end = "";
        String answer = "";

        if (isRome) {
            if (result > 10) {
                ending = result % 10;
                dozens = result - ending;
                for (String key :
                        romeNumber.rA.keySet()) {
                    if (romeNumber.rA.get(key) == dozens) {
                        dozen = key;
                    } else if (romeNumber.rA.get(key) == ending) {
                        end = key;
                    }
                }
                answer = dozen + end;
            } else if (result < 1) {
                throw new RuntimeException("Ответ меньше 1. " +
                        "Результатом работы калькулятора с римскими числами могут быть только положительные числа");
            } else {
                for (String key :
                        romeNumber.rA.keySet()) {
                    if (romeNumber.rA.get(key) == result) {
                        answer = key;
                    }
                }
            }
        } else {
            answer = String.valueOf(result);
        }
        return answer;
    }
}
