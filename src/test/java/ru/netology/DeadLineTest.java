package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.sql.SqlQueries.cleanDB;
import static ru.netology.sql.SqlQueries.getVerifyCode;

public class DeadLineTest {

    // 1. docker-compose up
    // 2. java -jar .\app-deadline.jar -P:jdbc:mysqldb://localhost:3306/app -P:jdbc:user:app -P:jdbc:password:pass
    // 3. SQL Client mysqldb, db - app, user - app, password - pass

    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999/");
    }


    @AfterEach
    void finalAction() {
        cleanDB();
    }

    @Test
    void shouldCheckValidLogin() {
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(userInfo.getLogin(), userInfo.getPassword());
        var verifyCode = getVerifyCode();
        var dashboardPage = verifyPage.validVerify(verifyCode);
        assertEquals("Личный кабинет", dashboardPage.loadDashboardPage().trim());
    }

    @Test
    void shouldCheckBlockedAfterEnteredThreeTimesInvalidPassword() {
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var text = loginPage.invalidPassword(userInfo.getLogin(), userInfo.getPassword() + "0");
        assertEquals("Ошибка! Превышено допустимое количество попыток ввода пароля. Попробуйте авторизоваться позже.", text);
    }

    @Test
    void shouldCheckInvalidVerifyCode() {
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(userInfo.getLogin(), userInfo.getPassword());
        var text = verifyPage.inValidVerify("00000");
        assertEquals("Ошибка! Неверно указан код! Попробуйте ещё раз.", text.trim());
    }

    @Test
    void shouldCheckEmptyVerifyCode() {
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(userInfo.getLogin(), userInfo.getPassword());
        var text = verifyPage.emptyVerify();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldCheckEmptyLoginFields() {
        var loginPage = new LoginPage();
        var text = loginPage.emptyFields();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldCheckEmptyLogin() {
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var text = loginPage.emptyLogin(userInfo.getPassword());
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldCheckEmptyPassword() {
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var text = loginPage.emptyPassword(userInfo.getLogin());
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
}
