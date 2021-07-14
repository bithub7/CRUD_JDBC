package service;

import controller.LabelController;
import model.Label;
import model.Post;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class PostServiceTest {

    private PostService postService = new PostService();
    private PostService mockPostService = Mockito.mock(PostService.class);
    private Date date = new Date();
    private List<Label> labelList = new ArrayList<Label>();

    @BeforeClass
    public void createLabelList(){
        LabelController labelController = new LabelController();
        labelList.add(labelController.getByIdLabel(1l));
        labelList.add(labelController.getByIdLabel(2l));
        labelList.add(labelController.getByIdLabel(3l));
    }

    @Test
    public void shouldSavePost() {

        Post post = new Post(1l, "content post", date, null, labelList);

        Mockito.when(mockPostService.save( "content post", labelList, date)).thenReturn(post);
        assertEquals(post.toString(),mockPostService.save("content post", labelList, date).toString());
    }

    @Test
    public void shouldUpdatePost() {

        Post post = new Post(1l, "content post", null, date, labelList);

        Mockito.when(mockPostService.update(1l, "content post", labelList, date)).thenReturn(new Post(1l, "content post", null, date, labelList));
        assertEquals(post.toString(), mockPostService.update(1l, "content post", labelList, date).toString());
    }

    @Test
    public void shouldGetPost() {

        Post post = new Post(1l, "content post", date, date, labelList);

        Mockito.when(mockPostService.getById(1l)).thenReturn(new Post(1l, "content post", date, date, labelList));
        assertEquals(post.toString(), mockPostService.getById(1l).toString());
    }

    @Test
    public void shouldGetAllPost(){

        List<Post> postList = postService.getAll();

        Mockito.when(mockPostService.getAll()).thenReturn(postList);
        assertEquals(postList.toString(), mockPostService.getAll().toString());
    }
}