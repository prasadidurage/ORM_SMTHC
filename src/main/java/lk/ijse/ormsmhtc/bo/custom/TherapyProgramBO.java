package lk.ijse.ormsmhtc.bo.custom;


import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.TherapyProgramDto;

import java.util.ArrayList;

public interface TherapyProgramBO extends SuperBO {
    public String getNextId();
    public ArrayList<TherapyProgramDto> getAllPrograms();
    public boolean saveTherapyProgram(TherapyProgramDto therapyProgramDto);
    public boolean updateTherapyProgram(TherapyProgramDto therapyProgramDto);
    public boolean deleteTherapy(String id);
    public ArrayList<String> getAllProgramId();
    public TherapyProgramDto getAllById(String programId);
    public double getFee(String programID) ;
}
