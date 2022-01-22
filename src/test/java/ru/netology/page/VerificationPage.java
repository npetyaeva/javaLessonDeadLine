package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement codeField = $("[name='code']");
    private final SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private final SelenideElement errorPopupInvalid = $("[data-test-id='error-notification'] .notification__content");
    private final SelenideElement errorPopupEmpty = $("[data-test-id='code'] .input__sub");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
        return new DashboardPage();
    }

    public String inValidVerify(String code) {
        codeField.setValue(code);
        verifyButton.click();
        return errorPopupInvalid.shouldBe(visible).text();
    }

    public String emptyVerify() {
        verifyButton.click();
        return errorPopupEmpty.shouldBe(visible).text();
    }
}
