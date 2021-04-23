package Selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.assertEquals;

@TestPropertySource("classpath:application-test.properties")
@Ignore("Not required to test the API")
public class HelpQueueUITest {

    private WebDriver driver;


    private String uiLocation = "http://localhost:3000";



    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1366, 768));

    }


    @Test
    public void testDepartmentMenuFunctionality() throws InterruptedException {
        driver.get(uiLocation);
        WebElement menu = driver.findElement(By.id("deptMainMenu"));

        assertEquals("Departments", menu.getText());
    }

    @Test
    @Ignore("Requires API to be running")
    public void testDepartmentMenuFunctionalityWithServer() throws InterruptedException {
        driver.get(uiLocation);
        WebElement menu = driver.findElement(By.id("deptMainMenu"));
        menu.click();
        WebElement menu1 = driver.findElement(By.id("Wealth"));
        menu1.click();
        assertEquals("Departments\nWealth\nCoreEng\nTrade", menu.getText());
    }


    @Test
    public void testUnassignedFunctionality() {
        driver.get(uiLocation);
        WebElement unassignedMenu = driver.findElement(By.id("unassigned"));
        assertEquals("Unassigned Tickets", unassignedMenu.getText());
    }

    @Test
    public void testManageDepartmentFunctionality() {
        driver.get(uiLocation);
        WebElement departmentManageMenu = driver.findElement(By.id("manage-departments"));
        assertEquals("Manage Departments", departmentManageMenu.getText());
    }

    @Test
    public void testWelcomePage() {
        driver.get(uiLocation);
        WebElement welcomeMessage = driver.findElement(By.id("home-screen"));
        assertEquals("Welcome to the Help Queue application! Select an option in the menu to begin.", welcomeMessage.getText());
    }


    @After
    public void tearDown() {
        driver.close();
    }
}
