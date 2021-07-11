package repository.sql;

import model.Post;
import model.Writer;
import repository.PostRepository;
import repository.WriterRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlWriterRepositoryImpl implements WriterRepository {

    @Override
    public Writer save(Writer writer) {

        String sqlWriters = "INSERT INTO writers VALUES(0, ?,?)";
        String sqlPostsWriters = "INSERT INTO writer_posts VALUES(?,?)";
        Long id = null;

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlWriters, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlPostsWriters);
            for(Post post : writer.getPosts()){
                preparedStatement.setLong(1, id);
                preparedStatement.setLong(2,post.getId());
                preparedStatement.executeUpdate();
            }

            writer.setId(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        String sqlWriterUpdate = "UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?";
        String sqlDeletePost = "DELETE FROM writer_posts WHERE writer_id = ?";
        String sqlUpdatePost = "INSERT INTO writer_posts VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlWriterUpdate);
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setLong(3, writer.getId());
            preparedStatement.executeUpdate();

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlDeletePost);
            preparedStatement.setLong(1, writer.getId());
            preparedStatement.executeUpdate();

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlUpdatePost);

            for(Post post : writer.getPosts()){
                preparedStatement.setLong(1, writer.getId());
                preparedStatement.setLong(2, post.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer getById(Long id) {

        Writer writer = new Writer();
        String sqlGetWriter = "SELECT * FROM writers WHERE id = ?";
        String sqlGetPostId = "SELECT post_id FROM writer_posts WHERE writer_id = ?";

        try {

            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlGetWriter);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            writer.setId(resultSet.getLong("id"));
            writer.setFirstName(resultSet.getString("first_name"));
            writer.setLastName(resultSet.getString("last_name"));

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlGetPostId);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            List<Post> postList = getPostList(resultSet);
            writer.setPosts(postList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {

        String sqlGetWriter = "SELECT * FROM writers";
        String sqlGetWriterPost = "SELECT post_id FROM writer_posts WHERE writer_id = ?";
        List<Writer> writerList = new ArrayList<Writer>();

        try{
            Statement statement = ConnectDB.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sqlGetWriter);

            while (resultSet.next()){
                Writer writer = new Writer();
                Long id = resultSet.getLong("id");
                writer.setId(id);
                writer.setFirstName(resultSet.getString("first_name"));
                writer.setLastName(resultSet.getString("last_name"));

                PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlGetWriterPost);
                preparedStatement.setLong(1, id);
                ResultSet resultSetPost = preparedStatement.executeQuery();
                List<Post> postList = getPostList(resultSetPost);
                writer.setPosts(postList);
                writerList.add(writer);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writerList;
    }

    @Override
    public void deleteById(Long id) {

        String sqlDeleteWriter = "DELETE FROM writers WHERE id = ?";
        String sqlDeleteWriterPost = "DELETE FROM writer_posts WHERE writer_id = ?";

        try {
            PreparedStatement preparedStatement = ConnectDB.getConnection().prepareStatement(sqlDeleteWriter);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

            preparedStatement = ConnectDB.getConnection().prepareStatement(sqlDeleteWriterPost);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<Post> getPostList(ResultSet resultSetPost){

        List<Post> postList = new ArrayList<Post>();
        PostRepository postRepository = new SqlPostRepositoryImpl();
        String sqlGetPost = "SELECT * FROM posts WHERE id = ?";
        PreparedStatement preparedStatement;
        try{
            while (resultSetPost.next()) {
                Post post = new Post();
                Long postId = resultSetPost.getLong("post_id");
                post = postRepository.getById(postId);
                postList.add(post);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return postList;
    }
}
