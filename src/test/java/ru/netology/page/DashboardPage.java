package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {
    private final SelenideElement headerDashboardPage = $(byText("Личный кабинет"));

    public DashboardPage() {
        headerDashboardPage.shouldBe(visible);
    }

    public String loadDashboardPage() {
        return headerDashboardPage.text();
    }
}
