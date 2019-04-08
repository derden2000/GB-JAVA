package ru.geekbrains.lesson3;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        String[] names = new String[15];
        names[0] = "Вася";
        names[1] = "Саша";
        names[2] = "Петя";
        names[3] = "Сережа";
        names[4] = "Кирилл";
        names[5] = "Игорь";
        names[6] = "Вася";
        names[7] = "Дима";
        names[8] = "Петя";
        names[9] = "Денис";
        names[10] = "Петя";
        names[11] = "Кирилл";
        names[12] = "Вася";
        names[13] = "Саша";
        names[14] = "Петя";

        Map<String,Integer> dict = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            dict.put(names[i], 0);
        }
        for (Map.Entry<String, Integer> pair : dict.entrySet()) {
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(pair.getKey())) {
                    dict.put(names[i], pair.getValue()+1);
                }
            }
        }
        System.out.print("Список уникальных слов: ");
        for (Map.Entry<String, Integer> pair : dict.entrySet()) {
            System.out.print(pair.getKey() + ", ");
        }
        System.out.println();
        System.out.println("Количество повторений каждого слова:");
        for (Map.Entry<String, Integer> pair : dict.entrySet()) {
            System.out.println(pair.getKey() + " - " + pair.getValue());
        }

        PhoneBook simple = new PhoneBook();
        //ключом делаем номера телефонов. Именно они уникальны, а не Фамилии
        simple.add("111", "Иванов");
        simple.add("000", "Иванов");
        simple.add("222", "Иванов");
        simple.add("333", "Петров");
        simple.add("444", "Сидоров");

        simple.get("Иванов");
    }
}
