package lk.ijse.ormsmhtc.dao;


import lk.ijse.ormsmhtc.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){}

    public static DAOFactory getInstance(){
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType{
        PATIENT,
        PATIENT_PROGRAM,
        PAYMENT,
        THERAPIST,
        THERAPIST_PROGRAM,
        THERAPY_PROGRAM,
        THERAPY_SESSION,
        USER,
        QUERY_DAO
    }

    public SuperDAO getDAO(DAOType type){
        switch (type){
            case PATIENT:
                return new PatientDAOImpl();
            case PATIENT_PROGRAM:
                return new PatientProgramDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case THERAPIST:
                return new TherapistDAOImpl();
            case THERAPIST_PROGRAM:
                return new TherapistProgramDAOImpl();
            case THERAPY_PROGRAM:
                return new TherapyProgramDAOImpl();
            case THERAPY_SESSION:
                return new TherapySessionDAOImpl();
            case USER:
                return new UserDAOImpl();
            case QUERY_DAO:
                return new QueryDAOImpl();
            default:
                return null;

        }
    }
}
