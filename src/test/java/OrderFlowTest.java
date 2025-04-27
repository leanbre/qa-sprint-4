import io.github.bonigarcia.wdm.WebDriverManager;
import model.OrderPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class OrderFlowTest {

    private WebDriver driver;
    private final String name;
    private final String surname;
    private final String deliveryAddress;
    private final String station;
    private final String phoneNumber;
    private final String deliveryDate;
    private final String comment;

    public OrderFlowTest(
            String name,
            String surname,
            String deliveryAddress,
            String station,
            String phoneNumber,
            String deliveryDate,
            String comment
    ) {
        this.name = name;
        this.surname = surname;
        this.deliveryAddress = deliveryAddress;
        this.station = station;
        this.phoneNumber = phoneNumber;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
    }


    public void beforeTestChromeSetup() {
        /*
            Комментарий для ревьюера: найден баг для браузера Chrome, финальная кнопка подтверждения заказа
            не  кликабельна. Если в качестве метода аннотации @Before использовать Chrome, то тесты будут падать.
            Для Firefox такой проблемы нет. Обе реализации сетапов оставлены для отображения проведенной работы.
         */
        // Запускаем драйвер до теста здесь, чтобы сделать сам код теста чище
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @Before
    public void beforeTestFireFoxSetup() {
        // Запускаем драйвер до теста здесь, чтобы сделать сам код теста чище
        WebDriverManager.firefoxdriver().clearDriverCache().setup();
        driver = new FirefoxDriver();
    }

    @Parameterized.Parameters()
    public static Object[][] usersInputs(){
        return new Object[][]{
                {"Тестов", "Тест", "Москва, ул. Тестовая, пристройка тестовая", "Белорусская", "89121231234", "01.01.2025", "eng comment"},
                {"Кириллов", "Кирилл", "Москва, ул. 2-го Регресса", "Партизанская", "89987654321", "03.03.2025", "Какой-то комментарий"},
        };
    }

    @Test
    public void checkFlowWithHeaderOrderButton() {
        // Открываем страницу заказа
        OrderPage orderPage = new OrderPage(driver);
        orderPage.openPage();
        // Нажимаем кнопку "Заказать" на верху страницы
        orderPage.clickToOrderButtonHeader();
        // Заполняем данные на первом экране
        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setDeliveryAddress(deliveryAddress);
        orderPage.setMetroStation(station);
        orderPage.setPhoneNumber(phoneNumber);
        // Скрываем уведомление о куках, чтобы без ошибок нажать на кнопку "Далее"
        orderPage.clickToHideCookiesButton();
        // Данные заполнены, нажимаем "Далее"
        orderPage.clickNextButton();
        // Заполняем второй экран с данными об аренде
        orderPage.setDeliveryDate(deliveryDate);
        orderPage.setRentInterval();
        orderPage.setScooterColor();
        orderPage.setCommentForCourier(comment);
        // Данные заполнены, нажимаем "Заказать"
        orderPage.clickToOrderButton();
        // В окне "Хотите оформить заказ?" нажимаем "Да"
        orderPage.clickToConfirmButton();
        // Проверяем, что получили статус "Успех" по заказу
        assertTrue(orderPage.orderPlacedWithSuccessStatus());
    }

    @Test
    public void checkFlowWithBottomOrderButton() {
        // Открываем страницу заказа
        OrderPage orderPage = new OrderPage(driver);
        orderPage.openPage();
        // Скроллим до кнопки "Заказать" в низу страницы
        orderPage.scrollToBottomOrderButton();
        // Нажимаем кнопку "Заказать после скролла
        orderPage.clickToOrderButtonBottom();
        // Заполняем данные на первом экране
        orderPage.setName(name);
        orderPage.setSurname(surname);
        orderPage.setDeliveryAddress(deliveryAddress);
        orderPage.setMetroStation(station);
        orderPage.setPhoneNumber(phoneNumber);
        // Скрываем уведомление о куках, чтобы без ошибок нажать на кнопку "Далее"
        orderPage.clickToHideCookiesButton();
        // Данные заполнены, нажимаем "Далее"
        orderPage.clickNextButton();
        // Заполняем второй экран с данными об аренде
        orderPage.setDeliveryDate(deliveryDate);
        orderPage.setRentInterval();
        orderPage.setScooterColor();
        orderPage.setCommentForCourier(comment);
        // Данные заполнены, нажимаем "Заказать"
        orderPage.clickToOrderButton();
        // В окне "Хотите оформить заказ?" нажимаем "Да"
        orderPage.clickToConfirmButton();
        // Проверяем, что получили статус "Успех" по заказу
        assertTrue(orderPage.orderPlacedWithSuccessStatus());
    }

    @After
    public void teardown() {
        driver.quit();
    }
}
