package repository.sql.sql_teams;

public enum PostSQLTeams {

    ADD_POST("INSERT INTO posts VALUES(0,?,?,null)"),
    ADD_POST_LABEL("INSERT INTO post_labels VALUES(?,?)"),
    UPDATE_POST("UPDATE posts SET content = ?, updated = ? WHERE id = ?"),
    DELETE_POST_LABELS("DELETE FROM post_labels WHERE post_id = ?"),
    GET_POSTS_BY_ID("SELECT * FROM posts WHERE id = ?"),
    GET_ID_LABELS("SELECT label_id FROM post_labels WHERE post_id = ?"),
    GET_ALL_POSTS("SELECT * FROM posts"),
    DELETE_POST("DELETE FROM posts WHERE id = ?"),
    GET_LABEL_NAME("SELECT name FROM labels WHERE id = ?");

    String sqlTeam;

    PostSQLTeams(String sqlTeam) {
        this.sqlTeam = sqlTeam;
    }

    public String getTeam(){
        return this.sqlTeam;
    }

}
