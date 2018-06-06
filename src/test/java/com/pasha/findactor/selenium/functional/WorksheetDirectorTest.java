package com.pasha.findactor.selenium.functional;

import com.pasha.findactor.model.User;
import com.pasha.findactor.selenium.util.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

/**
 * Functional testing of logic related to worksheets (for directors).
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public class WorksheetDirectorTest {

    private static final String LOGOUT_URL = "localhost:8080/logout";

    private static String directorSso;
    private static String agentSso;
    private static String userSso;
    private static final String USER_PASSWORD = "selenium_test";

    private static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void after() {
        driver.get(LOGOUT_URL);
        TestUtils.deleteUser(driver, directorSso);
        TestUtils.deleteUser(driver, agentSso);
        TestUtils.deleteUser(driver, userSso);
    }

    @Test
    public void testViewWorksheetsPositive() {
        directorSso = TestUtils.generateSsoId();

        User director = TestUtils.registerTestUserAsAdmin(driver, directorSso, USER_PASSWORD, "DIRECTOR");
        driver.get(LOGOUT_URL);

        agentSso = TestUtils.generateSsoId();
        User agent = TestUtils.registerTestUserAsAdmin(driver, agentSso, USER_PASSWORD, "AGENT");
        driver.get(LOGOUT_URL);

        userSso = TestUtils.generateSsoId();
        User user = TestUtils.registerTestUser(driver, userSso, USER_PASSWORD);
        driver.get(LOGOUT_URL);

        // Submit worksheet
        TestUtils.loginTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.submitTestWorksheet(driver);
        driver.get(LOGOUT_URL);

        // Accept worksheet
        TestUtils.loginTestUser(driver, agentSso, USER_PASSWORD);
        String expectedTitle = "Worksheets for agent";
        Assert.assertEquals(expectedTitle, driver.getTitle());

        WebElement table = driver.findElement(By.id("tableWithWorksheets"));
        List<WebElement> rows = table.findElements(By.xpath("//tbody/tr"));
        boolean firstNameMatch = false;
        boolean lastNameMatch = false;
        WebElement castingButton;
        for (WebElement row: rows) {
            List<WebElement> cells = row.findElements(By.xpath("td"));
            for (WebElement cell: cells) {
                if (cell.getText().equals(user.getFirstName())) {
                    firstNameMatch = true;
                }
                if (cell.getText().equals(user.getLastName())) {
                    lastNameMatch = true;
                }
                if (cell.getText().equals("casting!")) {
                    castingButton = row.findElement(By.xpath("td[6]"));
                    castingButton.findElement(By.tagName("a")).click();
                    break;
                }
            }
            if (firstNameMatch && lastNameMatch) {
                break;
            }
        }

        Assert.assertTrue(firstNameMatch);
        Assert.assertTrue(lastNameMatch);

        driver.get(LOGOUT_URL);
        // View worksheet by director
        TestUtils.loginTestUser(driver, directorSso, USER_PASSWORD);
        String expectedTitleDirector = "Worksheets for director";
        Assert.assertEquals(expectedTitleDirector, driver.getTitle());

        WebElement tableDirector = driver.findElement(By.id("tableWithWorksheets"));
        List<WebElement> rowsDirectorTable = tableDirector.findElements(By.xpath("//tbody/tr"));
        firstNameMatch = false;
        lastNameMatch = false;
        WebElement offerButton;
        for (WebElement row: rowsDirectorTable) {
            List<WebElement> cells = row.findElements(By.xpath("td"));
            for (WebElement cell: cells) {
                if (cell.getText().equals(user.getFirstName())) {
                    firstNameMatch = true;
                }
                if (cell.getText().equals(user.getLastName())) {
                    lastNameMatch = true;
                }
                if (cell.getText().equals("odder!")) {
                    offerButton = row.findElement(By.xpath("td[6]"));
                    offerButton.findElement(By.tagName("a")).click();
                    break;
                }
            }
            if (firstNameMatch && lastNameMatch) {
                break;
            }
        }

        Assert.assertTrue(firstNameMatch);
        Assert.assertTrue(lastNameMatch);
    }
}
