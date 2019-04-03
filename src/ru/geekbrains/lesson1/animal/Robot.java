package ru.geekbrains.lesson1.animal;

import ru.geekbrains.lesson1.Participant;

/**
 * Робот не является животным, но может учавствовать в соревнованиях
 * так как реализует интерфейс {@link Participant}
 */
public class Robot implements Participant {

    private String name;
    private boolean isOnDistance;
    private int runDistance;
    private int jumpHeight;
    private int swimDist;

    public String getName() {
        return name;
    }

    public Robot(String name, int runDistance, int jumpHeight, int swimDist) {
        this.name = name;
        this.runDistance = runDistance;
        this.jumpHeight = jumpHeight;
        this.swimDist = swimDist;
        this.isOnDistance = true;
    }

    public Robot(String name) {
        this.name = name;
    }

    @Override
    public boolean isOnDistance() {
        return isOnDistance;
    }

    @Override
    public void run(int distance) {

        if (!isOnDistance) {
            return;
        }
        if (distance > runDistance) {
            isOnDistance = false;
            return;
        }
        System.out.println(String.format("Робот %s пробежал кросс длинной %d", getName(), distance));
    }

    @Override
    public void jump(int height) {

        if (!isOnDistance) {
            return;
        }
        if (height > jumpHeight) {
            isOnDistance = false;
            return;
        }
        System.out.println(String.format("Робот %s прыгнул на высоту %d", getName(), height));
    }

    @Override
    public void swim(int distance) {

        //isOnDistance = false;
        //System.out.println("Робот не умеет плавать");
        if (!isOnDistance) {
            return;
        }
        if (distance > swimDist) {
            isOnDistance = false;
            return;
        }
        System.out.println(String.format("Робот %s проплыл дистанцию %d метров", getName(), distance));
    }

    @Override
    public String getParticipantName() {
        return this.getName();
    }
}
