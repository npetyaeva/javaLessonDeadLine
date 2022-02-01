package ru.netology.sql;

import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class SqlQueries {

    public static String getVerifyCode() {
        var runner = new QueryRunner();
        var verifyCodeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            String verifyCode = runner.query(conn, verifyCodeSQL, new ScalarHandler<>());
            return verifyCode;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cleanDB() {
        var runner = new QueryRunner();
        var deleteAuthCodes = "DELETE FROM auth_codes;";

        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app", "app", "pass"
                )
        ) {
            runner.update(conn, deleteAuthCodes);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
