﻿public class s0408 {
    public static void main(String[] args) {
        compare(3);
        compare(6);
        compare(5);
    }

    public static void compare(int a) {
        if (a==5)
            System.out.println("Число равно 5");
        if (a>5)
            System.out.println("Число больше 5");
        if (a<5)
            System.out.println("Число меньше 5");
    }
}