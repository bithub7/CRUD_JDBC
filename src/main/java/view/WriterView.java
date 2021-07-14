package view;

import model.Post;
import model.Writer;
import repository.PostRepository;
import repository.sql.SqlPostRepositoryImpl;
import service.WriterService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WriterView {

    private Scanner scan = new Scanner(System.in);
    private PostRepository postRepository = new SqlPostRepositoryImpl();
    private WriterService writerService = new WriterService();

    public void startWriterView(){
        while (true) {
            System.out.println("1. Добавить писателя\n" +
                    "2. Обновить писателя\n" +
                    "3. Получить писателя\n" +
                    "4. Получить всех писателя\n" +
                    "5. Удалить писателя\n" +
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

    public void saveView() {

        System.out.println("Введите имя писателя:");
        String writerFirstName = scan.nextLine();

        System.out.println("Введите фамилию писателся:");
        String writerLastName = scan.nextLine();

        List<Post> postList = new ArrayList<Post>();

        while (true) {
            System.out.println("Введите id поста писателя:\nДля завершения введите '0'");
            try {
                Long id = Long.parseLong(scan.nextLine());
                if (id == 0) {
                    break;
                }
                postList.add(postRepository.getById(id));
            } catch (Exception e) {
                System.out.println("Введены не коректные данные");
            }
        }
        Writer writer = writerService.save(writerFirstName, writerLastName,postList);
        System.out.println("Писатель сохранен.\n" +
                "id : " + writer.getId() + "\n" +
                "Имя : " + writer.getFirstName() + "\n" +
                "Фамилия : " + writer.getLastName() + "\n" );
    }

    public void updateView(){

        System.out.println("Введите id писателя:");
        Long writerId = Long.valueOf(scan.nextLine());

        System.out.println("Введите имя писателя:");
        String writerFirstName = scan.nextLine();

        System.out.println("Введите фамилию писателся:");
        String writerLastName = scan.nextLine();

        List<Post> postList = new ArrayList<Post>();

        while (true) {
            System.out.println("Введите id поста писателя:\nДля завершения введите '0'");
            try {
                Long postId = Long.parseLong(scan.nextLine());
                if (postId == 0) {
                    break;
                }
                postList.add(postRepository.getById(postId));
            } catch (Exception e) {
                System.out.println("Введены не коректные данные");
            }
        }
        Writer writer = writerService.update(writerId, writerFirstName, writerLastName, postList);

        System.out.println("Писатель обновлен.\n" +
                "id : " + writer.getId() + "\n" +
                "Имя : " + writer.getFirstName() + "\n" +
                "Фамилия : " + writer.getLastName()+ "\n" );
    }

    public void getByIdView() {
        System.out.println("Введите id писателя: ");
        Long id = Long.parseLong(scan.nextLine());
        Writer writer = writerService.getById(id);

        System.out.println("id : " + writer.getId() + "\n" +
                "Имя : " + writer.getFirstName() + "\n" +
                "Фамилия : " + writer.getLastName()+ "\n" );
    }

    public void getAllView(){
        List<Writer> writerList = writerService.getAll();
        System.out.println("Все писатели: ");
        for(Writer writer : writerList) {
            System.out.println("id : " + writer.getId() + "\n" +
                    "Имя : " + writer.getFirstName() + "\n" +
                    "Фамилия : " + writer.getLastName() + "\n");
        }
    }

    public void deleteByIdView() {
        System.out.println("Введите id писателя: ");
        Long id = Long.parseLong(scan.nextLine());
        writerService.deleteById(id);
        System.out.println("Писатель с id : " + id + " удален.\n");
    }
}
