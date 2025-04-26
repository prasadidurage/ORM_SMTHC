package lk.ijse.ormsmhtc.dao.custom.impl;


import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.dao.custom.UserDAO;
import lk.ijse.ormsmhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {
    private FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    public String getPassword(String userName) {
        Session session = null;
        String password = null;
        try {
            session = factoryConfiguration.getSession();
            Query query = session.createQuery("SELECT password FROM User WHERE username = :userName");
            query.setParameter("userName", userName);
            password = (String) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return password;
    }

    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT u.id FROM User u ORDER BY u.id DESC ", String.class)//String.class -return wena data type eka
                .setMaxResults(1)
                .uniqueResult();
        return lastId;
    }

    public boolean save(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public String checkMail(String emails) {
        Session session = null;
        String email = null;
        try {
            session = factoryConfiguration.getSession();
            Query query = session.createQuery("SELECT u.email FROM User u WHERE u.email = :email");
            query.setParameter("email", emails);
            email = (String) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return email;
    }

    public ArrayList<User> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<User> users = null;

        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User", User.class); // Query for Patient entities
            users = (List<User>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<User>) users;
    }

    public boolean delete(String userId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = session.get(User.class,userId);
            session.remove(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    public User getAllByUserCredential(String userName, String password) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        User users = null;

        try {
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery("FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username",userName);
            users = query.uniqueResult(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return users;
    }

    public boolean update(User user) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(user);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            if(session != null){
                session.close();
            }
        }
    }

    @Override
    public User getAllById(String paymentId) {
        return null;
    }
}
