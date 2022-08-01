package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTest {
    private WebDriver driver;

    @BeforeAll
    public static void setUpDriverPath() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldPassValidation() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Дмитрий Мамин-Сибиряк");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button.button_view_extra")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success'].paragraph")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldShowNotNullMessageWithNameField() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button.button_view_extra")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }

    @Test
    public void shouldShowNotNullMessageWithPhoneField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Николай Римский-Корсаков");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button.button_view_extra")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actual);
    }


    @Test
    public void shouldShowAlertMessageForNameField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("asd");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button.button_view_extra")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actual.trim());
    }

    @Test
    public void shouldShowAlertMessageForPhoneField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Петр Петров-Водкин");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+99999999999999");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector(".button.button_view_extra")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actual.trim());
    }

    @Test
    public void shouldCheckRedTextColorIfCheckboxUnchecked() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Фыв");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector(".button.button_view_extra")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid")).getText();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных " +
                "и разрешаю сделать запрос в бюро кредитных историй";
        assertEquals(expected, actual.trim());
    }
}
