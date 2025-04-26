package lk.ijse.ormsmhtc.dao.custom.impl;


import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.dao.custom.TherapyProgramDAO;
import lk.ijse.ormsmhtc.entity.TherapyProgram;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramDAOImpl implements TherapyProgramDAO {
    private FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT tp.id FROM TherapyProgram tp ORDER BY tp.id DESC ", String.class)//String.class -return wena data type eka
                .setMaxResults(1)
                .uniqueResult();
        return lastId;
    }

    public ArrayList<TherapyProgram> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<TherapyProgram> therapyPrograms = null;

        try {
            transaction = session.beginTransaction();
            Query<TherapyProgram> query = session.createQuery("FROM TherapyProgram", TherapyProgram.class); // Query for Patient entities
            therapyPrograms = (List<TherapyProgram>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<TherapyProgram>) therapyPrograms;
    }

    public boolean save(TherapyProgram therapyProgram) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(therapyProgram);
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

    public boolean update(TherapyProgram therapyProgram) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(therapyProgram);
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

    public boolean delete(String id) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, id);
            if (therapyProgram !=null){
                session.remove(therapyProgram);
                transaction.commit();
                return true;
            }
            return false;
        }catch (Exception e) {
            transaction.rollback();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    public List<TherapyProgram> getAllId(){
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<TherapyProgram> therapyPrograms = null;

        try {
            transaction = session.beginTransaction();
            Query<TherapyProgram> query = session.createQuery("SELECT tp.id FROM TherapyProgram tp", TherapyProgram.class); // Query for Patient entities
            therapyPrograms = (List<TherapyProgram>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return therapyPrograms;
    }

    public double getFee(String programID) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        Double fee = 0.0;

        try {
            transaction = session.beginTransaction();
            Query<Double> query = session.createQuery("SELECT tp.cost FROM TherapyProgram tp WHERE tp.id = :programId", Double.class); // Query for Patient entities
            query.setParameter("programId",programID);
            fee = query.uniqueResult(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return fee != null ? fee : 0.0;
    }

    public TherapyProgram getAllById(String programId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        TherapyProgram therapyProgram = null;

        try {
            transaction = session.beginTransaction();
            Query<TherapyProgram> query = session.createQuery("FROM TherapyProgram tp WHERE tp.id = :programId ", TherapyProgram.class);
            query.setParameter("programId",programId);
            therapyProgram =  query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return therapyProgram;
    }

//    public ArrayList<TherapyProgram> getAllByID(String programId) {
//        Session session = factoryConfiguration.getSession();
//        Transaction transaction = null;
//        List<TherapyProgram> therapyPrograms = null;
//
//        try {
//            transaction = session.beginTransaction();
//            Query<TherapyProgram> query = session.createQuery("FROM TherapyProgram tp WHERE tp.id = :programId", TherapyProgram.class); // Query for Patient entities
//            query.setParameter("id",programId);
//            therapyPrograms = (List<TherapyProgram>) query.list(); // Retrieve list of patients
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//        } finally {
//            session.close();
//        }
//        return (ArrayList<TherapyProgram>) therapyPrograms;
//    }
}
