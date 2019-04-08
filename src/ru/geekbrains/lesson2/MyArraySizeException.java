package ru.geekbrains.lesson2;

public class MyArraySizeException extends ArrayIndexOutOfBoundsException {
    public MyArraySizeException() {
        super("Размер массива превышен");
    }
}
