package ru.geekbrains.lesson3;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PhoneBook {
    Map<String, Set<String>> phoneBook;

    public PhoneBook() {
        this.phoneBook = new HashMap<>();
    }

    public void add (String surname, String phone) {
        Set<String> phones = phoneBook.get(surname);
        if (phones == null) {
            phones = new HashSet<>();
            phoneBook.put(surname, phones);
        }
        phones.add(phone);
    }

    public Set<String> get(String surname) {
        return phoneBook.get(surname);
        }

    }
