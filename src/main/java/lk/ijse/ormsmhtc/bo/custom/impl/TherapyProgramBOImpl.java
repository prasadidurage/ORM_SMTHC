package lk.ijse.ormsmhtc.bo.custom.impl;



import lk.ijse.ormsmhtc.bo.custom.TherapyProgramBO;
import lk.ijse.ormsmhtc.dao.DAOFactory;
import lk.ijse.ormsmhtc.dao.custom.impl.TherapyProgramDAOImpl;
import lk.ijse.ormsmhtc.dto.TherapyProgramDto;
import lk.ijse.ormsmhtc.entity.*;

import java.util.ArrayList;
import java.util.List;

public class TherapyProgramBOImpl implements TherapyProgramBO {
    private TherapyProgramDAOImpl therapyProgramDAO = (TherapyProgramDAOImpl) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.THERAPY_PROGRAM);
    public String getNextId() {
        String lastId = therapyProgramDAO.getLastId();
        if (lastId != null) {
            String subString = lastId.substring(2);
            int lastIndex = Integer.parseInt(subString);
            int nextIndex = lastIndex + 1;
            return String.format("TP%03d", nextIndex);
        }
        return "TP001";
    }

    public ArrayList<TherapyProgramDto> getAllPrograms() {
        List<TherapyProgram> therapyPrograms = therapyProgramDAO.getAll();
        ArrayList<TherapyProgramDto> therapyProgramList = new ArrayList<>();
        for (TherapyProgram therapyProgram : therapyPrograms){
            TherapyProgramDto therapyProgramDto = new TherapyProgramDto(
                    therapyProgram.getId(),
                    therapyProgram.getName(),
                    therapyProgram.getDuration(),
                    therapyProgram.getCost(),
                    therapyProgram.getDescription()
            );
            therapyProgramList.add(therapyProgramDto);
        }
        return therapyProgramList;
    }

    public boolean saveTherapyProgram(TherapyProgramDto therapyProgramDto) {
        return therapyProgramDAO.save(new TherapyProgram(
                therapyProgramDto.getId(),
                therapyProgramDto.getName(),
                therapyProgramDto.getDuration(),
                therapyProgramDto.getCost(),
                therapyProgramDto.getDescription()
        ));
    }

    public boolean updateTherapyProgram(TherapyProgramDto therapyProgramDto) {
        return therapyProgramDAO.update(new TherapyProgram(
                therapyProgramDto.getId(),
                therapyProgramDto.getName(),
                therapyProgramDto.getDuration(),
                therapyProgramDto.getCost(),
                therapyProgramDto.getDescription()
        ));
    }

    public boolean deleteTherapy(String id) {
        return therapyProgramDAO.delete(id);
    }

    public ArrayList<String> getAllProgramId() {
        List<TherapyProgramDto> therapyProgramDtos = getAllPrograms();
        ArrayList<String> IDS = new ArrayList<>();
        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos){
            IDS.add(therapyProgramDto.getId());
        }
        return IDS;
    }

    public TherapyProgramDto getAllById(String programId) {
        ArrayList<TherapyProgramDto> therapyProgramDtos = getAllPrograms();
        for (TherapyProgramDto therapyProgramDto : therapyProgramDtos){
            if (therapyProgramDto.getId().equals(programId)){
                return therapyProgramDto;
            }
        }
        return null;
    }


    public double getFee(String programID) {
        return therapyProgramDAO.getFee(programID);
    }
}
