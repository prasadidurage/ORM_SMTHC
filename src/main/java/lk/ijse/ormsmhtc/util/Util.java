package lk.ijse.ormsmhtc.util;

import lk.ijse.ormsmhtc.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Util {
    private static Util instance;
    private UserDto userDto;

    private Util() {}

    public static synchronized Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }
}
