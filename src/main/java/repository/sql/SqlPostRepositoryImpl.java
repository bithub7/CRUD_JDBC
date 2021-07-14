package repository.sql;

import model.Label;
import model.Post;
import repository.PostRepository;
import repository.sql.sql_teams.PostSQLTeams;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlPostRepositoryImpl implements PostRepository {

    @Override
    public Post save(Post post) {

        Long id = null;

        try(PreparedStatement preparedStatementAddPost = ConnectDB.getPreparedStatement(PostSQLTeams.ADD_POST.getTeam())){
            preparedStatementAddPost.setString(1, post.getContent());
            preparedStatementAddPost.setDate(2, new java.sql.Date(post.getCreated().getTime()));
            preparedStatementAddPost.executeUpdate();
            ResultSet resultSet = preparedStatementAddPost.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatementAddLabels = ConnectDB.getPreparedStatement(PostSQLTeams.ADD_POST_LABEL.getTeam())){
            for(Label label : post.getLabels()){
                preparedStatementAddLabels.setLong(1, id);
                preparedStatementAddLabels.setLong(2,label.getId());
                preparedStatementAddLabels.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        post.setId(id);

        return post;
    }

    @Override
    public Post update(Post post) {
        try(PreparedStatement preparedStatementUpdate = ConnectDB.getPreparedStatement(PostSQLTeams.UPDATE_POST.getTeam())) {
            preparedStatementUpdate.setString(1, post.getContent());
            preparedStatementUpdate.setDate(2, new java.sql.Date(post.getUpdated().getTime()));
            preparedStatementUpdate.setLong(3, post.getId());
            preparedStatementUpdate.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatementDelete = ConnectDB.getPreparedStatement(PostSQLTeams.DELETE_POST_LABELS.getTeam());) {
            preparedStatementDelete.setLong(1, post.getId());
            preparedStatementDelete.executeUpdate();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(PostSQLTeams.ADD_POST_LABEL.getTeam())) {
            for (Label label : post.getLabels()) {
                preparedStatement.setLong(1, post.getId());
                preparedStatement.setLong(2, label.getId());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post getById(Long id) {

        Post post = new Post();
        ResultSet resultSet = null;

        try(PreparedStatement preparedStatementPost = ConnectDB.getPreparedStatement(PostSQLTeams.GET_POSTS_BY_ID.getTeam())){
            preparedStatementPost.setLong(1, id);
            resultSet = preparedStatementPost.executeQuery();
            resultSet.next();
            post.setId(id);
            post.setContent(resultSet.getString("content"));
            post.setCreated(resultSet.getDate("created"));
            post.setUpdated(resultSet.getDate("updated"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatementLabel = ConnectDB.getPreparedStatement(PostSQLTeams.GET_ID_LABELS.getTeam())) {
            preparedStatementLabel.setLong(1, id);
            resultSet = preparedStatementLabel.executeQuery();
            List<Label> labelList = getLabelList(resultSet);
            post.setLabels(labelList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public List<Post> getAll() {

        List<Post> postList = new ArrayList<Post>();

        try(PreparedStatement preparedStatementPost = ConnectDB.getPreparedStatement(PostSQLTeams.GET_ALL_POSTS.getTeam())){
            ResultSet resultSet = preparedStatementPost.executeQuery();

            while(resultSet.next()){
                Post post = new Post();
                Long id = resultSet.getLong("id");
                post.setId(id);
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getDate("created"));
                post.setUpdated(resultSet.getDate("updated"));

                try(PreparedStatement preparedStatementLabel = ConnectDB.getPreparedStatement(PostSQLTeams.GET_ID_LABELS.getTeam())){
                    preparedStatementLabel.setLong(1, id);
                    ResultSet resultSetLabel = preparedStatementLabel.executeQuery();
                    List <Label> labelList = getLabelList(resultSetLabel);
                    post.setLabels(labelList);
                    postList.add(post);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return postList;
    }

    @Override
    public void deleteById(Long id) {
        try(PreparedStatement preparedStatementPost = ConnectDB.getPreparedStatement(PostSQLTeams.DELETE_POST.getTeam())){
            preparedStatementPost.setLong(1, id);
            preparedStatementPost.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatementLabel = ConnectDB.getPreparedStatement(PostSQLTeams.DELETE_POST_LABELS.getTeam())){
            preparedStatementLabel.setLong(1, id);
            preparedStatementLabel.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<Label> getLabelList(ResultSet resultSetLabel){

        List <Label> labelList = new ArrayList<Label>();

        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(PostSQLTeams.GET_LABEL_NAME.getTeam())){
            while (resultSetLabel.next()) {
                Long labelId = resultSetLabel.getLong("label_id");
                preparedStatement.setLong(1, labelId);
                ResultSet resultSetLabelName = preparedStatement.executeQuery();
                resultSetLabelName.next();
                String name = resultSetLabelName.getString("name");
                labelList.add(new Label(labelId, name));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return labelList;
    }
}