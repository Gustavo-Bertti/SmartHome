package br.com.fiap.smarthome.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User saveUser(User user) {
        return jdbcTemplate.execute((Connection connection) -> {
            try (CallableStatement callableStatement = connection.prepareCall("{call SAVE_USER(?, ?, ?, ?, ?)}")) {

                callableStatement.setString(1, user.getName());
                callableStatement.setString(2, user.getEmail());
                callableStatement.setString(3, user.getPassword());
                callableStatement.setDate(4, java.sql.Date.valueOf(user.getCreatedAt()));
                callableStatement.registerOutParameter(5, java.sql.Types.BIGINT);
                callableStatement.execute();
                user.setUserId(callableStatement.getLong(5));
                return user;
            }
        });
    }

}