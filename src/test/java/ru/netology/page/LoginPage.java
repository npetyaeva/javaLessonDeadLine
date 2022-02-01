package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement loginField = $("[name='login']");
    private final SelenideElement passwordField = $("[name='password']");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement closeButton = $(".notification__closer");
    private final SelenideElement errorPopupLogin = $("[data-test-id='login'] .input__sub");
    private final SelenideElement errorPopupPassword = $("[data-test-id='password'] .input__sub");
    private final SelenideElement errorPopupBlocked = $("[data-test-id='blocked'] .input__sub");

    public VerificationPage validLogin(String login, String password) {
        loginFunction(login, password);
        return new VerificationPage();
    }

    public String invalidPassword(String login, String invalidPassword) {
        for (int i = 0; i < 3; i++) {
            loginFunction(login, invalidPassword);
            closeButton.shouldBe(visible).click();
            loginField.doubleClick().sendKeys(Keys.BACK_SPACE);
            passwordField.doubleClick().sendKeys(Keys.BACK_SPACE);
        }
        loginField.setValue(login);
        passwordField.setValue(invalidPassword);
        loginButton.click();
        return errorPopupBlocked.shouldBe(visible).text();
    }

    public String emptyFields() {
        loginButton.click();
        return errorPopupLogin.shouldBe(visible).text();
    }

    public String emptyLogin(String password) {
        loginFunction("", password);
        return errorPopupLogin.shouldBe(visible).text();
    }

    public String emptyPassword(String login) {
        loginFunction(login, "");
        return errorPopupPassword.shouldBe(visible).text();
    }

    private void loginFunction(String login, String password) {
        loginField.setValue(login);
        passwordField.setValue(password);
        loginButton.click();
    }
}
