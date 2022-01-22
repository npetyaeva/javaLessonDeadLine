package ru.netology.sql;

import lombok.NoArgsConstructor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

@NoArgsConstructor
public class SqlQueries {

    public String getVerifyCode(String id) {
        var runner = new QueryRunner();
        var verifyCodeSQL = "SELECT code FROM auth_codes WHERE user_id='" + id + "' AND created=(SELECT MAX(created) FROM auth_codes);";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            String verifyCode = runner.query(conn, verifyCodeSQL, new ScalarHandler<>());
            return verifyCode;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

/*    public DataHelper.User getUser() {
        var runner = new QueryRunner();
        var usersSQL = "SELECT * FROM users;";
        var countSQL = "SELECT COUNT(*) FROM users;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                );
        ) {
            var first = runner.query(conn, usersSQL, new BeanHandler<>(DataHelper.User.class));
            return first;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }*/
}
