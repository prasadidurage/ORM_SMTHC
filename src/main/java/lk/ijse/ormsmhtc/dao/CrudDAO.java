package lk.ijse.ormsmhtc.dao;

import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO {
    String getLastId();
    boolean save(T dto);
    boolean update(T dto);
    T getAllById(String paymentId);
    boolean delete(String paymentId);
    ArrayList<T> getAll();
}
