package model;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    /*
    * POM класс основной страницы
    * Содержит локаторы и методы для основных действий
    * */

    // Конструктор класса основной страницы
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    // Драйвер для загрузки страницы
    private final WebDriver driver;

    // Базовая ссылка на основную страницу
    public static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    // Префикс ID элемента стрелки из секции "Вопросы о важном"
    public static final String IMPORTANT_QUESTION_ARROW_PREFIX = "accordion__heading-";

    // Префикс ID элемента текста из секции "Вопросы о важном"
    public static final String IMPORTANT_ANSWER_PREFIX = "accordion__panel-";

    // Локатор секции "Вопросы о важном"
    public static final By IMPORTANT_QUESTIONS_SECTION_LOCATOR = By.xpath(".//div[text()='Вопросы о важном']");

    // Локатор кнопки скрытия информации о куках
    private final By COOKIE_BUTTON = By.id("rcc-confirm-button");

    // Локатор кнопки "Заказать" на верху страницы
    private static final By ORDER_BUTTON_HEADER_LOCATOR = By.xpath(".//button[@class='Button_Button__ra12g']");

    // Локатор кнопки "Заказать" в низу страницы
    private static final By ORDER_BUTTON_BOTTOM_LOCATOR = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    // Действие открытия основной страницы
    public void openPage() {
        driver.get(BASE_URL);
    }

    // Действие скроллинга до секции "Вопросы о важном"
    public void scrollToImportantQuestionsSection() {
        WebElement importantQuestionsSection = driver.findElement(IMPORTANT_QUESTIONS_SECTION_LOCATOR);
        scrollToPageElement(importantQuestionsSection);
    }

    // Действие скроллинга по найденному элементу, вынесено для читабельности прочих методов
    public void scrollToPageElement(WebElement pageElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", pageElement);
    }

    // Действие нажатия на кнопку раскрытия вопроса в секции "Вопросы о важном" (зависит от порядка вопросов)
    public void clickToImportantQuestionArrow(int questionNumber) {
        // Получаем локатор с учетом индекса вопроса
        By importantQuestionArrowLocator = By.id(IMPORTANT_QUESTION_ARROW_PREFIX + questionNumber);
        // Нажимаем на элемент стрелочки для показа вопроса
        driver.findElement(importantQuestionArrowLocator).click();
    }

    // Дождаться появления текста ответа в секции "Вопросы о важном" после нажатия на стрелочку
    public void waitForImportantAnswerText(int questionNumber) {
        new WebDriverWait(driver, 5)
                .until(ExpectedConditions.visibilityOfElementLocated(By.id(IMPORTANT_ANSWER_PREFIX + questionNumber)));
    }

    // Получить текст ответа из секции "Вопросы о важном" по id вопроса
    public String getImportantAnswerText(int questionNumber) {
        By importantAnswerLocator = By.id(IMPORTANT_ANSWER_PREFIX + questionNumber);
        return driver.findElement(importantAnswerLocator).getText();
    }

    // Действие нажатия на кнопку "Заказать" на верху страницы
    public void clickToOrderButtonHeader() {
        driver.findElement(ORDER_BUTTON_HEADER_LOCATOR).click();
    }

    // Действие нажатия на кнопку "Заказать" в низу страницы
    public void clickToOrderButtonBottom() {
        driver.findElement(ORDER_BUTTON_BOTTOM_LOCATOR).click();
    }

    // Действие скроллинга до кнопки "Заказать" в низу страницы
    public void scrollToBottomOrderButton() {
        WebElement orderButtonBottom = driver.findElement(ORDER_BUTTON_BOTTOM_LOCATOR);
        scrollToPageElement(orderButtonBottom);
    }

    // Действие скрытия плашки об использовании кук, она загораживает целевую кнопку
    public void clickToHideCookiesButton() {
        driver.findElement(COOKIE_BUTTON).click();
    }
}
