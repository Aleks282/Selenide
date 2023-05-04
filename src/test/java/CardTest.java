import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.conditions.Visible;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CardTest {
    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
    String planningDate = generateDate(3);

    @Test
    void testDelivery() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $("input[placeholder=\"Город\"]").setValue("Москва");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder=\"Дата встречи\"]").setValue(planningDate);
        $("[name=\"name\"]").setValue("Салтыков-Щедрин Михаил");
        $("[name=\"phone\"]").setValue("+7950333333");
        $("[data-test-id=\"agreement\"]").click();
        $x("//span[text()=\"Забронировать\"]").click();
        $("[data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);


    }
}