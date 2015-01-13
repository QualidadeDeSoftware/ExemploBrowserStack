package br.com.exemplo.po;

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

import br.com.exemplo.webdriver.SetupWebDriver;

public class ExemploPO {
	WebDriver webDriver;
	Wait<WebDriver> wait;
	String winHandleBefore;
	
	public ExemploPO(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public void abrirSite() {
		definirTimeout();
		webDriver.get("http://www.qualidadedesoftware.com.br/");
		winHandleBefore = webDriver.getWindowHandle();
		wait.until(visibilityOfElementLocated(By.xpath("//div[1]/article/div[2]/h3/a")));
		tirarEvidencia();
	}

	public void tirarEvidencia() {
		webDriver = new Augmenter().augment(webDriver);
		((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
	}

	public void selecionarArtigo() {
		webDriver.findElement(By.xpath("//div[1]/article/div[2]/h3/a")).click();
		
		for(String winHandle : webDriver.getWindowHandles()){
			webDriver.switchTo().window(winHandle);
		}
		tirarEvidencia();
	}

	public void fecharArtigo() {
		webDriver.close();
	}

	public void fecharSite() {
		webDriver.switchTo().window(winHandleBefore);
		webDriver.close();
	}
	
	private void definirTimeout() {
		wait = new FluentWait<WebDriver>(webDriver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.MILLISECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
	}
	
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By by) {
		return new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				return element.isDisplayed() ? element : null;
			}
		};
	}

	public void gerarErro() {
		webDriver.findElement(By.xpath("//div[1]/article/div[2]/h3/a")).click();
	}
}
