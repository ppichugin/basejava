package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public static <T> void execute(ConnectionFactory conn, String SqlStatement, ProcessSqlRequest<PreparedStatement> blockCode) {
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatement)) {
            blockCode.run(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL State: " + e.getSQLState());
            if (e.getSQLState().equals("23505")) throw new ExistStorageException(e);
            if (e.getSQLState().equals("24000")) throw new NotExistStorageException(e);
            throw new StorageException(e);
        }
    }

    public interface ProcessSqlRequest<T> {
        void run(T ps) throws SQLException;
    }
}
