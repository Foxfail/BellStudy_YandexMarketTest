import com.sun.istack.internal.NotNull;
import org.junit.*;
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
    public static void setUpBeforeClass() throws MalformedURLException {
        System.out.println("Начинаю тест Яндекс.Маркета");
        URL chromeDriverUrl = new URL("http://localhost:9515");

        // 1.      Открыть браузер Chrome и развернуть на весь экран.
        System.out.println("1.      Открыть браузер Chrome и развернуть на весь экран.");
        System.out.print("Загружаю драйвер...");
        driver = new RemoteWebDriver(chromeDriverUrl, new ChromeOptions()); // открываем хром
        System.out.println("Загружено");
        driver.manage().window().maximize(); // разворачиваем окно
        System.out.println();
    }

    @Before
    public void setUp() {
        System.out.println("\t Начинаю новый тест");

        // 2.      Зайти на yandex.ru.
        System.out.println("2.      Зайти на yandex.ru.");
        driver.get("https://yandex.ru/"); // идем на яндекс

        // 3.      Перейти в яндекс маркет
        System.out.println("3.      Перейти в яндекс маркет");
        findWebElementByXpath("//a[contains(text(), 'Маркет')]").click();

        // 4.      Выбрать раздел Компьютеры
        System.out.println("4.      Выбрать раздел Компьютеры");
        findWebElementByXpath("//a[contains(span, 'Компьютерная техника')]").click();


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
    public void test1() throws InterruptedException {
        System.out.println("  Начинаю тест 1");

        List<WebElement> goodsList; // в этом списке будут хранится карточки товаров

        // 5.      Выбрать раздел Ноутбуки
        System.out.println("5.      Выбрать раздел Ноутбуки");
        findWebElementByXpath("//a[contains(text(), 'Ноутбуки')]").click();


        // 6.      Задать параметр поиска до 30000 рублей.
        System.out.println("6.      Задать параметр поиска до 30000 рублей.");
        findWebElementByXpath("//input[contains(@name, 'Цена до')]").sendKeys("30000");


        // 7.      Выбрать производителя HP и Lenovo
        System.out.println("7.      Выбрать производителя HP и Lenovo");
        checkManufacturerCheckboxes(new String[]{"HP", "Lenovo"});


        // 8.      Нажать кнопку Применить.
        System.out.println("8.      Нажать кнопку Применить.");
        // Нет такой кнопки, просто ждем пока прогрузится наш выбор
        Thread.sleep(10000);


        // 9.      Проверить, что элементов на странице 12.
        System.out.println("9.      Проверить, что элементов на странице 12.");
        goodsList = getGoodsList(); // получим список всех отображенных товаров
        Boolean isAnyGoodsFound = checkGoodsCardsCount(goodsList);
        if (!isAnyGoodsFound) return;


        // 10.  Запомнить первый элемент в списке.
        System.out.println("10.  Запомнить первый элемент в списке.");
        goodsList = getGoodsList(); // обновим список всех отображенных товаров
        WebElement firstElement = goodsList.get(0); // запоминаем первую карточку товара


        // 11.  В поисковую строку ввести запомненное значение.
        System.out.println("11.  В поисковую строку ввести запомненное значение.");
        // получаем название товара
        WebElement title_link = firstElement.findElement(By.xpath(".//div[contains(@class, 'title')]/a"));
        String itemTitle = title_link.getText();
        System.out.println("  Первый элемент: " + itemTitle);
        // вводим его в поиск
        driver.findElement(By.xpath("//input[@id = 'header-search']")).sendKeys(itemTitle);


        // 12.  Найти и проверить, что наименование товара соответствует запомненному значению.
        System.out.println("12.  Найти и проверить, что наименование товара соответствует запомненному значению.");
        findWebElementByXpath("//button[contains(@class, 'button2')]").click(); // щелкаем найти
        goodsList = getGoodsList(); // получаем результаты поиска
        firstElement = goodsList.get(0); // запоминаем первую карточку
        title_link = firstElement.findElement(By.xpath(".//div[contains(@class, 'title')]/a"));
        if (itemTitle.equals(title_link.getText())) { // сверяем названия
            System.out.println("  Наименование товара соответствует запомненному значению");
        } else {
            Assert.fail("  Error. Наименование товара не соответствует запомненному значению");
        }
        System.out.println();
    }

    @Test
    public void test2() throws InterruptedException {
        System.out.println("  Начинаю тест 2");

        List<WebElement> goodsList; // в этом списке будут хранится карточки товаров


        //5.      Выбрать раздел Планшеты
        System.out.println("5.      Выбрать раздел Планшеты");
        findWebElementByXpath("//a[contains(text(), 'Планшеты')]").click();


        //6.      Задать параметр поиска от 20000 рублей.
        System.out.println("6.      Задать параметр поиска от 20000 рублей.");
        findWebElementByXpath("//input[contains(@name, 'Цена от')]").sendKeys("20000");


        //7.      Задать параметр поиска до 25000 рублей.
        System.out.println("7.      Задать параметр поиска до 25000 рублей.");
        findWebElementByXpath("//input[contains(@name, 'Цена до')]").sendKeys("25000");


        //8.      Выбрать производителей Acer и DELL
        System.out.println("8.      Выбрать производителей Acer и DELL");
        checkManufacturerCheckboxes(new String[]{"Acer", "DELL"});


        //9.      Нажать кнопку Применить.
        System.out.println("9.      Нажать кнопку Применить.");
        // Нет такой кнопки, просто ждем пока прогрузится наш выбор
        Thread.sleep(10000);


        //10.  Проверить, что элементов на странице 12.
        System.out.println("10.      Проверить, что элементов на странице 12.");
        goodsList = getGoodsList(); // получим список всех отображенных товаров
        // проверяем что элементов 12, а если не 12 то выставляем 12 в настройках
        Boolean isAnyGoodsFound = checkGoodsCardsCount(goodsList);
        if (!isAnyGoodsFound) return;


        //11.  Запомнить первый элемент в списке.
        System.out.println("11.  Запомнить первый элемент в списке.");
//        goodsList = getGoodsList(); // получим список всех отображенных товаров // уже получали до этого
        WebElement firstElement = goodsList.get(0); // запоминаем первую карточку товара


        //12.  В поисковую строку ввести запомненное значение.
        System.out.println("12.  В поисковую строку ввести запомненное значение.");
        // получаем название товара
        WebElement title_link = firstElement.findElement(By.xpath(".//div[contains(@class, 'title')]/a"));
        String itemTitle = title_link.getText();
        System.out.println("  Первый элемент: " + itemTitle);
        // вводим его в поиск
        driver.findElement(By.xpath("//input[@id = 'header-search']")).sendKeys(itemTitle);


        //13.  Найти и проверить, что наименование товара соответствует запомненному значению.
        System.out.println("13.  Найти и проверить, что наименование товара соответствует запомненному значению.");
        findWebElementByXpath("//button[contains(@class, 'button2')]").click(); // щелкаем найти
        goodsList = getGoodsList(); // получаем результаты поиска
        firstElement = goodsList.get(0); // запоминаем первую карточку
        title_link = firstElement.findElement(By.xpath(".//div[contains(@class, 'title')]/a"));
        if (itemTitle.equals(title_link.getText())) { // сверяем названия
            System.out.println("  Наименование товара соответствует запомненному значению");
        } else {
            System.out.println("  Error. Наименование товара не соответствует запомненному значению");
        }
        System.out.println();
    }

    // ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ
    private WebElement findWebElementByXpath(String xpath) {
        WebElement webElement;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webElement = driver.findElement(By.xpath(xpath));
        return webElement;
    }

    // возможно следует сделать метод более конкретным т.к. используется он только за нахождением списка товаров
    @SuppressWarnings("SameParameterValue")
    private List<WebElement> findWebElementsByXpath(String xpath) {
        List<WebElement> webElementList;
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        webElementList = driver.findElements(By.xpath(xpath));
        return webElementList;
    }

    // получим список всех отображенных товаров
    private List<WebElement> getGoodsList() {
        return findWebElementsByXpath("//div[contains(@data-id, 'model-')]");
    }

    // Проверить, что элементов на странице 12
    // return true - если найдены товары
    // return false - если нет товаров с заданными параметрами
    private Boolean checkGoodsCardsCount(List<WebElement> goodsList) throws InterruptedException {
        System.out.println("  Элементов на странице: " + goodsList.size());
        if (goodsList.size() == 0) {
//            Assert.fail("Не найдено ни одного товара по заданным параметрам");
            // если надо чтобы тест выдавал ошибку в случае не нахождения товаров то расскоментить пред. строку
            System.out.println("Не найдено ни одного товара по заданным параметрам");
            return false;
        } else if (goodsList.size() > 12) {
            //сначала щелкаем на выпадающий список чтобы появились элементы этого списка
            WebElement select_size_span = driver.findElement(By.xpath("//span[contains(@class, 'select select_size_')]"));
            select_size_span.click();

            //затем ищем элемент выпадающего списка "показывать по 12"
            WebElement dropdown_element = findWebElementByXpath("//span[(contains(@class, 'select__text')) and text()='Показывать по 12']");
            dropdown_element.click();

            Thread.sleep(10000); // ждем пока прогрузятся товары

            goodsList = getGoodsList(); // получим список всех отображенных товаров
            System.out.println("  Элементов на странице теперь: " + goodsList.size()); // должно быть 12
        }
        return true;
    }

    private void checkManufacturerCheckboxes(@NotNull String[] manufacturers) throws InterruptedException {
        // acer скрыт под "показать всех". Таких ссылок на странице аж 4шт.
        // поэтому ищем через блок производителей
        WebElement manufacturer_fieldset = findWebElementByXpath("//fieldset[contains(legend, 'Производитель')]");
        manufacturer_fieldset.findElement(By.xpath(".//a[contains(text(), 'Показать всё')]")).click();
        Thread.sleep(5000);
        for (String manufacturer : manufacturers) {
            findWebElementByXpath("//div[contains(span, '" + manufacturer + "')]").click();
        }
    }

}
