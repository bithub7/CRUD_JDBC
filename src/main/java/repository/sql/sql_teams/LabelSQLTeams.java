package repository.sql.sql_teams;

public enum LabelSQLTeams {

    ADD_LABEL("INSERT INTO labels VALUES(0, ?)"),
    UPDATE_LABEL("UPDATE labels SET name = ? WHERE id = ?"),
    GET_LABEL_BY_ID("SELECT * FROM labels WHERE id = ?"),
    GET_ALL_LABELS("SELECT * FROM labels"),
    DELETE_LABEL_DY_ID("DELETE FROM labels WHERE id = ?");

    private String sqlTeam;

    LabelSQLTeams(String sqlTeam) {
        this.sqlTeam = sqlTeam;
    }

    public String getTeam(){
        return this.sqlTeam;
    }
}
