package controller;

import model.Label;
import repository.LabelRepository;
import repository.sql.SqlLabelRepositoryImpl;
import view.LabelView;

import java.sql.SQLException;
import java.util.List;


public class LabelController {

    LabelRepository labelRepository =  new SqlLabelRepositoryImpl();
    Label model = new Label();
    Label label;

    public void setLabelName(String name){
        this.model.setName(name);
    }

    public void setLabelId(Long id){
        this.model.setId(id);
    }

    public Label saveLabel(){
        try {
            this.label = labelRepository.save(model);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return label;
    }

    public Label updateLabel(){
        this.label = labelRepository.update(model);
        return label;
    }

    public Label getByIdLabel(Long id){
        this.label = labelRepository.getById(id);
        return label;
    }

    public List<Label> getAllLebels(){
        List<Label> labelList = labelRepository.getAll();
        return labelList;
    }

    public void deleteByidLebal(Long id){
        labelRepository.deleteById(id);
    }
}
