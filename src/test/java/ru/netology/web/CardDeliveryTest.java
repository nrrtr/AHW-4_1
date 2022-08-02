package ru.netology.web;

import com.codeborne.selenide.Condition;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");
    private LocalDate dt = LocalDate.now().plusDays(3);
    String date = DATE_FORMATTER.format(dt);

    @BeforeEach
    public void openCardForm() {
        open("http://localhost:9999");
    }

    @Test
    void shouldPassValidation() {
        $x("//input[@placeholder='Город']").setValue("Краснодар");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $x("//*[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id='notification']").should(Condition.matchText("Успешно!"));
    }
}
