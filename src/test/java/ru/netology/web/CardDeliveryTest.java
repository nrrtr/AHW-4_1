package ru.netology.web;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    private Random random = new Random();
    private String date = generateDate(3);

    public String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

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
        $(".button [class='button__text']").click();
        $x("//*[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id='notification']").should(Condition.matchText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition
                .text("Встреча успешно забронирована на " + date));

    }

    @Test
    void shouldShowNotNullMessageWithCityField() {
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $("[data-test-id='city'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowNotNullMessageWithDateField() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//input[@name='name']").setValue("Мамин Дмитрий-Сибиряк");
        $x("//input[@name='phone']").setValue("+75678901234");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Неверно введена дата"));
    }

    @Test
    void shouldShowNotNullMessageWithNameField() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='phone']").setValue("+77890561234");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldShowNotNullMessageWithPhoneField() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Сибиряк Дмитрий-Мамин");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $("[data-test-id='phone'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldColorRedWithUncheckedCheckbox() {
        $x("//input[@placeholder='Город']").setValue("Великий Новгород");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Сибиряк Дмитрий-Мамин");
        $x("//input[@name='phone']").setValue("+75612347890");
        $(".button [class='button__text']").click();
        $("[data-test-id='agreement'].input_invalid").shouldBe(visible)
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void shouldShowWrongCityMessage() {
        $x("//input[@placeholder='Город']").setValue("Норильск");
        $x("//*[@placeholder='Дата встречи']").doubleClick();
        $x("//*[@placeholder='Дата встречи']").sendKeys(Keys.BACK_SPACE);
        $x("//*[@placeholder='Дата встречи']").setValue(date);
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $("[data-test-id='city'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
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
        $(".button [class='button__text']").click();
        $("[data-test-id='date'] .input__sub").shouldBe(visible).shouldHave(exactText("Неверно введена дата"));
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
        $(".button [class='button__text']").click();
        $("[data-test-id='name'] .input__sub").shouldBe(visible).shouldHave(
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
        $(".button [class='button__text']").click();
        $("[data-test-id='phone'] .input__sub").shouldBe(visible)
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldPassValidationWithPopupCitiesListAndCalendarWidget() {
        int i = 7;
        String day = String.valueOf(LocalDate.now().plusDays(i).getDayOfMonth());
        $x("//input[@placeholder='Город']").setValue("Кр");
        $$(".menu-item__control").get(random.nextInt(14)).click();
        $x("//*[@id='root']/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        if (LocalDate.now().plusDays(i).getMonthValue() > LocalDate.now().plusDays(3).getMonthValue()) {
            $x("//*[@class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        $$("td.calendar__day").find(exactText(day)).click();
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $x("//*[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id='notification']").should(Condition.matchText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition
                .text("Встреча успешно забронирована на " + generateDate(i)));
    }

    @Test
    void shouldPassValidationWithPopupCitiesListAndCalendarWidget2() {
        int i = 30;
        String day = String.valueOf(LocalDate.now().plusDays(i).getDayOfMonth());
        $x("//input[@placeholder='Город']").setValue("Кр");
        $$(".menu-item__control").get(random.nextInt(14)).click();
        $x("//*[@id='root']/div/form/fieldset/div[2]/span/span/span/span/span[1]/span/button").click();
        if (LocalDate.now().plusDays(i).getMonthValue() > LocalDate.now().plusDays(3).getMonthValue()) {
            $x("//*[@class='calendar__arrow calendar__arrow_direction_right']").click();
        }
        $$("td.calendar__day").find(exactText(day)).click();
        $x("//input[@name='name']").setValue("Дмитрий Мамин-Сибиряк");
        $x("//input[@name='phone']").setValue("+79012345678");
        $x("//span[@class='checkbox__box']").click();
        $(".button [class='button__text']").click();
        $x("//*[@data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $x("//*[@data-test-id='notification']").should(Condition.matchText("Успешно!"));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition
                .text("Встреча успешно забронирована на " + generateDate(i)));
    }
}
