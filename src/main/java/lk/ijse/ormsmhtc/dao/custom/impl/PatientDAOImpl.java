package lk.ijse.ormsmhtc.dao.custom.impl;

import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.dao.custom.PatientDAO;
import lk.ijse.ormsmhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.List;

public class PatientDAOImpl implements PatientDAO {
    private final FactoryConfiguration factoryConfiguration =FactoryConfiguration.getInstance();

    public boolean save(Patient patient) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(patient);
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

    public List<Patient> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<Patient> patients = null;

        try {
            transaction = session.beginTransaction();
            Query<Patient> query = session.createQuery("FROM Patient", Patient.class); // Query for Patient entities
            patients = (List<Patient>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return patients;
    }

    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT p.id FROM Patient p ORDER BY p.id DESC ", String.class)//String.class -return wena data type eka
                .setMaxResults(1)
                .uniqueResult();
        return lastId;
    }

    public boolean update(Patient patient) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(patient);
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
            Patient patient = session.get(Patient.class, id);
            if (patient!=null){
                session.remove(patient);
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

    public Patient getAllById(String patientId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        Patient patients = null;

        try {
            transaction = session.beginTransaction();
            Query<Patient> query = session.createQuery("FROM Patient p WHERE p.id = :patientId ", Patient.class);
            query.setParameter("patientId",patientId);
            patients = (Patient) query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return patients;
    }
}
