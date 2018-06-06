package com.pasha.findactor.selenium.util;

import com.pasha.findactor.model.User;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.UUID;

/**
 * Common methods which are used in many tests are here.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public class TestUtils {

    private static final String LOGIN_URL = "localhost:8080/login";
    private static final String REGISTER_URL = "localhost:8080/registerUser";
    private static final String SUBMIT_WORKSHEET_URL = "localhost:8080/submitWorksheet";
    private static final String LOGOUT_URL = "localhost:8080/logout";

    private static final String ADMIN_SSO = "pasha";
    private static final String ADMIN_PASSWORD = "pasha";

    public static void loginTestUser(WebDriver driver, String ssoID, String password) {
        final String usernameId = "username";
        final String passwordId = "password";
        final String submitButtonId = "submitLoginButton";

        driver.get(LOGIN_URL);

        WebElement usernameField = driver.findElement(By.id(usernameId));
        WebElement passwordField = driver.findElement(By.id(passwordId));
        WebElement submitButton = driver.findElement(By.id(submitButtonId));

        usernameField.sendKeys(ssoID);
        passwordField.sendKeys(password);
        submitButton.submit();
    }

    public static User registerTestUser(WebDriver driver, String testSsoId, String password) {
        driver.get(REGISTER_URL);

        final String firstNameId = "firstName";
        final String lastNameId = "lastName";
        final String ssoIdId = "ssoId";
        final String passwordId = "password";
        final String emailId = "email";
        final String registerButtonId = "registerButton";

        WebElement firstNameField = driver.findElement(By.id(firstNameId));
        WebElement lastNameField = driver.findElement(By.id(lastNameId));
        WebElement ssoIdField = driver.findElement(By.id(ssoIdId));
        WebElement passwordField = driver.findElement(By.id(passwordId));
        WebElement emailField = driver.findElement(By.id(emailId));
        WebElement registerButton = driver.findElement(By.id(registerButtonId));

        final String testFirstName = "test_selenium";
        final String testLastName = "test_selenium";
        final String testEmail = "test_selenium@mail.ru";

        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setSsoId(ssoIdId);
        user.setPassword(password);

        firstNameField.sendKeys(testFirstName);
        lastNameField.sendKeys(testLastName);
        ssoIdField.sendKeys(testSsoId);
        passwordField.sendKeys(password);
        emailField.sendKeys(testEmail);
        registerButton.submit();

        return user;
    }

    public static User registerTestUserAsAdmin(WebDriver driver, String testSsoId, String password, String role) {

        TestUtils.loginTestUser(driver, ADMIN_SSO, ADMIN_PASSWORD);
        driver.get(REGISTER_URL);

        final String firstNameId = "firstName";
        final String lastNameId = "lastName";
        final String ssoIdId = "ssoId";
        final String passwordId = "password";
        final String emailId = "email";
        final String selectRoleId = "selectRole";
        final String registerButtonId = "registerButton";

        WebElement firstNameField = driver.findElement(By.id(firstNameId));
        WebElement lastNameField = driver.findElement(By.id(lastNameId));
        WebElement ssoIdField = driver.findElement(By.id(ssoIdId));
        WebElement passwordField = driver.findElement(By.id(passwordId));
        WebElement emailField = driver.findElement(By.id(emailId));
        WebElement registerButton = driver.findElement(By.id(registerButtonId));
        WebElement selectRole = driver.findElement(By.id(selectRoleId));
        Select select = new Select(selectRole);
        select.deselectAll();
        select.selectByVisibleText(role);

        final String testFirstName = "test_selenium";
        final String testLastName = "test_selenium";
        final String testEmail = "test_selenium@mail.ru";

        User user = new User();
        user.setFirstName(testFirstName);
        user.setLastName(testLastName);
        user.setEmail(testEmail);
        user.setSsoId(ssoIdId);
        user.setPassword(password);

        firstNameField.sendKeys(testFirstName);
        lastNameField.sendKeys(testLastName);
        ssoIdField.sendKeys(testSsoId);
        passwordField.sendKeys(password);
        emailField.sendKeys(testEmail);
        registerButton.submit();

        return user;
    }

    public static void submitTestWorksheet(WebDriver driver) {

        driver.get(SUBMIT_WORKSHEET_URL);

        final String ageId = "age";
        final String heightId = "height";
        final String weightId = "weight";
        final String sexId = "sex";
        final String nationalityId = "nationality";
        final String eyeColorId = "eyeColor";
        final String skinColorId = "skinColor";
        final String hairColorId = "hairColor";
        final String experienceId = "experience";
        final String reasonId = "reason";
        final String submitWorksheetButtonId = "submitWorksheetButton";

        WebElement ageField = driver.findElement(By.id(ageId));
        WebElement heightField = driver.findElement(By.id(heightId));
        WebElement weightField = driver.findElement(By.id(weightId));
        WebElement sexField = driver.findElement(By.id(sexId));
        WebElement nationalityField = driver.findElement(By.id(nationalityId));
        WebElement eyeColorField = driver.findElement(By.id(eyeColorId));
        WebElement skinColorField = driver.findElement(By.id(skinColorId));
        WebElement hairColorField = driver.findElement(By.id(hairColorId));
        WebElement experienceField = driver.findElement(By.id(experienceId));
        WebElement reasonField = driver.findElement(By.id(reasonId));
        WebElement submitWorksheetButton = driver.findElement(By.id(submitWorksheetButtonId));

        final String testAge = "11";
        final String testHeight = "180";
        final String testWeight = "71";
        final String testSex = "m";
        final String testNationality = "russian";
        final String testEyeColor = "blue";
        final String testSkinColor = "white";
        final String testHairColor = "brown";
        final String testExperience = "3 years of acting school";
        final String testReason = "Wanna try myself in big movie casting";

        ageField.sendKeys(testAge);
        heightField.sendKeys(testHeight);
        weightField.sendKeys(testWeight);
        sexField.sendKeys(testSex);
        nationalityField.sendKeys(testNationality);
        eyeColorField.sendKeys(testEyeColor);
        skinColorField.sendKeys(testSkinColor);
        hairColorField.sendKeys(testHairColor);
        experienceField.sendKeys(testExperience);
        reasonField.sendKeys(testReason);

        submitWorksheetButton.submit();

    }

    public static String generateSsoId() {
        String testSsoId = UUID.randomUUID().toString().substring(0, 10);
        return testSsoId.replaceAll("-", "");
    }

    public static void deleteUser(WebDriver driver, String ssoId) {
        TestUtils.loginTestUser(driver, ADMIN_SSO, ADMIN_PASSWORD);
        WebElement table = driver.findElement(By.id("tableWithUsers"));
        List<WebElement> rows = table.findElements(By.xpath("//tbody/tr"));
        boolean isBreak = false;
        for (WebElement row: rows) {
            List<WebElement> cells = row.findElements(By.xpath("td"));
            for (WebElement cell: cells) {
                if (cell.getText().equals(ssoId)) {
                    isBreak = true;
                    WebElement deleteCell = row.findElement(By.xpath("td[6]"));
                    WebElement deleteLink = deleteCell.findElement(By.tagName("a"));
                    deleteLink.click();
                    break;
                }
            }
            if (isBreak) {
                break;
            }
        }
        driver.get(LOGOUT_URL);
    }


}
