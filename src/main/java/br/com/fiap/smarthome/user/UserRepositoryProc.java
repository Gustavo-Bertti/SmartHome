package br.com.fiap.smarthome.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;

@Repository
public class UserRepositoryProc {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User saveUser(User user) {
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement callableStatement = connection.prepareCall("{call PKG_USERS.INSERT_USER(?, ?, ?)}")) {

                callableStatement.setString(1, user.getName());
                callableStatement.setString(2, user.getEmail());
                callableStatement.setString(3, user.getPassword());

                callableStatement.execute();
                return user;
            }
        });
    }

}