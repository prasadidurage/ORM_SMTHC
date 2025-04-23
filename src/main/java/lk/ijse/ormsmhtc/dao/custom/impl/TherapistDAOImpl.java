package lk.ijse.ormsmhtc.dao.custom.impl;

import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class TherapistDAOImpl {
    private FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    public List<Therapist> getAllTherapist() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<Therapist> therapists = null;

        try {
            transaction = session.beginTransaction();
            Query<Therapist> query = session.createQuery("FROM Therapist", Therapist.class); // Query for Patient entities
            therapists = (List<Therapist>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return therapists;
    }

    public boolean save(Therapist therapist) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(therapist);
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

    public boolean update(Therapist therapist) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(therapist);
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
            Therapist therapist = session.get(Therapist.class, id);
            if (therapist !=null){
                session.remove(therapist);
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

    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT t.id FROM Therapist t ORDER BY t.id DESC ", String.class)//String.class -return wena data type eka
                .setMaxResults(1)
                .uniqueResult();
        return lastId;
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
}
