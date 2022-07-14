import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class Login {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com/");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    private void login(String user, String pass) {
        WebElement username = driver.findElement(By.name("txtUsername"));
        WebElement password = driver.findElement(By.name("txtPassword"));

        username.sendKeys(user);
        password.sendKeys(pass);

        WebElement submit = driver.findElement(By.className("button"));
        submit.click();
    }

    @Test
    public void loginSuccessfully() {
        login("Admin", "admin123");

        WebElement welcome = driver.findElement(By.id("welcome"));
        String expectedText = "Welcome";
        Assert.assertTrue(welcome.getText().contains(expectedText), "Welcome message is not correct. Expected to contain: " + expectedText + ", Actual: " + welcome.getText());
    }

    @Test
    public void loginBasCredentials() {
        login("asd", "asd");

        WebElement spanMessage = driver.findElement(By.id("spanMessage"));
        String expectedText = "Invalid credentials";
        Assert.assertTrue(spanMessage.getText().contains(expectedText), "Welcome message is not correct. Expected to contain: " + expectedText + ", Actual: " + spanMessage.getText());
    }

    @Test
    public void logout() {
        login("Admin", "admin123");

        WebElement welcome = driver.findElement(By.id("welcome"));
        welcome.click();

        WebElement logout = driver.findElement(By.cssSelector("#welcome-menu > ul > li:nth-child(3) > a"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(logout));

        logout.click();
    }
}
