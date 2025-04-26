package lk.ijse.ormsmhtc.bo.custom;


import lk.ijse.ormsmhtc.bo.SuperBO;
import lk.ijse.ormsmhtc.dto.UserDto;

import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public String getPassword(String username);
    public String getNextUserId();
    public boolean saveUser(UserDto userDto);
    public boolean getUserEmail(String emails);
    public ArrayList<UserDto> getAllUsers();
    public boolean deleteUser(String userId);
    public UserDto getAllByUserName(String userName, String password);
    public boolean update(UserDto userDto, String newPwd);
}
