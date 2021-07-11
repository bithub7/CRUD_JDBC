package repository;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository <T,ID>{
    T save(T obj) throws SQLException;
    T update(T obj);
    T getById(ID id);
    List<T> getAll();
    void deleteById(ID id);
}
