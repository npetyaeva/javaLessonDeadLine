package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.LoginPage;
import ru.netology.sql.SqlQueries;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadLineTest {
    @BeforeEach
    void setUp() {
        Selenide.open("http://localhost:9999/");
    }

    @Test
    void shouldCheckValidLogin() {
        var sql = new SqlQueries();
        var userInfo = DataHelper.getUserInfo();
        var loginPage = new LoginPage();
        var verifyPage = loginPage.validLogin(userInfo.getLogin(), userInfo.getPassword());
        var verifyCode = sql.getVerifyCode(userInfo.getId());
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
        assertEquals("Ошибка! Превышено количество попыток ввода кода!", text.trim());
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
