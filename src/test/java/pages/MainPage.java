package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локатор верхней кнопки "Заказать"
    private By topOrderButton = By.xpath("//button[text()='Заказать']");

    // Локатор кнопки согласия с куки
    private By cookieButton = By.xpath("//button[text()='да все привыкли']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Метод для закрытия баннера куки (вызывать сразу после открытия страницы)
    public void closeCookieBanner() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieButton)).click();
        } catch (TimeoutException e) {
            // Если баннера нет, ничего не делаем
        }
    }

    // Метод клика по верхней кнопке "Заказать"
    public void clickTopOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(topOrderButton)).click();
    }

    // Метод клика по вопросу FAQ (передаём индекс: 0 для первого, 1 для второго и т.д.)
    public void clickFaqQuestion(int index) {
        By locator = By.id("accordion__heading-" + index);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        // Прокручиваем к элементу, чтобы он точно был виден
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    // Метод получения текста ответа FAQ
    public String getFaqAnswerText(int index) {
        By locator = By.id("accordion__panel-" + index);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }
}