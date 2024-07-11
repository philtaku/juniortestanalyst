package assessment;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

public class automationassessment {

    public static void main(String[] args) {
        // Setup ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // A. Navigate to the URL
            driver.get("http://www.way2automation.com/angularjsprotractor/webtables/");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // B. Validate that you are on the User List Table
            WebElement userListTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userListTable")));
            if (userListTable.isDisplayed()) {
                System.out.println("User List Table is displayed.");

                // D. User details
                String[][] userDetails = {
                        {"fName1", "LName1", "pass1", "admin@mail.com", "082555"},
                        {"fName2", "LName2", "pass2", "customer@mail.com", "083444"}
                };

                for (String[] userDetail : userDetails) {
                    addUser(driver, wait, userDetail);
                }

                // F. Ensure that 2 users are added to the list
                for (String[] userDetail : userDetails) {
                    String username = userDetail[0].toLowerCase() + UUID.randomUUID().toString().substring(0, 8);
                    WebElement addedUser = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@class='smart-table table table-striped']//tr/td[text()='" + username + "']")));
                    if (addedUser.isDisplayed()) {
                        System.out.println("User " + username + " added successfully.");
                    } else {
                        System.out.println("Failed to add user " + username + ".");
                    }
                }
            } else {
                System.out.println("User List Table is not displayed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
    }

    public static void addUser(WebDriver driver, WebDriverWait wait, String[] userDetails) {
        // C. Click Add User
        WebElement addUserButton = driver.findElement(By.xpath("//button[text()='Add User']"));
        addUserButton.click();

        String firstName = userDetails[0];
        String lastName = userDetails[1];
        String username = firstName.toLowerCase() + UUID.randomUUID().toString().substring(0, 8); // Ensure unique username
        String password = userDetails[2];
        String email = userDetails[3];
        String mobilePhone = userDetails[4];

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("FirstName"))).sendKeys(firstName);
        driver.findElement(By.name("LastName")).sendKeys(lastName);
        driver.findElement(By.name("UserName")).sendKeys(username);
        driver.findElement(By.name("Password")).sendKeys(password);
        driver.findElement(By.name("Email")).sendKeys(email);
        driver.findElement(By.name("Mobilephone")).sendKeys(mobilePhone);

        // Select Customer
        driver.findElement(By.xpath("//label[text()='Company AAA']")).click();
        // Select Role
        WebElement roleDropdown = driver.findElement(By.name("RoleId"));
        roleDropdown.click();
        driver.findElement(By.xpath("//option[text()='Customer']")).click();

        // Save the user
        driver.findElement(By.xpath("//button[text()='Save']")).click();
    }
}
