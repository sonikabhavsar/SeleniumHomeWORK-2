import com.google.common.annotations.VisibleForTesting;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


public class TestSuite {
    protected static WebDriver driver;
    static Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    public static void clickOnElement(By by) {
        driver.findElement(by).click();
    }

    public static void typeText(By by, String text) {
        driver.findElement(by).sendKeys(text);
    }

    public static String getTextFromElement(By by) {
        return driver.findElement(by).getText();
    }

    public static long timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    static String expectedRegistrationCompleteMsg = "Thanks for registration";
    static String expectedCommunityPollVoteMessage = "Only registered users can vote.";
    static String expectedEmailToFriendMessage = "Only registered customers can use email a friend feature";
    static String expectedShoppingCartMessage = "Leica T Mirrorless Digital Camera";
    static String expectedReferProductMessage = "Your message has been sent.";
    static String expectedAbleToVoteMessage = "Only registered users can vote.";
    static String expectedCompareProductMessage = "You have no items to compare.";


    @BeforeMethod
    public static void openBrowser() {
        driver = new ChromeDriver();
        //type the url
        driver.get("https://demo.nopcommerce.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @AfterMethod
    public static void closeBrowser() {
        driver.close();
    }

    @Test
    public static void verifyUserShouldBeAbleToRegisterSuccessfully() {

        //click on register button
        clickOnElement(By.className("ico-register"));
        //type first name
        typeText(By.id("FirstName"), "TestFirstName");
        //type last name
        typeText(By.id("LastName"), "TestLstName");
        //type email address
        typeText(By.name("Email"), "testemail+" + timestamp() + "@gmail.com");
        //type password
        typeText(By.id("Password"), "test1234");
        //type confirm password
        typeText(By.id("ConfirmPassword"), "test1234");
        //click on register submit button
        clickOnElement(By.id("register-button"));
        //get text message from web element
        String actualMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        System.out.println("My message:" + actualMessage);
        Assert.assertEquals(actualMessage, expectedRegistrationCompleteMsg, "Registration is not working");

    }

    @Test
    public static void verifyUserShouldBeAbleToCommunityPoll() {

        //click on good button
        clickOnElement(By.id("pollanswers-2"));
        //Click on vote button
        clickOnElement(By.id("vote-poll-1"));
        //Use the selenium wait
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        //get the text message for web element
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class=\"poll-vote-error\"]")));
        String actualMessage = getTextFromElement(By.xpath("//div[@class=\"poll-vote-error\"]"));
        System.out.println("My message:" + actualMessage);
        //text message  is disappearing
        Assert.assertEquals(actualMessage, expectedCommunityPollVoteMessage, "Error message is disappearing.");

    }

    @Test
    public static void VerifyUserShouldBeAbleToSendEmailToFriend() {

        //click on product
        clickOnElement(By.xpath("//a[@ href=\"/apple-macbook-pro-13-inch\"]"));
        //click on email a friend
        clickOnElement(By.className("email-a-friend"));
        //Enter friend email
        typeText(By.className("friend-email"), "testmail+" + timestamp() + "@gmail.com");
        //Enter your email
        typeText(By.className("your-email"), "test1235+" + timestamp() + "@gmail.com");
        //click on send email button
        clickOnElement(By.name("send-email"));
        //get text message from web element
        String actualMessage = getTextFromElement(By.xpath("//div[@class='message-error validation-summary-errors']/ul/li"));
        System.out.println("My Message:" + actualMessage);
        Assert.assertEquals(actualMessage, expectedEmailToFriendMessage, "can not sending email");

    }

    @Test
    public static void VerifyUserShouldBeAbleToAddProductToShoppingCart() {
        //click the electronics
        clickOnElement(By.linkText("Electronics"));
        // click the camera & photo
        clickOnElement(By.linkText("Camera & photo"));
        //select the product and click it
        clickOnElement(By.linkText("Leica T Mirrorless Digital Camera"));
        //click on add to cart button
        clickOnElement(By.id("add-to-cart-button-16"));
        //click on shopping cart  green button
        clickOnElement(By.linkText("Shopping cart"));
        //check the selected same product have in shopping cart
        String actualMessage = driver.findElement(By.className("product-name")).getText();
        System.out.println("My Message:" + actualMessage);
        Assert.assertEquals(actualMessage, expectedShoppingCartMessage, "Product is not adding in shopping cart");
    }

    @Test
    public static void VerifyUserShouldBeAbleToReferProductToFriend() {
        clickOnElement(By.className("ico-register"));
        //type first name
        typeText(By.id("FirstName"), "Rahul");
        //type last name
        typeText(By.id("LastName"), "shah");
        //type email address
        typeText(By.name("Email"), "rahul123@gmail.com");
        //type password
        typeText(By.id("Password"), "rahul1234");
        //type confirm password
        typeText(By.id("ConfirmPassword"), "rahul1234");
        //click on register submit button
        clickOnElement(By.id("register-button"));
        //click on login button
        clickOnElement(By.className("ico-login"));
        //Type  email address
        typeText(By.xpath("//div[@class='form-fields']/div[1]/input"), "rahul123@gmail.com");
        //Type password
        typeText(By.xpath("//div[@class='form-fields']/div[2]/input"), "rahul1234");
        clickOnElement(By.xpath("//button[@class=\"button-1 login-button\"]"));
        //select product
        clickOnElement(By.xpath("//a[@ href=\"/apple-macbook-pro-13-inch\"]"));
        //click on email a friend
        clickOnElement(By.className("email-a-friend"));
        //Enter friend email
        typeText(By.className("friend-email"), "raj123@gmail.com");
        //type the msg
        typeText(By.id("PersonalMessage"), "pls check this product");
        //click on send email button
        clickOnElement(By.name("send-email"));
        String actualMessage = getTextFromElement(By.xpath("//div[@class='result']"));
        System.out.println("My Message:" + actualMessage);
        Assert.assertEquals(actualMessage, expectedReferProductMessage, "Successfully message send");
    }

    @Test
    public static void VerifyUserShouldBeAbleTOVote() {
        clickOnElement(By.className("ico-register"));
        //type first name
        typeText(By.id("FirstName"), "Rahul");
        //type last name
        typeText(By.id("LastName"), "shah");
        //type email address
        typeText(By.name("Email"), "rahul1234@gmail.com");
        //type password
        typeText(By.id("Password"), "rahul1234");
        //type confirm password
        typeText(By.id("ConfirmPassword"), "rahul1234");
        //click on register submit button
        clickOnElement(By.id("register-button"));
        //click on login button
        clickOnElement(By.className("ico-login"));
        //Type  email address
        //   typeText(By.xpath("//div[@class='form-fields']/div[1]/input"),"rahul123@gmail.com");
        typeText(By.id("Email"), "rahul1234@gmail.com");
        //Type password
        // typeText(By.xpath("//div[@class='form-fields']/div[2]/input"),"rahul1234");
        typeText(By.id("Password"), "rahul1234");
        clickOnElement(By.xpath("//button[@class=\"button-1 login-button\"]"));
        //click on good button
        //  clickOnElement(By.id("pollanswers-2"));
        clickOnElement(By.id("pollanswers-2"));
        //Click on vote button
        clickOnElement(By.id("vote-poll-1"));
        // End of the process Actual message will come
        String actualMessage = getTextFromElement(By.xpath("//span[@class=\"poll-total-votes\"]"));
        System.out.println("My Message:" + actualMessage);
        // Expected message
        Assert.assertEquals(actualMessage, expectedAbleToVoteMessage, "Total Votes are wrong");
    }

    @Test
    public static void VerifyUserShouldBeAbleToCompareProduct() {
        //select product
        clickOnElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        //click compare button
        clickOnElement(By.xpath("//div[@class='compare-products']/button"));
        //select product
        clickOnElement(By.linkText("Gift Cards"));
        clickOnElement(By.linkText("$25 Virtual Gift Card"));
        //click compare product
        clickOnElement(By.xpath("//div[@class='compare-products']/button"));
        //product comparison
        clickOnElement(By.linkText("product comparison"));
        String actualMessage = getTextFromElement(By.linkText("HTC One M8 Android L 5.0 Lollipop"));
        System.out.println("My Message :" + actualMessage);
        String actualMessage1 = getTextFromElement(By.linkText("$25 Virtual Gift Card"));
        System.out.println("My Message :" + actualMessage1);
        //click clear-list button
        clickOnElement(By.className("clear-list"));
        String actualMessage2 = getTextFromElement(By.className("no-data"));
        System.out.println("MyMessage :" + actualMessage2);
        Assert.assertEquals(actualMessage2, expectedCompareProductMessage, "Can not comparing product");
    }
}
