package service;

import controller.PostController;
import model.Post;
import model.Writer;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WriterServiceText {

    private WriterService writerService = new WriterService();
    private WriterService mockWriterService = Mockito.mock(WriterService.class);
    private List<Post> postList = new ArrayList<>();


    @BeforeClass
    public void createPostList(){
        PostController postController = new PostController();
        postList.add(postController.getByIdPost(1l));
        postList.add(postController.getByIdPost(2l));
        postList.add(postController.getByIdPost(3l));

    }

    @Test
    public void shouldSaveWriter() {

        Writer writer = new Writer(1l, "testname", "testlastname", postList);

        Mockito.when(mockWriterService.save("testname", "testlastname", postList)).thenReturn(writer);
        assertEquals(writer.toString(),mockWriterService.save("testname", "testlastname", postList).toString());

    }

    @Test
    public void shouldUpdateWriter() {

        Writer writer = new Writer(1l, "testname", "testlastname", postList);

        Mockito.when(mockWriterService.update(1l, "testname", "testlastname", postList)).thenReturn(writer);
        assertEquals(writer.toString(),mockWriterService.update(1l,"testname", "testlastname", postList).toString());

    }

    @Test
    public void shouldGetWriter() {

        Writer writer = new Writer(1l, "testname", "testlastname", postList);

        Mockito.when(mockWriterService.getById(1l)).thenReturn(writer);
        assertEquals(writer.toString(),mockWriterService.getById(1l).toString());

    }

    @Test
    public void shouldGetAllWriter(){
        List<Writer> writerList = writerService.getAll();

        Mockito.when(mockWriterService.getAll()).thenReturn(writerList);
        assertEquals(writerList.toString(), mockWriterService.getAll().toString());

    }
}
