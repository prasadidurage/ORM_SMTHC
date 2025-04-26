package lk.ijse.ormsmhtc.dao.custom.impl;


import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.dao.custom.PaymentDAO;
import lk.ijse.ormsmhtc.entity.*;
import lk.ijse.ormsmhtc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements PaymentDAO {
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

    @Override
    public boolean update(Payment dto) {
        return false;
    }

    public boolean update(Payment payment, String therapySessionId, String patientId, String programId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Patient patient = session.get(Patient.class, patientId);
            TherapyProgram therapyProgram = session.get(TherapyProgram.class, programId);

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

            if (payment.getId() == null) {
                System.err.println("Payment ID is null, cannot update.");
                transaction.rollback();
                return false;
            }

            TherapySession therapySession = null;
            if (therapySessionId != null) {
                therapySession = session.get(TherapySession.class, therapySessionId);
                payment.setTherapySession(therapySession);
            } else {
                payment.setTherapySession(null);
            }

            payment.setPatient(patient);
            payment.setProgram(therapyProgram);

            session.merge(payment); // Safe now
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
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

    public Payment getBalance(String programID, String patientID) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        Payment payment = null;

        try {
            transaction = session.beginTransaction();
            Query<Payment> query = session.createQuery(
                    "FROM Payment p WHERE p.patient.id = :patientId AND p.program.id = :programId " +
                            "AND p.date = (SELECT MAX(p2.date) FROM Payment p2 WHERE p2.patient.id = :patientId AND p2.program.id = :programId) ORDER BY p.id DESC",
                    Payment.class
            );
            query.setParameter("programId", programID);
            query.setParameter("patientId", patientID);
            query.setMaxResults(1);
            payment = query.uniqueResult();
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

    public ArrayList<Payment> findByPaymentId(String paymentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Payment> payments = session.createQuery(
                        "FROM Payment p WHERE p.id = :id", Payment.class)
                .setParameter("id", paymentId)
                .getResultList();
        session.close();
        return (ArrayList<Payment>) payments;
    }
}
