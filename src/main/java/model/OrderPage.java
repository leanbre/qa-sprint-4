package model;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class OrderPage {
    /*
     * POM класс страницы заказа самоката
     * Содержит локаторы и методы для основных действий
     * */

    // Конструктор класса основной страницы
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Драйвер для загрузки страницы
    private final WebDriver driver;

    private static final String SUCCESS_PLACED_ORDER_TEXT = "Заказ оформлен";

    // Имя
    private static final By NAME_LOCATOR = By.cssSelector("[placeholder='* Имя']");

    // Фамилия
    private static final By SURNAME_LOCATOR = By.cssSelector("[placeholder='* Фамилия']");

    // Адрес: куда привезти заказ
    private static final By DELIVERY_ADDRESS_LOCATOR = By.cssSelector("[placeholder='* Адрес: куда привезти заказ']");

    // Станция метро
    private static final By METRO_STATION_LIST_LOCATOR = By.xpath(".//input[contains(@placeholder, '* Станция метро')]");

    // Телефон: на него позвонит курьер
    private static final By PHONE_NUMBER_LOCATOR = By.cssSelector("[placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее" на форме "Для кого самокат"
    private static final By NEXT_BUTTON_LOCATOR = By.xpath(".//button[text()='Далее']");

    // Когда привезти самокат
    private static final By DELIVERY_DATE_LOCATOR = By.cssSelector("[placeholder='* Когда привезти самокат']");

    // Срок аренды
    private static final By RENT_INTERVAL_LIST_LOCATOR = By.xpath("//div[@class='Dropdown-control']");

    // Срок аренды (для упрощения оставим один интервал)
    private static final By RENT_INTERVAL_LOCATOR = By.xpath("//div[@class='Dropdown-menu']/div[4]");

    // Цвет самоката (для упрощения оставим один цвет заполнения)
    private static final By SCOOTER_COLOR_LOCATOR = By.xpath(".//label[@for='grey']");

    // Комментарий для курьера
    private static final By COMMENT_FOR_COURIER_LOCATOR = By.cssSelector("[placeholder='Комментарий для курьера']");

    // Кнопка "Заказать" в конце заполнения формы
    private static final By ORDER_BUTTON_LOCATOR = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");

    // Кнопка подтверждения заказа (Хотите заказать? Да)
    private static final By YES_BUTTON_LOCATOR = By.xpath("//div[@class='Order_Modal__YZ-d3']/div[2]/button[2]");

    // Плашка "Заказ оформлен"
    private static final By ORDER_HAS_BEEN_PLACED_WINDOW_LOCATOR = By.xpath("//*[contains(text(), 'Заказ оформлен')]");

    // Действия по локаторам
    // Заполнение имени
    public void setName(String name) {
        driver.findElement(NAME_LOCATOR).sendKeys(name);
    }

    // Заполнение фамилии
    public void setSurname(String surname) {
        driver.findElement(SURNAME_LOCATOR).sendKeys(surname);
    }

    // Заполнение адреса
    public void setDeliveryAddress(String address) {
        driver.findElement(DELIVERY_ADDRESS_LOCATOR).sendKeys(address);
    }

    // Получение локатора станции метро на основе текстового названия
    public By getMetroStationLocator(String stationName) {
        return By.xpath(".//button/div[text()='" + stationName + "']");
    }

    // Действие скроллинга до нужной станции в выпадающем списке
    public void scrollToMetroStationByLocator(By metroStationLocator) {
        WebElement metroStationInList = driver.findElement(metroStationLocator);
        scrollToPageElement(metroStationInList);
    }

    // Действие скроллинга по найденному элементу, вынесено для читабельности прочих методов
    public void scrollToPageElement(WebElement pageElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", pageElement);
    }

    // Заполнение станции метро
    public void setMetroStation(String stationName) {
        // Нажимаем на выпадающее меню
        driver.findElement(METRO_STATION_LIST_LOCATOR).click();

        // Получаем локатор нужной станции метро
        By metroStationLocator = getMetroStationLocator(stationName);

        // Скроллим список до нужной станции
        scrollToMetroStationByLocator(metroStationLocator);

        // Ставим выбранную станцию
        driver.findElement(metroStationLocator).click();
    }

    // Заполнение номера телефона
    public void setPhoneNumber(String phoneNumber) {
        driver.findElement(PHONE_NUMBER_LOCATOR).sendKeys(phoneNumber);
    }

    // Нажатие на кнопку "Далее" на форме "Для кого самокат"
    public void clickNextButton() {
        driver.findElement(NEXT_BUTTON_LOCATOR).click();
    }

    // Заполнение даты, когда привезти самокат
    public void setDeliveryDate(String deliveryDate) {
        // Нажимаем на поле
        driver.findElement(DELIVERY_DATE_LOCATOR).click();
        // Вводим дату
        driver.findElement(DELIVERY_DATE_LOCATOR).sendKeys(deliveryDate);
        // Нажимаем Enter для скрытия панели с календарем
        driver.findElement(DELIVERY_DATE_LOCATOR).sendKeys(Keys.ENTER);
    }

    // Заполнение срока аренды самоката
    public void setRentInterval() {
        // Нажимаем на выпадающий список
        driver.findElement(RENT_INTERVAL_LIST_LOCATOR).click();
        // Дожидаемся появления самого списка
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(RENT_INTERVAL_LOCATOR));
        // Нажимаем на выбранный интервал
        driver.findElement(RENT_INTERVAL_LOCATOR).click();
    }

    // Заполняем цвет самоката
    public void setScooterColor() {
        driver.findElement(SCOOTER_COLOR_LOCATOR).click();
    }

    // Заполняем комментарий для курьера
    public void setCommentForCourier(String comment) {
        driver.findElement(COMMENT_FOR_COURIER_LOCATOR).sendKeys(comment);

    }

    // Нажимаем кнопку "Заказать"
    public void clickToOrderButton() {
        driver.findElement(ORDER_BUTTON_LOCATOR).click();
    }

    // Нажимаем кнопку подтверждения заказа (Вы уверены? Да)
    public void clickToConfirmButton() {
        // Дожидаемся появления кнопки подтверждения
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(YES_BUTTON_LOCATOR));

        // Нажимаем на кнопку подтверждения
        driver.findElement(YES_BUTTON_LOCATOR).click();
    }

    public boolean orderPlacedWithSuccessStatus() {
        // Дожидаемся появления завершающего окна
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(ORDER_HAS_BEEN_PLACED_WINDOW_LOCATOR));

        // Получаем текст с окна
        String resultText = driver.findElement(ORDER_HAS_BEEN_PLACED_WINDOW_LOCATOR).getText();

        // Возвращаем результат, был ли получен статус "успех"
        return resultText.contains(SUCCESS_PLACED_ORDER_TEXT);
    }
}
