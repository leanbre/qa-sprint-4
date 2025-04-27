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

    // Базовая ссылка на основную страницу
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    public static final String SUCCESS_PLACED_ORDER_TEXT = "Заказ оформлен";

    // Локатор кнопки скрытия информации о куках
    private final By cookieButton = By.id("rcc-confirm-button");

    // Локатор кнопки "Заказать" на верху страницы
    public static final By orderButtonHeaderLocator = By.xpath(".//button[@class='Button_Button__ra12g']");

    // Локатор кнопки "Заказать" в низу страницы
    public static final By orderButtonBottomLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Имя
    public static final By nameLocator = By.cssSelector("[placeholder='* Имя']");

    // Фамилия
    public static final By surnameLocator = By.cssSelector("[placeholder='* Фамилия']");

    // Адрес: куда привезти заказ
    public static final By deliveryAddressLocator = By.cssSelector("[placeholder='* Адрес: куда привезти заказ']");

    // Станция метро
    public static final By metroStationListLocator = By.xpath(".//input[contains(@placeholder, '* Станция метро')]");

    // Телефон: на него позвонит курьер
    public static final By phoneNumberLocator = By.cssSelector("[placeholder='* Телефон: на него позвонит курьер']");

    // Кнопка "Далее" на форме "Для кого самокат"
    public static final By nextButtonLocator = By.xpath(".//button[text()='Далее']");

    // Когда привезти самокат
    public static final By deliveryDateLocator = By.cssSelector("[placeholder='* Когда привезти самокат']");

    // Срок аренды
    public static final By rentIntervalListLocator = By.xpath("//div[@class='Dropdown-control']");

    // Срок аренды (для упрощения оставим один интервал)
    public static final By rentIntervalLocator = By.xpath("//div[@class='Dropdown-menu']/div[4]");

    // Цвет самоката (для упрощения оставим один цвет заполнения)
    public static final By scooterColorLocator = By.xpath(".//label[@for='grey']");

    // Комментарий для курьера
    public static final By commentForCourierLocator = By.cssSelector("[placeholder='Комментарий для курьера']");

    // Кнопка "Заказать" в конце заполнения формы
    public static final By orderButtonLocator = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Заказать']");

    // Кнопка подтверждения заказа (Хотите заказать? Да)
    public static final By yesButtonLocator = By.xpath("//div[@class='Order_Modal__YZ-d3']/div[2]/button[2]");

    // Плашка "Заказ оформлен"
    public static final By orderHasBeenPlacedWindowLocator = By.xpath("//*[contains(text(), 'Заказ оформлен')]");

    // Действия по локаторам
    // Заполнение имени
    public void setName(String name) {
        driver.findElement(nameLocator).sendKeys(name);
    }

    // Заполнение фамилии
    public void setSurname(String surname) {
        driver.findElement(surnameLocator).sendKeys(surname);
    }

    // Заполнение адреса
    public void setDeliveryAddress(String address) {
        driver.findElement(deliveryAddressLocator).sendKeys(address);
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
        driver.findElement(metroStationListLocator).click();

        // Получаем локатор нужной станции метро
        By metroStationLocator = getMetroStationLocator(stationName);

        // Скроллим список до нужной станции
        scrollToMetroStationByLocator(metroStationLocator);

        // Ставим выбранную станцию
        driver.findElement(metroStationLocator).click();
    }

    // Заполнение номера телефона
    public void setPhoneNumber(String phoneNumber) {
        driver.findElement(phoneNumberLocator).sendKeys(phoneNumber);
    }

    // Нажатие на кнопку "Далее" на форме "Для кого самокат"
    public void clickNextButton() {
        driver.findElement(nextButtonLocator).click();
    }

    // Заполнение даты, когда привезти самокат
    public void setDeliveryDate(String deliveryDate) {
        // Нажимаем на поле
        driver.findElement(deliveryDateLocator).click();
        // Вводим дату
        driver.findElement(deliveryDateLocator).sendKeys(deliveryDate);
        // Нажимаем Enter для скрытия панели с календарем
        driver.findElement(deliveryDateLocator).sendKeys(Keys.ENTER);
    }

    // Заполнение срока аренды самоката
    public void setRentInterval() {
        // Нажимаем на выпадающий список
        driver.findElement(rentIntervalListLocator).click();
        // Дожидаемся появления самого списка
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(rentIntervalLocator));
        // Нажимаем на выбранный интервал
        driver.findElement(rentIntervalLocator).click();
    }

    // Заполняем цвет самоката
    public void setScooterColor() {
        driver.findElement(scooterColorLocator).click();
    }

    // Заполняем комментарий для курьера
    public void setCommentForCourier(String comment) {
        driver.findElement(commentForCourierLocator).sendKeys(comment);

    }

    // Нажимаем кнопку "Заказать"
    public void clickToOrderButton() {
        driver.findElement(orderButtonLocator).click();
    }

    // Нажимаем кнопку подтверждения заказа (Вы уверены? Да)
    public void clickToConfirmButton() {
        // Дожидаемся появления кнопки подтверждения
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(yesButtonLocator));

        // Нажимаем на кнопку подтверждения
        driver.findElement(yesButtonLocator).click();
    }

    public boolean orderPlacedWithSuccessStatus() {
        // Дожидаемся появления завершающего окна
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(orderHasBeenPlacedWindowLocator));

        // Получаем текст с окна
        String resultText = driver.findElement(orderHasBeenPlacedWindowLocator).getText();

        // Возвращаем результат, был ли получен статус "успех"
        return resultText.contains(SUCCESS_PLACED_ORDER_TEXT);
    }

    // Действие нажатия на кнопку "Заказать" на верху страницы
    public void clickToOrderButtonHeader() {
        driver.findElement(orderButtonHeaderLocator).click();
    }

    // Действие нажатия на кнопку "Заказать" в низу страницы
    public void clickToOrderButtonBottom() {
        driver.findElement(orderButtonBottomLocator).click();
    }

    // Действие скроллинга до кнопки "Заказать" в низу страницы
    public void scrollToBottomOrderButton() {
        WebElement orderButtonBottom = driver.findElement(orderButtonBottomLocator);
        scrollToPageElement(orderButtonBottom);
    }

    // Действие скрытия плашки об использовании кук, она загораживает целевую кнопку
    public void clickToHideCookiesButton() {
        driver.findElement(cookieButton).click();
    }

    public void openPage() {
        driver.get(BASE_URL);
    }
}
