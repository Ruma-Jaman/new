import org.openqa.selenium.WebElement;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppTest {
    public static void main(String[] args) throws Exception {

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        JsonObject data = JsonParser.parseReader(new FileReader("data/testData.json")).getAsJsonObject();
        String email = data.get("email").getAsString();
        String password = data.get("password").getAsString();

        driver.get("https://test.nop-station.store/");

        // Step 2: Register
        driver.findElement(By.linkText("Register")).click();
        Thread.sleep(1000);
        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Test");
        driver.findElement(By.id("LastName")).sendKeys("User");
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(password);
        driver.findElement(By.id("register-button")).click();

        // Step 3: Login
        driver.findElement(By.linkText("Log in")).click();
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(password);
        driver.findElement(By.xpath("//button[contains(text(),'Log in')]")).click();

        // Step 4: Search and Add Products
        driver.findElement(By.id("small-searchterms")).sendKeys("Awesome");
        driver.findElement(By.xpath("//button[@type='submit' and contains(@class,'search-box-button')]")).click();
        Thread.sleep(2000);
        List<WebElement> addToCartBtns = driver.findElements(By.xpath("//button[contains(@class,'add-to-cart-button')]"));

        for (int i = 0; i < 2 && i < addToCartBtns.size(); i++) {
            addToCartBtns.get(i).click();
            Thread.sleep(2000);
        }

        // Step 5: Go to Cart
        driver.findElement(By.linkText("Shopping cart")).click();
        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();

        // Step 6: Billing
        Thread.sleep(1000);
        new Select(driver.findElement(By.id("BillingNewAddress_CountryId"))).selectByVisibleText("Bangladesh");
        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Dhaka");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("123 Test Street");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("1216");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("0123456789");
        driver.findElement(By.xpath("//button[@onclick='Billing.save()']")).click();

        // Step 7: Shipping
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@onclick='Shipping.save()']")).click();

        // Step 8: Shipping Method
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@onclick='ShippingMethod.save()']")).click();

        // Step 9: Payment Method
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@onclick='PaymentMethod.save()']")).click();

        // Step 10: Payment Info
        Thread.sleep(2000);
        driver.findElement(By.id("CardholderName")).sendKeys(data.get("cardHolder").getAsString());
        driver.findElement(By.id("CardNumber")).sendKeys(data.get("cardNumber").getAsString());
        driver.findElement(By.id("ExpireMonth")).sendKeys("09");
        driver.findElement(By.id("ExpireYear")).sendKeys("2030");
        driver.findElement(By.id("CardCode")).sendKeys(data.get("cvv").getAsString());
        driver.findElement(By.xpath("//button[@onclick='PaymentInfo.save()']")).click();

        // Step 11: Confirm Order
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[@onclick='ConfirmOrder.save()']")).click();

        System.out.println("Order placed successfully!");
        Thread.sleep(3000);
        driver.quit();
    }
}