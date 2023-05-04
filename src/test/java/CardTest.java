package ru.netology.web;

import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {
    @Test
    public void shouldSentForm() {
        open("http://localhost:9999/");
        $("[data-test-id = city] input").setValue("Москва");

        Calendar date = Calendar.getInstance();
        date.add(Calendar.DATE, 19);
        String formattedDate = new SimpleDateFormat("dd.MM.yyyy").format(date.getTime());
        $("[data-test-id = date] input").doubleClick().sendKeys(" ");
        $("[data-test-id = date] input").setValue(formattedDate);

        $("[data-test-id = name] input").setValue("Иванов Иван");
        $("[data-test-id = phone] input").setValue("+79508215070");
        $("[data-test-id = agreement]").click();
        $$("form button").last().click();

        String expected = "Встреча успешно забронирована на " + formattedDate;
        $("[data-test-id = notification]").shouldBe(visible, Duration.ofSeconds(15)).shouldHave(text(expected));

    }
}