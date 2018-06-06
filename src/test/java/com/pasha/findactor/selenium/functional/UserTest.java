package com.pasha.findactor.selenium.functional;

import com.pasha.findactor.selenium.util.TestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Functional testing of user registration, user login/logout and other related to
 * users cases.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public class UserTest {

    private static final String LOGOUT_URL = "localhost:8080/logout";

    private static String ssoId;
    private static final String USER_PASSWORD = "test";

    private static WebDriver driver;

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        driver = new ChromeDriver();
        ssoId = TestUtils.generateSsoId();
        TestUtils.registerTestUser(driver, ssoId, USER_PASSWORD);
    }

    @AfterClass
    public static void afterClass() {
        TestUtils.deleteUser(driver, ssoId);
    }

    @After
    public void after() {
        driver.get(LOGOUT_URL);
    }

    @Test
    public void testLoginPositive() {
        TestUtils.loginTestUser(driver, ssoId, USER_PASSWORD);
        final String newWorksheetHeader = "newWorksheetHeader";
        // Test that there is no NoSuchElementException
        driver.findElement(By.id(newWorksheetHeader));
    }

    @Test(expected = NoSuchElementException.class)
    public void testLoginIncorrectLoginAndPassword() {
        TestUtils.loginTestUser(driver, TestUtils.generateSsoId(), TestUtils.generateSsoId());
        final String newWorksheetHeader = "newWorksheetHeader";
        // Test that NoSuchElementException is occurred
        driver.findElement(By.id(newWorksheetHeader));
    }

    @Test(expected = NoSuchElementException.class)
    public void testLoginEmptyLoginAndPassword() {
        TestUtils.loginTestUser(driver, "", "");
        final String newWorksheetHeader = "newWorksheetHeader";
        // Test that NoSuchElementException is occurred
        driver.findElement(By.id(newWorksheetHeader));
    }


    @Test(expected = NoSuchElementException.class)
    public void testRegisterUserNegative() {
        TestUtils.registerTestUser(driver, "", "");
        final String registrationSuccessHeader = "registrationSuccessHeader";
        driver.findElement(By.id(registrationSuccessHeader));
    }

    @Test
    public void testLogoutAndRedirectToLoginPage() {
        TestUtils.loginTestUser(driver, ssoId, USER_PASSWORD);
        driver.get(LOGOUT_URL);
        final String expectedTitle = "Login page";
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }
}
