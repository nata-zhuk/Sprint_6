package tests;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import pages.MainPage;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FaqTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "0, Сутки — 400 рублей. Оплата курьеру — наличными или картой.",
            "1, Пока что у нас так: один заказ — один самокат.",
            "2, Допустим, вы оформляете заказ на 8 мая.",
            "3, Только начиная с завтрашнего дня.",
            "4, Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.",
            "5, Самокат приезжает к вам с полной зарядкой.",
            "6, Да, пока самокат не привезли. Штрафа не будет.",
            "7, Да, обязательно. Всем самокатов! И Москве, и Московской области."
    })
    public void testFaqAccordion(int index, String expectedText) {
        MainPage mainPage = new MainPage(driver);

        // 1. Закрываем куки
        mainPage.closeCookieBanner();

        // 2. Кликаем по вопросу
        mainPage.clickFaqQuestion(index);

        // 3. Получаем текст ответа
        String actualText = mainPage.getFaqAnswerText(index);

        // 4. Проверяем, что текст содержит ожидаемую фразу
        assertTrue(actualText.contains(expectedText),
                "Текст ответа не совпадает. Ожидалось: " + expectedText + ", а получено: " + actualText);
    }
}