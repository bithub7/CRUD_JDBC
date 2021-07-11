package view;

import controller.LabelController;
import model.Label;

import java.util.List;
import java.util.Scanner;

public class LabelView {

    LabelController labelController = new LabelController();
    Scanner scan = new Scanner(System.in);

    public void startLabelView(){
        while (true) {
            System.out.println("1. Добавить тег\n" +
                    "2. Обновить тег\n" +
                    "3. Получить тег\n" +
                    "4. Получить все теги\n" +
                    "5. Удалить тег\n" +
                    "0. Главное меню\n");

            int num = -1;

            try{
                num = Integer.parseInt(scan.nextLine());
            }catch (Exception e){
                System.out.println("Введены не коректные данные");
            }
            if(num == 1){
                saveView();
            }else if(num == 2){
                updateView();
            }else if(num == 3){
                getByIdView();
            }else if(num == 4){
                getAllView();
            }else if(num == 5){
                deleteByIdView();
            }else if(num == 0){
                break;
            }else{
                System.out.println("Введено не коректное число");
            }
        }
    }

    public void saveView(){
        System.out.println("Введите название тега:");
        String labelName = scan.nextLine();
        labelController.setLabelName(labelName);
        Label label = labelController.saveLabel();
        System.out.println("Тег создан. name : " + label.getName() + ", id : " + label.getId()+ "\n" );
    }

    public void updateView(){
        System.out.println("Введите id тега для обновления:");
        Long id = Long.parseLong(scan.nextLine());
        labelController.setLabelId(id);
        System.out.println("Введите название тега:");
        String labelName = scan.nextLine();
        labelController.setLabelName(labelName);
        Label label = labelController.updateLabel();
        System.out.println("Тег обновлен. name : " + label.getName() + ", id : " + label.getId()+ "\n" );
    }

    public void getByIdView(){
        System.out.println("Введите id тега:");
        Long id = Long.parseLong(scan.nextLine());
        Label label = labelController.getByIdLabel(id);
        System.out.println(label.getName()+ "\n" );
    }

    public void getAllView(){
        List<Label> labelList = labelController.getAllLebels();
        for(Label label : labelList){
            System.out.println("name : " + label.getName() + ", id : " + label.getId());
        }
        System.out.println();
    }

    public void deleteByIdView(){
        System.out.println("Введите id тега:");
        Long id = Long.parseLong(scan.nextLine());
        labelController.deleteByidLebal(id);
        System.out.println("Тег с id " + id + " удален." + "\n" );
    }
}
