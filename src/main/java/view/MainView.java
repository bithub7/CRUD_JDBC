package view;

import repository.sql.ConnectDB;
import java.sql.SQLException;
import java.util.Scanner;

public class MainView {

    public void start() {
        Scanner scan = new Scanner(System.in);
        LabelView labelView = new LabelView();
        PostView postView = new PostView();
        WriterView writerView = new WriterView();

        while (true) {
            System.out.println("1. Теги\n" +
                    "2. Посты\n" +
                    "3. Писатели\n" +
                    "0. Выйти\n");

            int num = -1;
            try {
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
                try {
                    if (ConnectDB.getConnection() != null) {
                        ConnectDB.getConnection().close();
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                System.exit(0);

            } else {
                System.out.println("Введено некорректное число");
            }
        }
    }
}