package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import static com.urise.webapp.storage.AbstractStorage.COMPARATOR_FULLNAME_THEN_UUID;

public class SqlStorage implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
    private final ConnectionFactory connectionFactory;
    private final SqlHelper sqlHelper;

    public SqlStorage(ConnectionFactory sqlStorage) {
        connectionFactory = sqlStorage;
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        LOG.info("Clear");
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume r) {
        LOG.info("Update " + r);
        sqlHelper.execute("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(r.getUuid());
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("Save " + r.getUuid());
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.executeUpdate();
        });
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        final Resume[] resume = new Resume[1];
        sqlHelper.execute("SELECT * FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new NotExistStorageException(uuid);
            resume[0] = new Resume(uuid, rs.getString("full_name"));
        });
        return resume[0];
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        sqlHelper.execute("DELETE FROM resume r WHERE r.uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) throw new NotExistStorageException(uuid);
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = new ArrayList<>();
        sqlHelper.execute("SELECT * FROM resume", ps -> {
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
        sqlHelper.execute("SELECT COUNT (*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            size.set(rs.getInt(1));
        });
        return size.get();
    }
}