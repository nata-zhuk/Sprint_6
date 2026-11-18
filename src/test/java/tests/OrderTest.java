package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import pages.MainPage;
import pages.OrderPage;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.TimeoutException;

public class OrderTest extends BaseTest {

    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of("Иван", "Петров", "ул. Ленина 1", "Сокольники", "+79991112233", "01.01.2025"),
                Arguments.of("Анна", "Сидорова", "пр. Мира 10", "Черкизовская", "+79994445566", "02.01.2025")
        );
    }

    @ParameterizedTest
    @MethodSource("orderData")
    public void testPositiveOrderFlow(String name, String surname, String address, String metro, String phone, String date) {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        // 1. Закрываем куки
        mainPage.closeCookieBanner();

        // 2. Нажимаем кнопку "Заказать" на главной
        mainPage.clickTopOrderButton();

        // 3. Заполняем первую форму
        orderPage.fillCustomerForm(name, surname, address, phone);
        orderPage.selectMetro(metro);
        orderPage.clickNext();

        // 4. Заполняем вторую форму
        orderPage.fillRentForm(date);
        orderPage.clickOrder();

        // 5. Проверяем всплывающее окно
        try {
            String successText = orderPage.getSuccessMessageText();
            assertTrue(successText.contains("Заказ оформлен"), "Ожидался текст 'Заказ оформлен', но получено: " + successText);
        } catch (TimeoutException e) {
            // Если мы попали сюда, значит, окно не появилось. Это баг!
            // Мы пробрасываем исключение дальше, чтобы тест упал, но с понятным сообщением.
            throw new AssertionError("БАГ: Окно успешного оформления заказа не появилось! " +
                    "Скорее всего, это известный баг в Chrome, из-за которого кнопка 'Да' не срабатывает.", e);
        }
    }
}