package ru.netology.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {
    @BeforeEach
    public void openCardForm() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldPassValidation() {
        $("[data-test-id='name'] input").setValue("Дмитрий Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button.button_view_extra").click();
        $("[data-test-id='order-success'].paragraph").shouldHave(
                exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void shouldShowNotNullMessageWithNameField() {
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button.button_view_extra").click();
        $("[data-test-id='name'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    public void shouldShowNotNullMessageWithPhoneField() {
        $("[data-test-id='name'] input").setValue("Николай Римский-Корсаков");
        $("[data-test-id='agreement']").click();
        $(".button.button_view_extra").click();
        $("[data-test-id='phone'] .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }


    @Test
    public void shouldShowAlertMessageForNameField() {
        $("[data-test-id='name'] input").setValue("asd");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $("[data-test-id='agreement']").click();
        $(".button.button_view_extra").click();
        $("[data-test-id='name'].input_invalid .input__sub").shouldHave(
                exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void shouldShowAlertMessageForPhoneField() {
        $("[data-test-id='name'] input").setValue("Николай Римский-Корсаков");
        $("[data-test-id='phone'] input").setValue("+99999999999999");
        $("[data-test-id='agreement']").click();
        $(".button.button_view_extra").click();
        $("[data-test-id='phone'].input_invalid .input__sub").shouldHave(
                exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void shouldCheckRedTextColorIfCheckboxUnchecked() {
        open("http://localhost:9999");
        $("[data-test-id='name'] input").setValue("Дмитрий Мамин-Сибиряк");
        $("[data-test-id='phone'] input").setValue("+79012345678");
        $(".button.button_view_extra").click();
        $("[data-test-id='agreement'].input_invalid").shouldHave(exactText("Я соглашаюсь с условиями обработки" +
                " и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
