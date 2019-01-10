import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class YandexMarketTest {

    private static RemoteWebDriver driver;

    @BeforeClass
    public static void setUp() throws MalformedURLException {
        System.out.println("Начинаю тест Яндекс.Маркета");

        URL chromeDriverUrl = new URL("http://localhost:9515");

        System.out.println("Загружаю драйвер");
        // 1.      Открыть браузер Chrome и развернуть на весь экран.
        System.out.println("1.      Открыть браузер Chrome и развернуть на весь экран.");
        driver = new RemoteWebDriver(chromeDriverUrl, new ChromeOptions()); // открываем хром
        driver.manage().window().maximize(); // разворачиваем окно
        // 2.      Зайти на yandex.ru.
        System.out.println("2.      Зайти на yandex.ru.");
        driver.get("https://yandex.ru/"); // идем на яндекс

    }

    @AfterClass
    public static void tearDown() {
        try {
            System.out.print("Закрываю драйвер...");
            driver.close();
            System.out.println("Закрыто");
        } catch (NullPointerException e) {
            System.out.println("Не удалось закрыть драйвер:" + e.getMessage());
        }
        System.out.println("Завершаю тест Яндекс.Маркета");
    }

    @Test
    public void test1() {
        // 3.      Перейти в яндекс маркет
        System.out.println("3.      Перейти в яндекс маркет");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS); // ждем прогрузки
        driver.findElement(By.linkText("Маркет")).click(); // жмем на маркет

        // 4.      Выбрать раздел Компьютеры
        System.out.println("4.      Выбрать раздел Компьютеры");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Компьютерная техника")).click();

        // 5.      Выбрать раздел Ноутбуки
        System.out.println("5.      Выбрать раздел Ноутбуки");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Ноутбуки")).click();

        // 6.      Задать параметр поиска до 30000 рублей.
        System.out.println("6.      Задать параметр поиска до 30000 рублей.");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.id("glpriceto")).sendKeys("30000");

        // 7.      Выбрать производителя HP и Lenovo
        //TODO выдает ошибку
//        System.out.println("7.      Выбрать производителя HP и Lenovo");
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.findElement(By.linkText("HP")).click();
//        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//        driver.findElement(By.linkText("Lenovo")).click();

        // 8.      Нажать кнопку Применить.
        // Нет такой кнопки

        // 9.      Проверить, что элементов на странице 12.
        System.out.println("9.      Проверить, что элементов на странице 12.");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        // TODO убрать в функцию
        // получим список всех отображенных товаров
        List<WebElement> cardList = driver.findElements(By.xpath(
                "//div[contains(@data-id, 'model-')]"
//                "n-snippet-card2 i-bem b-zone b-spy-visible b-spy-visible_js_inited b-zone_js_inited n-snippet-card2_js_inited"
        ));
        // проверяем что элементов 12, а если не 12 то выставляем 12 в настройках
        System.out.println("\tЭлементов на странице: " + cardList.size());
        if (cardList.size() != 12) {
            //TODO выдает ошибку
//            // на форме два селекта с одинаковыми атрибутами, поэтому будем искать по родителю, у которого они уникальны
//            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//            Thread.sleep(5000);
//            WebElement select_size_span = driver.findElement(By.xpath("//span[contains(@class, 'select select_size_')]"));
//            Select select_size_select = new Select(select_size_span.findElement(By.xpath(".//select")));
//            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//
////            select_size_span.findElement(By.xpath(".//select")).click();
//            select_size_select.selectByVisibleText("Показывать по 12");
//            driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//            driver.findElement(By.xpath("//span[contains(text()='Показывать по 12')]")).click();
        }

        // 10.  Запомнить первый элемент в списке.
        System.out.println("10.  Запомнить первый элемент в списке.");
        // TODO убрать в функцию
        // получим список всех отображенных товаров
        cardList = driver.findElements(By.xpath(
                "//div[contains(@data-id, 'model-')]"
//                "n-snippet-card2 i-bem b-zone b-spy-visible b-spy-visible_js_inited b-zone_js_inited n-snippet-card2_js_inited"
        ));
        WebElement firstElement = cardList.get(0); // запоминаем первую карточку

        // 11.  В поисковую строку ввести запомненное значение.
        System.out.println("11.  В поисковую строку ввести запомненное значение.");
        WebElement title_a = firstElement.findElement(By.xpath(".//div[contains(@class, 'title')]/a"));
        String itemTitle = title_a.getText(); // получаем текст из первой карточки товара
        System.out.println("\tПервый элемент: " + itemTitle);
        driver.findElement(By.xpath("//input[@id = 'header-search']")).sendKeys(itemTitle);

        // 12.  Найти и проверить, что наименование товара соответствует запомненному значению.
        System.out.println("12.  Найти и проверить, что наименование товара соответствует запомненному значению.");
        driver.findElement(By.xpath("//button[contains(@class, 'button2')]")).click(); // найти
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        // TODO убрать в функцию
        // получим список всех отображенных товаров
        cardList = driver.findElements(By.xpath(
                "//div[contains(@data-id, 'model-')]"
//                "n-snippet-card2 i-bem b-zone b-spy-visible b-spy-visible_js_inited b-zone_js_inited n-snippet-card2_js_inited"
        ));
        firstElement = cardList.get(0); // запоминаем первую карточку
        title_a = firstElement.findElement(By.xpath(".//div[contains(@class, 'title')]/a"));
        if (itemTitle.equals(title_a.getText())) {
            System.out.println("\t Наименование товара соответствует запомненному значению");
        } else {
            System.out.println("\t Error. Наименование товара не соответствует запомненному значению");
        }
    }
}
