package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory conn;

    public SqlHelper(ConnectionFactory conn) {
        this.conn = conn;
    }

    public <T> T execute(String SqlStatement, ProcessSqlRequest<T> blockCode) {
        try (Connection connection = conn.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SqlStatement)) {
            return blockCode.run(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) throw new ExistStorageException(e);
            throw new StorageException(e);
        }
    }

    public interface ProcessSqlRequest<T> {
        T run(PreparedStatement ps) throws SQLException;
    }
}
