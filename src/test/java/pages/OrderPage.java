package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // --- ЛОКАТОРЫ ФОРМЫ "ДЛЯ КОГО САМОКАТ" ---
    private By nameField = By.xpath("//input[@placeholder='* Имя']");
    private By surnameField = By.xpath("//input[@placeholder='* Фамилия']");
    private By addressField = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private By metroField = By.xpath("//input[@placeholder='* Станция метро']");
    private By phoneField = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private By nextButton = By.xpath("//button[text()='Далее']");

    // --- ЛОКАТОРЫ ФОРМЫ "ПРО АРЕНДУ" ---
    private By dateField = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private By rentalPeriodField = By.className("Dropdown-placeholder");
    private By orderButton = By.xpath("//div[contains(@class, 'Order_Button')]//button[text()='Заказать']");

    // --- ВСПЛЫВАЮЩЕЕ ОКНО ---
    private By successMessage = By.xpath("//div[contains(text(), 'Заказ оформлен')]");

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Заполнение полей "Имя", "Фамилия", "Адрес", "Телефон"
    public void fillCustomerForm(String name, String surname, String address, String phone) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(nameField)).sendKeys(name);
        driver.findElement(surnameField).sendKeys(surname);
        driver.findElement(addressField).sendKeys(address);
        driver.findElement(phoneField).sendKeys(phone);
    }

    // Выбор станции метро
    public void selectMetro(String metroName) {
        // 1. Кликаем по полю ввода, чтобы открыть список
        WebElement metroInput = wait.until(ExpectedConditions.elementToBeClickable(metroField));
        metroInput.click();

        // 2. Вводим название станции
        metroInput.sendKeys(metroName);

        // 3. Ждём, пока в DOM появится элемент, содержащий нужный текст.
        // Используем //div[text()='...'] без привязки к классу select-search__option.
        // Это сработает, если текст лежит прямо в div.
        By metroOption = By.xpath("//div[text()='" + metroName + "']");

        // 4. Ждём и кликаем
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(metroOption));
        option.click();
    }

    // Клик по кнопке "Далее"
    public void clickNext() {
        driver.findElement(nextButton).click();
    }

    // Заполнение формы "Про аренду" (дата)
    public void fillRentForm(String date) {
        // Вводим дату
        wait.until(ExpectedConditions.visibilityOfElementLocated(dateField)).sendKeys(date);
        // Нажимаем Enter, чтобы выбрать дату из календаря
        driver.findElement(dateField).sendKeys(Keys.ENTER);

        // Выбираем срок аренды (например, "сутки")
        driver.findElement(rentalPeriodField).click();
        By periodOption = By.xpath("//div[text()='сутки']");
        wait.until(ExpectedConditions.elementToBeClickable(periodOption)).click();
    }

    // Клик по кнопке "Заказать" в конце формы
    public void clickOrder() {
        driver.findElement(orderButton).click();

        // Ждем появления модального окна с подтверждением и кликаем "Да"
        By confirmButton = By.xpath("//button[text()='Да']");
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }

    // Получение текста из всплывающего окна
    public String getSuccessMessageText() {
        // Увеличиваем тайм-аут для этого элемента, так как окно может появляться с задержкой
        WebDriverWait longerWait = new WebDriverWait(driver, Duration.ofSeconds(15));
        return longerWait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }


}