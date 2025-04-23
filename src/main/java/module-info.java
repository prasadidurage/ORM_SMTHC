module lk.ijse.ormsmhtc {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires static lombok;


    opens lk.ijse.ormsmhtc.controller to javafx.fxml;
    opens lk.ijse.ormsmhtc.dto.tmp to javafx.base;
    opens lk.ijse.ormsmhtc.config to jakarta.persistence;
    opens lk.ijse.ormsmhtc.entity to org.hibernate.orm.core;
    exports lk.ijse.ormsmhtc;
    opens lk.ijse.ormsmhtc.dto to javafx.base;
}