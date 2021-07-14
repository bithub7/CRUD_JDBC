package view;

import repository.sql.ConnectDB;

import java.util.Scanner;

public class MainView {

    public void start() {
        LabelView labelView = new LabelView();
        PostView postView = new PostView();
        WriterView writerView = new WriterView();
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("1. Теги\n" +
                    "2. Посты\n" +
                    "3. Писатели\n" +
                    "0. Выйти\n");

            int num = -1;
            try{
                num = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                System.out.println("Введены не коректные данные");
            }
            if (num == 1) {
                labelView.startLabelView();
            } else if (num == 2) {
                postView.startsPostView();
            } else if (num == 3) {
                writerView.startWriterView();
            } else if (num == 0) {
                System.out.println("──────▄▀▄─────▄▀▄\n" +
                        "─────▄█░░▀▀▀▀▀░░█▄\n" +
                        "─▄▄──█░░░░░░░░░░░█──▄▄  bye\n" +
                        "█▄▄█─█░░▀░░┬░░▀░░█─█▄▄█\n");
                ConnectDB.close();
                scan.close();
                System.exit(0);

            } else {
                System.out.println("Введено некорректное число");
            }
        }
    }
}