package ru.geekbrains.lesson2;

public class MyArrayDataException extends NumberFormatException {

    public MyArrayDataException(int x, int y) {
        super(String.format("Ошибка преобразования в ячейке[%d][%d]", x, y));
    }
}
