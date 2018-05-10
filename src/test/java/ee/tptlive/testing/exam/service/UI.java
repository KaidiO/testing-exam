package ee.tptlive.testing.exam.service;

import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UI {
    @Test
    public void userCanLoginByCar_name() {
        // given
        open("http://localhost:8082/carList.jsf");

        // when
        $(By.linkText("3")).click();

        // then
        $("#form").shouldHave(text("Brand:\tLexus"));
    }
}
