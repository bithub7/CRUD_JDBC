package controller;

import model.Label;
import model.Post;
import repository.PostRepository;
import repository.sql.SqlPostRepositoryImpl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class PostController {

    PostRepository postRepository = new SqlPostRepositoryImpl();
    Post model = new Post();
    Post post;

    public void setPostId(Long id){
        this.model.setId(id);
    }

    public void setPostContent(String content){
        this.model.setContent(content);
    }

    public void setPostLabels(List<Label> labelList){
        this.model.setLabels(labelList);
    }

    public void setCreated(Date date){
        this.model.setCreated(date);
    }

    public void setUpdated(Date date){
        this.model.setUpdated(date);
    }

    public Post savePost()  {
        try {
            this.post = postRepository.save(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    public Post updatePost(){
        this.post = postRepository.update(model);
        return post;
    }

    public Post getByIdPost(Long id){
        this.post = postRepository.getById(id);
        return post;
    }

    public List<Post> getAllLebels(){
        List<Post> postList = postRepository.getAll();
        return postList;
    }

    public void deleteByidPost(Long id){
        postRepository.deleteById(id);
    }
}