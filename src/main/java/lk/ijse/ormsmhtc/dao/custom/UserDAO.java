package lk.ijse.ormsmhtc.dao.custom;


import lk.ijse.ormsmhtc.dao.CrudDAO;
import lk.ijse.ormsmhtc.entity.User;

public interface UserDAO extends CrudDAO<User> {
    public String getPassword(String userName);
    public String checkMail(String emails);
    public User getAllByUserCredential(String userName, String password);
}
