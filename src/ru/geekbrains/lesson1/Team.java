package ru.geekbrains.lesson1;

/**
 * Класс - комманда участников соревнований
 */
public class Team {

    private Participant[] participants;

    // здесь используется конструктор с переменным числом параметров
    public Team(Participant... participants) {
        // внутри метода переменное число параметров интерпретируется как массив
        this.participants = participants;
    }

    public Participant[] getParticipants() {
        return participants;
    }

    public void printfinished (Team team) {
        for (Participant participant : participants)
            if (participant.isOnDistance())
                System.out.println(participant.getParticipantName());
    }

    public void printall (Team team) {
        for (Participant participant : participants)
            System.out.println(participant);
    }

    public void showResult (Team team) {
        System.out.println("Дистанцию закончили:");
        printfinished(team);
        System.out.println();
        System.out.println("Дистанцию не закончили:");
        for (Participant participant : participants)
            if (!participant.isOnDistance())
                System.out.println(participant.getParticipantName());
    }
}
