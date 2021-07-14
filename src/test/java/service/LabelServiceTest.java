package service;

import model.Label;
import org.mockito.Mockito;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertEquals;

class LabelServiceTest {

    private LabelService labelService = new LabelService();
    private LabelService mockLabelService = Mockito.mock(LabelService.class);

    @Test
    public void shouldSaveLabel(){
        Label label = new Label(1L, "news");

        Mockito.when(mockLabelService.save( "news")).thenReturn(label);
        assertEquals(label.toString(), mockLabelService.save("news").toString());
    }

    @Test
    public void shouldUpdateLabel(){
        Label label = new Label(1l, "news");

        Mockito.when(mockLabelService.update(1l, "news")).thenReturn(new Label(1l, "news"));
        assertEquals(label.toString(), mockLabelService.update(1l, "news").toString());

    }

    @Test
    public void shouldGetLabel(){
        Label label = new Label(1l, "news");

        Mockito.when(mockLabelService.getById(1l)).thenReturn(new Label(1l, "news"));
        assertEquals(label.toString(), mockLabelService.getById(1l).toString());
    }

    @Test
    public void shouldGetAllLabel(){
        List<Label> labelList = labelService.getAll();

        Mockito.when(mockLabelService.getAll()).thenReturn(labelList);
        assertEquals(labelList.toString(), mockLabelService.getAll().toString());
    }
}