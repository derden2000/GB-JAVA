package ru.geekbrains.lesson2;

public class ExceptionApp {

    public static void main(String[] args) {

        String[][] arrStr = new String[4][4];
        for(int i=0; i< arrStr.length; i++) {
            for (int j = 0; j < arrStr[i].length; j++) {
                arrStr[i][j] = String.valueOf(( (int) (100*j*Math.random())));
                System.out.print(arrStr[i][j] + " ");
            }
            System.out.println("");
        }
        try {
            sumArr(arrStr);
        }
        catch (MyArraySizeException e) {
            System.out.println(e);
        }
        catch (MyArrayDataException e) {
            System.out.println(e);
        }

    }

    private static void sumArr(String[][] arrStr) throws MyArrayDataException, MyArraySizeException {
        try {
            String[][] arrNum = new String[4][4];
            for (int i = 0; i < arrStr.length; i++) {
                for (int j = 0; j < arrStr[i].length; j++) {
                    arrNum[i][j] = arrStr[i][j];
                }
            }
            //arrStr[3][2] = "fhg";
        }
        catch (IndexOutOfBoundsException e) {
            throw new MyArraySizeException();
        }
        int sum = 0;
            for (int i = 0; i < arrStr.length; i++) {
                for (int j = 0; j < arrStr[i].length; j++) {
                    try {
                        int a = Integer.parseInt(arrStr[i][j]);
                        sum+=a;
                    }
                    catch (NumberFormatException e) {
                        throw new MyArrayDataException(i,j);
                    }
                }
            }
            System.out.println(String.format("Сумма элементов массива равна %d",sum));
    }
}
