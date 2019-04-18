package ru.geekbrains.lesson5;

public class ThreadSample {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    float[] arr = new float[SIZE];
    float[] arr1 = new float[HALF];
    float[] arr2 = new float[HALF];

    public static void main(String[] args) {
        ThreadSample smpl = new ThreadSample();
        smpl.method1();
        smpl.method2();
    }

    private void method1() {
            for (int i = 0; i < SIZE; i++) {
                arr[i] = 1;
            }
            long a = System.currentTimeMillis();
            calcArr (arr, 0);
            long b = System.currentTimeMillis()-a;
            System.out.println("Время выполнения метода1: " + b);
    }

    private void method2() {
            for (int i = 0; i < SIZE; i++) {arr[i] = 1;}
            long a = System.currentTimeMillis();
            System.arraycopy(arr, 0, arr1, 0, HALF);
            System.arraycopy(arr, HALF, arr2, 0, HALF);
            Thread tr1 = new Thread(new Runnable()
            {
                public void run()
                {
                    calcArr(arr1, 0);
                }
            });
            tr1.start();
            Thread tr2 = new Thread(new Runnable()
            {
                public void run()
                {
                    calcArr(arr2, HALF);
                }
            });
            tr2.start();
            try {
                tr1.join();
                tr2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.arraycopy(arr1, 0, arr, 0,HALF);
            System.arraycopy(arr2, 0, arr, HALF,HALF);
            long b = System.currentTimeMillis() - a;
            System.out.println("Время выполнения метода2: " + b);
    }

    private void calcArr(float[] arr, int sdvig) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i+sdvig) / 5) * Math.cos(0.2f + (i+sdvig) / 5) * Math.cos(0.4f + (i+sdvig) / 2));
        }
    }
}
