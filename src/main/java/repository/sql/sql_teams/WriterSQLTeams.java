package repository.sql.sql_teams;

public enum WriterSQLTeams {

    ADD_WRITERS("INSERT INTO writers VALUES(0, ?,?)"),
    ADD_POSTS_WRITERS("INSERT INTO writer_posts VALUES(?,?)"),
    UPDATE_WRITER("UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?"),
    INNER_JOIN_WRITER_POST("SELECT * FROM writers INNER JOIN writer_posts ON id = ? AND writer_id = ?"),
    GET_ALL_WRITERS("SELECT * FROM writers"),
    GET_POSTSID_WRITRES("SELECT post_id FROM writer_posts WHERE writer_id = ?"),
    DELETE_WRITER("DELETE FROM writers WHERE id = ?"),
    DELETE_WRITER_POST("DELETE FROM writer_posts WHERE writer_id = ?");

    String sqlTeam;

    WriterSQLTeams(String sqlTeam) {
        this.sqlTeam = sqlTeam;
    }

    public String getTeam(){
        return this.sqlTeam;
    }

    }
