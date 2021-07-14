package view;

import model.Label;
import model.Post;
import repository.LabelRepository;
import repository.sql.SqlLabelRepositoryImpl;
import service.PostService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class PostView {

    PostService postService = new PostService();
    LabelRepository labelRepository = new SqlLabelRepositoryImpl();
    Scanner scan = new Scanner(System.in);

    public void startsPostView(){
        while (true) {
            System.out.println( "1. Добавить пост\n"+
                    "2. Обновить пост\n" +
                    "3. Получить пост\n" +
                    "4. Получить все пост\n" +
                    "5. Удалить пост\n" +
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
        System.out.println("Введите контент поста:");
        String postContent = scan.nextLine();
        List<Label> labelList = new ArrayList<Label>();

        while (true){
            System.out.println("Введите id тега:\nДля завершения введите '0'");
            try{
                Long id = Long.parseLong(scan.nextLine());
                if(id == 0){
                    break;
                }
                labelList.add(labelRepository.getById(id));
            }catch (Exception e){
                System.out.println("Введены не коректные данные");
            }
        }
        Post post = postService.save(postContent, labelList, new Date());
        System.out.println("Пост сохранен.\n" + post);
    }

    public void updateView(){
        System.out.println("Введите id поста для обновление:");
        Long id = Long.parseLong(scan.nextLine());

        System.out.println("Введите контент поста:");
        String postContent = scan.nextLine();

        List<Label> labelList = new ArrayList<Label>();
        while (true){
            System.out.println("Введите id тега:\nДля завершения введите '0'");
            try{
                Long labelId = Long.parseLong(scan.nextLine());
                if(labelId == 0 ){
                    break;
                }
                labelList.add(labelRepository.getById(labelId));
            }catch (Exception e){
                System.out.println("Введены не коректные данные");
            }
        }
        Post post = postService.update(id, postContent, labelList, new Date());
        System.out.println("Пост обновлен.\n" + post );
    }

    public void getByIdView() {
        System.out.println("Введите id поста:");
        Long id = Long.parseLong(scan.nextLine());
        Post post = postService.getById(id);
        System.out.println(post);
    }

    public void getAllView(){
        List<Post> postList = postService.getAll();
        System.out.println("Все посты: ");
        for(Post post : postList) {
            System.out.println(post);
        }
    }

    public void deleteByIdView() {
        System.out.println("Введите id поста:");
        Long id = Long.parseLong(scan.nextLine());
        postService.deleteById(id);
        System.out.println("Пост с id : " + id + " удален.");
    }
}