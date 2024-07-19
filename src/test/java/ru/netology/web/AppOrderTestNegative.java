package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Требования к содержимому полей:
 * В поле фамилии и имени разрешены только русские буквы, дефисы и пробелы.
 * В поле телефона — только 11 цифр, символ + на первом месте.
 * Флажок согласия должен быть выставлен.
 */

public class AppOrderTestNegative {
    private WebDriver driver;

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    public void shouldCollapseWhenEnteringNameEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79093548598");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void shouldCollapseWhenEnteringNameLatin() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Ivan");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79093548598");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.xpath("//span[@data-test-id='name'] " +
                        "[contains(@class,'input_invalid')]//span[@class='input__sub']")).getText().trim());
    }

    @Test
    public void shouldCollapseWhenEnteringNameNumbers() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("1234567890");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+79093548598");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                driver.findElement(By.xpath("//span[@data-test-id='name'] " +
                        "[contains(@class,'input_invalid')]//span[@class='input__sub']")).getText().trim());
    }

    @Test
    public void shouldCollapseWhenEnteringPhoneEmpty() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        assertEquals("Поле обязательно для заполнения",
                driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).isDisplayed());
    }

    @Test
    public void shouldCollapseWhenEnteringPhoneLatin() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Иван Иванов");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("ivanivanov");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.xpath("//span[@data-test-id='phone'] " +
                        "[contains(@class,'input_invalid')]//span[@class='input__sub']")).getText().trim());
    }

    @Test
    public void shouldCollapseWhenEnteringPhoneMoreLimit() {
        driver.findElement(By.xpath("//span[@data-test-id='name']//input")).sendKeys("Иван Иванов");
        driver.findElement(By.xpath("//span[@data-test-id='phone']//input")).sendKeys("+790991135866");
        driver.findElement(By.xpath("//label[@data-test-id='agreement']")).click();
        driver.findElement(By.xpath("//button[contains(@class,'button')]")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.",
                driver.findElement(By.xpath("//span[@data-test-id='phone'] " +
                        "[contains(@class,'input_invalid')]//span[@class='input__sub']")).getText().trim());
    }

    @Test
    public void shouldCollapseWhenCheckboxUnchecked() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Иван Иванов");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79093999980");
        driver.findElement(By.cssSelector("button.button")).click();
        assertTrue(driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid")).isDisplayed());
    }

}