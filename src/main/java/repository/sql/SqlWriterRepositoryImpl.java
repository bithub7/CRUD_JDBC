package repository.sql;

import model.Post;
import model.Writer;
import repository.PostRepository;
import repository.WriterRepository;
import repository.sql.sql_teams.WriterSQLTeams;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlWriterRepositoryImpl implements WriterRepository {

    @Override
    public Writer save(Writer writer) {

        Long id = null;

        try(PreparedStatement preparedStatementWriter = ConnectDB.getPreparedStatement(WriterSQLTeams.ADD_WRITERS.getTeam())){
            preparedStatementWriter.setString(1, writer.getFirstName());
            preparedStatementWriter.setString(2, writer.getLastName());
            preparedStatementWriter.executeUpdate();
            ResultSet resultSet = preparedStatementWriter.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try(PreparedStatement preparedStatementPost = ConnectDB.getPreparedStatement(WriterSQLTeams.ADD_POSTS_WRITERS.getTeam())) {
            for (Post post : writer.getPosts()) {
                preparedStatementPost.setLong(1, id);
                preparedStatementPost.setLong(2, post.getId());
                preparedStatementPost.executeUpdate();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        writer.setId(id);
        return writer;
    }

    @Override
    public Writer update(Writer writer) {
        try(PreparedStatement preparedStatementUpdate = ConnectDB.getPreparedStatement(WriterSQLTeams.UPDATE_WRITER.getTeam())){
            preparedStatementUpdate.setString(1, writer.getFirstName());
            preparedStatementUpdate.setString(2, writer.getLastName());
            preparedStatementUpdate.setLong(3, writer.getId());
            preparedStatementUpdate.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatementDelete = ConnectDB.getPreparedStatement(WriterSQLTeams.DELETE_WRITER_POST.getTeam())){
            preparedStatementDelete.setLong(1, writer.getId());
            preparedStatementDelete.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(WriterSQLTeams.ADD_POSTS_WRITERS.getTeam())) {
            for (Post post : writer.getPosts()) {
                preparedStatement.setLong(1, writer.getId());
                preparedStatement.setLong(2, post.getId());
                preparedStatement.executeUpdate();
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public Writer getById(Long id) {

        Writer writer = new Writer();

        try(PreparedStatement preparedStatement = ConnectDB.getPreparedStatement(WriterSQLTeams.INNER_JOIN_WRITER_POST.getTeam())){
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            writer.setId(resultSet.getLong("id"));
            writer.setFirstName(resultSet.getString("first_name"));
            writer.setLastName(resultSet.getString("last_name"));
            List<Post> postList = getPostList(resultSet);
            writer.setPosts(postList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public List<Writer> getAll() {

        List<Writer> writerList = new ArrayList<Writer>();

        try(PreparedStatement preparedStatementWriter = ConnectDB.getPreparedStatement(WriterSQLTeams.GET_ALL_WRITERS.getTeam());){
            ResultSet resultSet = preparedStatementWriter.executeQuery();

            while (resultSet.next()){
                Writer writer = new Writer();
                Long id = resultSet.getLong("id");
                writer.setId(id);
                writer.setFirstName(resultSet.getString("first_name"));
                writer.setLastName(resultSet.getString("last_name"));

                try(PreparedStatement preparedStatementPostId = ConnectDB.getPreparedStatement(WriterSQLTeams.GET_POSTSID_WRITRES.getTeam())) {
                    preparedStatementPostId.setLong(1, id);
                    ResultSet resultSetPost = preparedStatementPostId.executeQuery();
                    List<Post> postList = getPostList(resultSetPost);
                    writer.setPosts(postList);
                    writerList.add(writer);
                }catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writerList;
    }

    @Override
    public void deleteById(Long id) {
        try(PreparedStatement preparedStatementWriters = ConnectDB.getPreparedStatement(WriterSQLTeams.DELETE_WRITER.getTeam())){
            preparedStatementWriters.setLong(1, id);
            preparedStatementWriters.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(PreparedStatement preparedStatementPost = ConnectDB.getPreparedStatement(WriterSQLTeams.DELETE_WRITER_POST.getTeam())){
            preparedStatementPost.setLong(1, id);
            preparedStatementPost.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private List<Post> getPostList(ResultSet resultSetPost){

        List<Post> postList = new ArrayList<Post>();
        PostRepository postRepository = new SqlPostRepositoryImpl();
        try{
            while (resultSetPost.next()) {
                Post post;
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