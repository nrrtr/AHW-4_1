package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private LocalDate dt = LocalDate.now().plusDays(3);
    private String date = DATE_FORMATTER.format(dt);

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

    //not null suite

    @Test
    void shouldShowNotNullMessageWithCityField() {
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(0).shouldBe(visible);
        $$("[class='input__sub']").get(0).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowNotNullMessageWithDateField() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//input[@name='name']").setValue("Мамин Дмитрий-Сибиряк");
        $x("//input[@name='phone']").setValue("+75678901234");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(1).shouldBe(visible);
        $$("[class='input__sub']").get(1).shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldShowNotNullMessageWithNameField() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='phone']").setValue("+77890561234");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(2).shouldBe(visible);
        $$("[class='input__sub']").get(2).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowNotNullMessageWithPhoneField() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Сибиряк Дмитрий-Мамин");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(3).shouldBe(visible);
        $$("[class='input__sub']").get(3).shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldColorRedWithUncheckedCheckbox() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Сибиряк Дмитрий-Мамин");
        $x("//input[@name='phone']").setValue("+75612347890");
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible);
        $("[data-test-id='agreement'].input_invalid").shouldHave(
                exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    //wrong suite

    @Test
    void shouldShowWrongCityMessage() {
        $x("//input[@placeholder='Город']").setValue("Норильск");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(0).shouldBe(visible);
        $$("[class='input__sub']").get(0).shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldShowWrongDateMessage() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue("12345678");
        $x("//input[@name='name']").setValue("Мамин Дмитрий-Сибиряк");
        $x("//input[@name='phone']").setValue("+75678901234");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(1).shouldBe(visible);
        $$("[class='input__sub']").get(1).shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldShowWrongNameMessage() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("qwerty");
        $x("//input[@name='phone']").setValue("+75612347890");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(2).shouldBe(visible);
        $$("[class='input__sub']").get(2).shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldShowWrongPhoneNumberMessage() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Сибиряк Дмитрий-Мамин");
        $x("//input[@name='phone']").setValue("99999999999999");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $$("[class='input__sub']").get(3).shouldBe(visible);
        $$("[class='input__sub']").get(3).shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    //лепим костыли хД
    private int rng = (int) Math.random() * 15;
    LocalDate currentDate = LocalDate.now();
    LocalDate datePlusWeek = LocalDate.now().plusWeeks(1);
    private String day = String.valueOf(datePlusWeek.getDayOfMonth());

    @Test
    void shouldPassValidationWithPopupCitiesListAndCalendarWidget() {
        $x("//input[@placeholder='Город']").setValue("Кр");
        $$(".menu-item__control").get(rng).click();
        $x("//*[@id='root']/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        if (datePlusWeek.getMonthValue() > currentDate.getMonthValue()) {
            $x("//*[@class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        $$("td.calendar__day").find(exactText(day)).click();
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $x("//*[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id='notification']").should(Condition.matchText("Успешно!"));
    }

    LocalDate datePlusMonth = LocalDate.now().plusMonths(1);
    private String day2 = String.valueOf(datePlusMonth.getDayOfMonth());

    @Test
    void shouldPassValidationWithPopupCitiesListAndCalendarWidget2() {
        $x("//input[@placeholder='Город']").setValue("Кр");
        $$(".menu-item__control").get(rng).click();
        $x("//*[@id='root']/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        if (datePlusMonth.getMonthValue() > currentDate.getMonthValue()) {
            $x("//*[@class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        $$("td.calendar__day").find(exactText(day2)).click();
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $x("//button[@class='button button_view_extra button_size_m button_theme_alfa-on-white']").click();
        $x("//*[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id='notification']").should(Condition.matchText("Успешно!"));
    }
}
