package Memo.Memo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionTests {
    @Test
    public void testConnection() throws Exception{
        Class.forName("org.mariadb.jdbc.Driver");

        Connection connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/memodb",
                "root",
                "6030"
        );
        Assertions.assertNotNull(connection);

        connection.close();
    }
}
