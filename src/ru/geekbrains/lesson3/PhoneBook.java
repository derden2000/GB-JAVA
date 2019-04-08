package ru.geekbrains.lesson3;

import java.util.HashMap;
import java.util.Map;

public class PhoneBook {
    Map<String, String> phoneBook;

    //можно было бы этот класс сделать Синглтоном, но я не стал. Пусть будет несколько телефонных справочников
    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    public void add (String phone, String surname) {
        phoneBook.put(phone, surname);
    }

    public void get(String s) {
        for (Map.Entry<String, String> pair : phoneBook.entrySet()) {
            if (s.equals(pair.getValue())) {
                System.out.print(pair.getKey() + " ");
            }
        }

    }


}
