package ru.geekbrains.lesson3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        Set<String> namesSet = new HashSet<>();
        for (String str : names) {
            namesSet.add(str);
        }
        System.out.println(namesSet);


        Map<String, Integer> namesCount = new HashMap<>();
        for (String str : names) {
            int cnt = 0;
            for (String str1 : names) {
                if (str.equals(str1)) {
                    cnt++;
                }
            }
            namesCount.put(str, cnt);
        }
        System.out.println(namesCount);

        PhoneBook sample = new PhoneBook();
        sample.add("Иванов", "111");
        sample.add("Иванов", "222");
        sample.add("Иванов", "000");
        sample.add("Петров", "333");
        sample.add("Сидоров", "444");

        System.out.println(sample.get("Иванов"));
    }
}
