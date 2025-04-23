package lk.ijse.ormsmhtc.dao.custom.impl;

import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl {
    FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    public String getLastId() {
        Session session = factoryConfiguration.getSession();
        String lastId = session.createQuery("SELECT pp.id FROM Payment pp ORDER BY pp.id DESC ", String.class)//String.class -return wena data type eka
                .setMaxResults(1)
                .uniqueResult();
        return lastId;
    }

    public boolean save(Payment payment) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.persist(payment);
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

    public boolean update(Payment payment, String therapySessionId, String patientId, String programId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        TherapySession therapySession = session.get(TherapySession.class,therapySessionId);
        Patient patient = session.get(Patient.class,patientId);
        TherapyProgram therapyProgram = session.get(TherapyProgram.class,programId);
        payment.setPatient(patient);
        payment.setTherapySession(therapySession);
        payment.setProgram(therapyProgram);
        try {
            if (patient == null) {
                System.err.println("Patient not found for ID: " + patientId);
                transaction.rollback();
                return false;
            }

            if (therapyProgram == null) {
                System.err.println("TherapyProgram not found for ID: " + programId);
                transaction.rollback();
                return false;
            }

            session.merge(payment);
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

    public Payment getAllById(String paymentId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        Payment payment = null;

        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery("FROM Payment p WHERE p.id = :paymentId ", Payment.class);
            query.setParameter("paymentId",paymentId);
            payment =  query.uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return payment;
    }

    public boolean delete(String paymentId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Payment payment = session.get(Payment.class, paymentId);
            if (payment !=null){
                session.remove(payment);
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

    public ArrayList<Payment> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<Payment> payments = null;

        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery("FROM Payment", Payment.class); // Query for Patient entities
            payments = (List<Payment>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<Payment>) payments;
    }
}
