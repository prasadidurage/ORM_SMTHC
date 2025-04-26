package lk.ijse.ormsmhtc.dao.custom.impl;

import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.dao.custom.PatientProgramDAO;
import lk.ijse.ormsmhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientProgramDAOImpl implements PatientProgramDAO {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public boolean save(PatientProgram dto) {
        return false;
    }

    @Override
    public boolean update(PatientProgram dto) {
        return false;
    }

    @Override
    public PatientProgram getAllById(String paymentId) {
        return null;
    }

    @Override
    public boolean delete(String paymentId) {
        return false;
    }

    public ArrayList<PatientProgram> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<PatientProgram> patientPrograms = null;
        try {
            transaction = session.beginTransaction();
            Query<PatientProgram> query = session.createQuery("FROM PatientProgram", PatientProgram.class); // Query for Patient entities
            patientPrograms = (List<PatientProgram>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<PatientProgram>) patientPrograms;
    }


    public boolean save(PatientProgramId patientProgramId, String patientId, String programId, Date registerDate, String paymentId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Patient patient = session.get(Patient.class,patientId);
            TherapyProgram therapyProgram = session.get(TherapyProgram.class,programId);
            Payment payment = session.get(Payment.class,paymentId);
            PatientProgram program = new PatientProgram(
                    patientProgramId,
                    patient,
                    therapyProgram,
                    registerDate,
                    payment
            );
            session.persist(program);
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

    public boolean update(PatientProgramId patientProgramId, String patientId, String programId, Date registerDate, String paymentId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Patient patient = session.get(Patient.class,patientId);
            TherapyProgram therapyProgram = session.get(TherapyProgram.class,programId);
            Payment payment = session.get(Payment.class,paymentId);
            PatientProgram program = new PatientProgram(
                    patientProgramId,
                    patient,
                    therapyProgram,
                    registerDate,
                    payment
            );
            session.merge(program);
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

    public boolean delete(PatientProgramId patientProgramId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            PatientProgram patientProgram = session.get(PatientProgram.class, patientProgramId);
            if (patientProgram !=null){
                session.remove(patientProgram);
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

    public ArrayList<PatientProgram> getAllByID(String patientId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<PatientProgram> patientPrograms = null;

        try {
            transaction = session.beginTransaction();
//            Patient patient = session.get(Patient.class,patientId);
            Query<PatientProgram> query = session.createQuery("FROM PatientProgram p WHERE p.patientId.id = :patientID", PatientProgram.class); // Query for Patient entities
            query.setParameter("patientID",patientId);
            patientPrograms = (List<PatientProgram>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<PatientProgram>) patientPrograms;
    }
}
