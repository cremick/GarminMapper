package com.camrynremick.projects;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

class Driver {
    public static void run() {
        WebDriver driver = new ChromeDriver();

        try {
            // Maximize the browser window
            driver.manage().window().maximize();

            // Open the Garmin Connect
            driver.get("https://sso.garmin.com/portal/sso/en-US/sign-in?clientId=GarminConnect&service=https%3A%2F%2Fconnect.garmin.com%2Fmodern");

            // Declare wait object
            Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));

            // Log in to the website
            WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("email")));
            WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));

            // Fill in the form
            usernameField.sendKeys("camrynremick@gmail.com");
            passwordField.sendKeys("FastLion14");

            // Click login button
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@type=\"submit\"]")));
            loginButton.click();

            // Wait for the page to load after login
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("gridContainer")));

            // Scroll down the page to trigger dynamic content loading
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

            // Wait for more content to load
            TimeUnit.SECONDS.sleep(5);

            // Now grab the fully loaded page source with dynamic content
            String pageSource = driver.getPageSource();

            // Use JSoup to parse the page
            Document doc = Jsoup.parse(pageSource);

            System.out.println(doc);
            
            /*
             * 
            // Select and process the data (modify the selector according to your need)
            doc.select("div.someClass").forEach(element -> {
                System.out.println("Element Text: " + element.text());
            });
            */

        } catch (Exception e) {
            System.out.println("ERROR :(");
            e.printStackTrace();
        } finally {
            // Quit the WebDriver session
            driver.quit();
        }
    }
}