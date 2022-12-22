package jdi.chat.application.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface IConnectionDAO {

    Connection createConnection() throws SQLException, IOException, ClassNotFoundException;

}
