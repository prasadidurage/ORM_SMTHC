package lk.ijse.ormsmhtc.dao.custom.impl;


import lk.ijse.ormsmhtc.config.FactoryConfiguration;
import lk.ijse.ormsmhtc.dao.custom.TherapistProgramDAO;
import lk.ijse.ormsmhtc.entity.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TherapistProgramDAOImpl implements TherapistProgramDAO {
    private final FactoryConfiguration factoryConfiguration = FactoryConfiguration.getInstance();
    public boolean save(TherapistProgramId therapistProgramId, String therapistId, String programId, String day, LocalTime startTime, LocalTime endTime) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Therapist therapist = session.get(Therapist.class, therapistId);
            TherapyProgram program = session.get(TherapyProgram.class, programId);
            TherapistProgram therapistProgram = new TherapistProgram(therapistProgramId,therapist,program,
                    day,startTime,endTime);
            session.persist(therapistProgram);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    @Override
    public String getLastId() {
        return "";
    }

    @Override
    public boolean save(TherapistProgram dto) {
        return false;
    }

    @Override
    public boolean update(TherapistProgram dto) {
        return false;
    }

    @Override
    public TherapistProgram getAllById(String paymentId) {
        return null;
    }

    @Override
    public boolean delete(String paymentId) {
        return false;
    }

    public ArrayList<TherapistProgram> getAll() {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        List<TherapistProgram> therapistPrograms = null;
        try {
            Query query = session.createQuery("FROM TherapistProgram",TherapistProgram.class);
            therapistPrograms = (List<TherapistProgram>) query.list();
            transaction.commit();
        }catch (Exception e){
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
        return (ArrayList<TherapistProgram>) therapistPrograms;
    }

    public boolean update(TherapistProgramId therapistProgramId, String therapistId, String programId, String day, LocalTime startTime, LocalTime endTime) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Therapist therapist = session.get(Therapist.class, therapistId);
            TherapyProgram program = session.get(TherapyProgram.class, programId);
            TherapistProgram therapistProgram = new TherapistProgram(therapistProgramId,therapist,program,
                    day,startTime,endTime);
            session.merge(therapistProgram);
            transaction.commit();
            return true;
        }catch (Exception e){
            transaction.rollback();
            return false;
        }finally {
            if (session != null){
                session.close();
            }
        }
    }

    public boolean delete(TherapistProgramId therapistProgramId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            TherapistProgram therapistProgram = session.get(TherapistProgram.class, therapistProgramId);
            if (therapistProgram !=null){
                session.remove(therapistProgram);
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

    public List<TherapistProgram> getDataWiseTherapist(String therapistID) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = session.beginTransaction();
        List<TherapistProgram> therapistPrograms = null;
        try {
            Query query = session.createQuery("FROM TherapistProgram tp WHERE tp.therapistId.id = :therapistId", TherapistProgram.class);
            query.setParameter("therapistId", therapistID);
            therapistPrograms = (List<TherapistProgram>) query.list();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return therapistPrograms;
    }

    public ArrayList<TherapistProgram> getAllByID(String programId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<TherapistProgram> therapyPrograms = null;

        try {
            transaction = session.beginTransaction();
            Query<TherapistProgram> query = session.createQuery("FROM TherapistProgram tp WHERE tp.programId.id = :programId", TherapistProgram.class); // Query for Patient entities
            query.setParameter("programId",programId);
            therapyPrograms = (List<TherapistProgram>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<TherapistProgram>) therapyPrograms;
    }

    public ArrayList<TherapistProgram> getAllByIDS(String programId, String therapistId) {
        Session session = factoryConfiguration.getSession();
        Transaction transaction = null;
        List<TherapistProgram> therapyPrograms = null;

        try {
            transaction = session.beginTransaction();
            Query<TherapistProgram> query = session.createQuery("FROM TherapistProgram tp WHERE tp.programId.id = :programId AND tp.therapistId.id = :therapistId", TherapistProgram.class); // Query for Patient entities
            query.setParameter("programId",programId);
            query.setParameter("therapistId",therapistId);
            therapyPrograms = (List<TherapistProgram>) query.list(); // Retrieve list of patients
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (ArrayList<TherapistProgram>) therapyPrograms;
    }
}
