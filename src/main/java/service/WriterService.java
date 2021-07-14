package service;

import controller.WriterController;
import model.Post;
import model.Writer;

import java.util.List;

public class WriterService {

    private WriterController writerController = new WriterController();

    public Writer save(String writerFirstName, String writerLastName, List<Post> postList ){
        writerController.setWriterFirstName(writerFirstName);
        writerController.setWriterLastName(writerLastName);
        writerController.setWriterPosts(postList);
        return writerController.saveWriter();
    }

    public Writer update(Long id, String writerFirstName, String writerLastName, List<Post> postList){
        writerController.setWriterId(id);
        writerController.setWriterFirstName(writerFirstName);
        writerController.setWriterLastName(writerLastName);
        writerController.setWriterPosts(postList);

        return writerController.updateWriter();
    }

    public Writer getById(Long id){
        return writerController.getByIdWriter(id);
    }

    public List<Writer> getAll(){
        return writerController.getAllWriter();
    }

    public void deleteById(Long id){
        writerController.deleteByidWriter(id);
    }
}
