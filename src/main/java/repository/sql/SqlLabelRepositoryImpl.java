package repository.sql;

import model.Label;
import repository.LabelRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlLabelRepositoryImpl implements LabelRepository {

    public SqlLabelRepositoryImpl() {
    }

    @Override
    public Label save(Label label) {

        String sql = "INSERT INTO labels VALUES(0, ?)";
        Long id = null;

        try {
            Connection connection = ConnectDB.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, label.getName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        label.setId(id);
        return label;
    }

    @Override
    public Label update(Label label) {

        String sql = "UPDATE labels SET name = ? WHERE id = ?;\n";
        Long id = null;

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, label.getName());
            preparedStatement.setLong(2, label.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return label;
    }

    @Override
    public Label getById(Long id) {

        String sql = "SELECT * FROM labels WHERE id = ?";
        Label label = new Label();

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            label.setId(resultSet.getLong("id"));
            label.setName(resultSet.getString("name"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return label;
    }

    @Override
    public List<Label> getAll() {

        List<Label> labelList = new ArrayList<Label>();
        String sql = "SELECT * FROM labels";
        try {
            Statement statement = ConnectDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()){
                Label label = new Label();
                label.setId(resultSet.getLong("id"));
                label.setName(resultSet.getString("name"));
                labelList.add(label);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return labelList;
    }

    @Override
    public void deleteById(Long id) {

        String sql = "DELETE FROM labels WHERE id = ?";

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
