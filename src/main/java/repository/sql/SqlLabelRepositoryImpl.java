package repository.sql;

import model.Label;
import repository.LabelRepository;
import repository.sql.sql_teams.LabelSQLTeams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlLabelRepositoryImpl implements LabelRepository {

    public SqlLabelRepositoryImpl() {}

    @Override
    public Label save(Label label) {
        Long id = null;
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(LabelSQLTeams.ADD_LABEL.getTeam())){
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
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(LabelSQLTeams.UPDATE_LABEL.getTeam())){
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

        Label label = new Label();
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(LabelSQLTeams.GET_LABEL_BY_ID.getTeam())){
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
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(LabelSQLTeams.GET_ALL_LABELS.getTeam())){
            ResultSet resultSet = preparedStatement.executeQuery();

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
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(LabelSQLTeams.DELETE_LABEL_DY_ID.getTeam())){
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}