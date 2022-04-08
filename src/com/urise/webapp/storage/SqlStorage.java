package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.urise.webapp.storage.AbstractStorage.COMPARATOR_FULLNAME_THEN_UUID;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        SqlHelper.execute(connectionFactory, "DELETE FROM resume", PreparedStatement::execute);
        Storage.super.clear();
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        SqlHelper.execute(connectionFactory, "UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(r.getUuid());
        });
        Storage.super.update(r);
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r.getUuid());
        SqlHelper.execute(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            Storage.super.save(r);
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        final Resume[] resume = new Resume[1];
        SqlHelper.execute(connectionFactory, "SELECT * FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            resume[0] = new Resume(uuid, rs.getString("full_name"));
        });
        return resume[0];
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        SqlHelper.execute(connectionFactory, "DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
        });
        Storage.super.delete(uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = new ArrayList<>();
        SqlHelper.execute(connectionFactory, "SELECT * FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String uuid = rs.getString("uuid").trim();
                String fullName = rs.getString("full_name");
                list.add(new Resume(uuid, fullName));
            }
        });
        list.sort(COMPARATOR_FULLNAME_THEN_UUID);
        return list;
    }

    @Override
    public int size() {
        LOG.info("size");
        AtomicInteger size = new AtomicInteger();
        SqlHelper.execute(connectionFactory, "SELECT COUNT (*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            size.set(rs.getInt(1));
        });
        return size.get();
    }
}