package repository.sql;

import model.Label;
import model.Post;
import repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlPostRepositoryImpl implements PostRepository {

    @Override
    public Post save(Post post) {

        String sql = "INSERT INTO posts VALUES(0,?,?,null)";
        String sqlLabel = "INSERT INTO post_labels VALUES(?,?)";
        Long id = null;

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setDate(2, new java.sql.Date(post.getCreated().getTime()));

            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlLabel);
            for(Label label : post.getLabels()){
                preparedStatement.setLong(1, id);
                preparedStatement.setLong(2,label.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        post.setId(id);

        return post;
    }

    @Override
    public Post update(Post post) {

        String sqlPost = "UPDATE posts SET content = ?, updated = ? WHERE id = ?";
        String deleteLabel = "DELETE FROM post_labels WHERE post_id = ?";
        String sqlPostLabel = "INSERT INTO post_labels VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlPost);
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setDate(2, new java.sql.Date(post.getUpdated().getTime()));
            preparedStatement.setLong(3, post.getId());
            preparedStatement.executeUpdate();

            preparedStatement = ConnectDB.getConnection().prepareStatement(deleteLabel);
            preparedStatement.setLong(1, post.getId());
            preparedStatement.executeUpdate();

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlPostLabel);

            for(Label label : post.getLabels()){
                preparedStatement.setLong(1, post.getId());
                preparedStatement.setLong(2, label.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post getById(Long id) {

        Post post = new Post();
        String sqlPost = "SELECT * FROM posts WHERE id = ?";
        String sqlPostLabels = "SELECT label_id FROM post_labels WHERE post_id = ?";

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlPost);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            post.setId(id);
            post.setContent(resultSet.getString("content"));
            post.setCreated(resultSet.getDate("created"));
            post.setUpdated(resultSet.getDate("updated"));

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlPostLabels);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();

            List<Label> labelList = getLabelList(resultSet);
            post.setLabels(labelList);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public List<Post> getAll() {

        String sqlPosts = "SELECT * FROM posts";
        String sqlPostLabels = "SELECT label_id FROM post_labels WHERE post_id = ?";
        List<Post> postList = new ArrayList<Post>();

        try {
            Statement statement = ConnectDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlPosts);

            while(resultSet.next()){
                Post post = new Post();
                Long id = resultSet.getLong("id");
                post.setId(id);
                post.setContent(resultSet.getString("content"));
                post.setCreated(resultSet.getDate("created"));
                post.setUpdated(resultSet.getDate("updated"));

                PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlPostLabels);
                preparedStatement.setLong(1, id);
                ResultSet resultSetLabel = preparedStatement.executeQuery();

                List <Label> labelList = getLabelList(resultSetLabel);
                post.setLabels(labelList);
                postList.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return postList;
    }

    @Override
    public void deleteById(Long id) {

        String sqlDeletePost = "DELETE FROM posts WHERE id = ?";
        String sqlDeleteLabelPost = "DELETE FROM post_labels WHERE post_id = ?";

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlDeletePost);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlDeleteLabelPost);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<Label> getLabelList(ResultSet resultSetLabel){

        List <Label> labelList = new ArrayList<Label>();
        String sqlLabel = "SELECT name FROM labels WHERE id = ?";

        try {
            while (resultSetLabel.next()) {
                Long labelId = resultSetLabel.getLong("label_id");
                PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlLabel);
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