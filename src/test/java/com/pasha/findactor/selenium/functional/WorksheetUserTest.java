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

/**
 * Functional testing of logic related to worksheets (for users).
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public class WorksheetUserTest {

    private static final String LOGOUT_URL = "localhost:8080/logout";
    private static final String SUBMIT_WORKSHEET_URL = "localhost:8080/submitWorksheet";
    private static final String VIEW_WORKSHEETS_USER_URL = "localhost:8080/worksheetsUser";

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
        TestUtils.deleteUser(driver, userSso);
    }

    @Test
    public void testSubmitWorksheetPositive() {
        userSso = TestUtils.generateSsoId();

        TestUtils.registerTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.loginTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.submitTestWorksheet(driver);

        String expectedTitle = "Worksheet submitted";
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testSubmitWorksheetEmptyFields() {
        userSso = TestUtils.generateSsoId();

        TestUtils.registerTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.loginTestUser(driver, userSso, USER_PASSWORD);

        driver.get(SUBMIT_WORKSHEET_URL);
        final String submitWorksheetButtonId = "submitWorksheetButton";
        WebElement submitWorksheetButton = driver.findElement(By.id(submitWorksheetButtonId));
        submitWorksheetButton.submit();

        // Stay at submit worksheet page
        String expectedTitle = "Submit new worksheet";
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testSubmitWorksheetIncorrectFields() {
        userSso = TestUtils.generateSsoId();

        TestUtils.registerTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.loginTestUser(driver, userSso, USER_PASSWORD);

        driver.get(SUBMIT_WORKSHEET_URL);
        final String ageId = "age";
        WebElement ageField = driver.findElement(By.id(ageId));
        final String testIncorrectAge = "aaa";
        ageField.sendKeys(testIncorrectAge);

        final String submitWorksheetButtonId = "submitWorksheetButton";
        WebElement submitWorksheetButton = driver.findElement(By.id(submitWorksheetButtonId));
        submitWorksheetButton.submit();

        // Stay at submit worksheet page
        String expectedTitle = "Submit new worksheet";
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void testViewWorksheetUser() {
        userSso = TestUtils.generateSsoId();

        User user = TestUtils.registerTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.loginTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.submitTestWorksheet(driver);

        driver.get(VIEW_WORKSHEETS_USER_URL);

        final String editWorksheetLinkId = "editWorksheetLink";

        WebElement editWorksheetLink = driver.findElement(By.id(editWorksheetLinkId));
        WebElement userFirstName = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[2]/td[1]"));
        WebElement status = driver.findElement(By.xpath("/html/body/div/div[2]/table/tbody/tr[2]/td[3]"));

        String expectedName = user.getFirstName();
        String expectedStatus = "Reviewed";
        String expectedLinkName = "edit";

        String actualName = userFirstName.getText();
        String actualStatus = status.getText();
        String actualEditLinkName = editWorksheetLink.getText();

        // Go to worksheets list
        String expectedTitle = "Worksheets of user";
        Assert.assertEquals(expectedTitle, driver.getTitle());
        Assert.assertEquals(expectedLinkName, actualEditLinkName);
        Assert.assertEquals(expectedName, actualName);
        Assert.assertEquals(expectedStatus, actualStatus);
    }

    @Test
    public void testEditWorksheetUserPositiveSubmit() {
        goToViewWorksheetsPage();

        final String editWorksheetLinkId = "editWorksheetLink";

        WebElement editWorksheetLink = driver.findElement(By.id(editWorksheetLinkId));
        editWorksheetLink.click();

        final String reasonId = "reason";
        String newReasonField = "Just cool guy";
        WebElement reasonField = driver.findElement(By.id(reasonId));
        reasonField.clear();
        reasonField.sendKeys(newReasonField);

        String expectedTitle = "Edit worksheet";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(expectedTitle, actualTitle);

        final String submitButtonId = "submitEditedWorksheet";
        WebElement submitButton = driver.findElement(By.id(submitButtonId));
        submitButton.submit();

        String expectedTitleListWorksheets = "Worksheets of user";
        String actualTitleListWorksheets = driver.getTitle();
        Assert.assertEquals(expectedTitleListWorksheets, actualTitleListWorksheets);

        // Go to edit page and check that reason field was modified correctly
        editWorksheetLink = driver.findElement(By.id(editWorksheetLinkId));
        editWorksheetLink.click();

        reasonField = driver.findElement(By.id(reasonId));
        String actualReason = reasonField.getAttribute("value");
        Assert.assertEquals(newReasonField, actualReason.trim());
    }

    @Test
    public void testEditWorksheetUserPositiveCancel() {
        goToViewWorksheetsPage();

        final String editWorksheetLinkId = "editWorksheetLink";

        WebElement editWorksheetLink = driver.findElement(By.id(editWorksheetLinkId));
        editWorksheetLink.click();

        final String cancelLinkId = "cancelLink";
        WebElement cancelLink = driver.findElement(By.id(cancelLinkId));
        cancelLink.click();

        String expectedTitleListWorksheets = "Worksheets of user";
        String actualTitleListWorksheets = driver.getTitle();
        Assert.assertEquals(expectedTitleListWorksheets, actualTitleListWorksheets);
    }

    private void goToViewWorksheetsPage() {
        userSso = TestUtils.generateSsoId();

        TestUtils.registerTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.loginTestUser(driver, userSso, USER_PASSWORD);
        TestUtils.submitTestWorksheet(driver);

        driver.get(VIEW_WORKSHEETS_USER_URL);
    }
}
