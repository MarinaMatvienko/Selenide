package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    LocalDate date = LocalDate.now();
    LocalDate newDate = date.plusDays(3);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldSubmitForm() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Краснодар");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Яна Машкова");
        $("[data-test-id=phone] input").setValue("181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(newDate)));
    }

    @Test
    void shouldCityNot() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Огонь");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Яна Машкова");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=city] .input__sub").shouldBe(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void shouldNocyrillic() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Yana Senova");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=name] .input__sub").shouldBe(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldNamedash() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Анна-Мария Воронцова");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $(Selectors.withText("Успешно!")).shouldBe(Condition.appear, Duration.ofSeconds(15));
        $("[data-test-id='notification'] .notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + formatter.format(newDate)));
    }

    @Test
    void shouldPhoneError() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Анна Воронцова");
        $("[data-test-id=phone] input").setValue("89181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=phone] .input__sub").shouldBe(Condition.text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void shouldNoDate() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Артур Смолянников");
        $("[data-test-id=phone] input").setValue("+79001234567");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=date] .input__sub").shouldBe(Condition.text("Неверно введена дата"));
    }


    @Test
    void shouldNoname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=name] .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNophohe() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Яна Машкова");
        $("[data-test-id=phone] input").setValue("");
        $("[class=checkbox__box]").click();
        $("[class=button__text]").click();
        $("[data-test-id=phone] .input__sub").shouldBe(Condition.text("Поле обязательно для заполнения"));
    }


    @Test
    void shouldNoCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").sendKeys(formatter.format(newDate));
        $("[data-test-id=name] input").setValue("Оксимирон Окси");
        $("[data-test-id=phone] input").setValue("+79181102009");
        $("[class=button__text]").click();
        $("[class=checkbox__text]").shouldBe(Condition.text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}

