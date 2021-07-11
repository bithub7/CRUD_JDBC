package controller;


import model.Post;
import model.Writer;
import repository.WriterRepository;
import repository.sql.SqlWriterRepositoryImpl;

import java.sql.SQLException;
import java.util.List;

public class WriterController {

    private WriterRepository writerRepository = new SqlWriterRepositoryImpl();
    private Writer model = new Writer();
    private Writer writer;

    public void setWriterId(Long id){
        this.model.setId(id);
    }

    public void setWriterFirstName(String firstName){
        this.model.setFirstName(firstName);
    }

    public void setWriterLastName(String lastName){
        this.model.setLastName(lastName);
    }

    public void setWriterPosts(List<Post> posts){
        this.model.setPosts(posts);
    }

    public Writer saveWriter(){
        try {
            this.writer = writerRepository.save(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    public Writer updateWriter(){
        this.writer = writerRepository.update(model);
        return writer;
    }

    public Writer getByIdWriter(Long id){
        this.writer = writerRepository.getById(id);
        return writer;
    }

    public List<Writer> getAllWriter(){
        List<Writer> postList = writerRepository.getAll();
        return postList;
    }

    public void deleteByidWriter(Long id){
        writerRepository.deleteById(id);
    }
}
