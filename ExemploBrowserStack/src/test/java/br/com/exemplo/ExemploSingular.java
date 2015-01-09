package br.com.exemplo;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import br.com.walmart.webdriver.SetupWebDriver;

public class ExemploSingular extends SetupWebDriver {
	Wait<WebDriver> wait;
	
	public ExemploSingular() {
	}
	
	@Test
	public void testNavegarNaHome() throws Exception {
		wait = new FluentWait<WebDriver>(webDriver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
		
		webDriver.get("http://www.qualidadedesoftware.com.br/");
		String winHandleBefore = webDriver.getWindowHandle();
		
		for (int i = 0; i < 1; i++) {
			wait.until(visibilityOfElementLocated(By.xpath("//div[1]/article/div[2]/h3/a")));
			webDriver = new Augmenter().augment(webDriver);
			((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			
			webDriver.findElement(By.xpath("//div[1]/article/div[2]/h3/a")).click();
			
			for(String winHandle : webDriver.getWindowHandles()){
				webDriver.switchTo().window(winHandle);
			}

			webDriver = new Augmenter().augment(webDriver);
			((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
			webDriver.close();
			
			webDriver.switchTo().window(winHandleBefore);
		}
		webDriver.close();
	}
	
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				return element.isDisplayed() ? element : null;
			}
		};
	}
}
