package service;

import controller.LabelController;
import model.Label;

import java.util.List;

public class LabelService {

    private LabelController labelController = new LabelController();

    public Label save(String name){
        labelController.setLabelName(name);
        return labelController.saveLabel();
    }

    public Label update(Long id, String name) {
        labelController.setLabelId(id);
        labelController.setLabelName(name);
        return labelController.updateLabel();
    }

    public Label getById(Long id) {
        return labelController.getByIdLabel(id);
    }

    public List<Label> getAll() {
        return labelController.getAllLebels();
    }

    public void deleteById(Long id) {
        labelController.deleteByidLebal(id);
    }

}