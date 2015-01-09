package br.com.exemplo;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;

import br.com.walmart.webdriver.Parallelized;
import br.com.walmart.webdriver.SetupWebDriverParallelized;

@RunWith(Parallelized.class)
public class ExemploThreads extends SetupWebDriverParallelized {

	public ExemploThreads(String platform, String browserName, String browserVersion) {
		super(platform, browserName, browserVersion);
	}

	@Parameterized.Parameters
	public static LinkedList<String[]> getEnvironments() throws Exception {
		LinkedList<String[]> env = new LinkedList<String[]>();
		env.add(new String[] { Platform.WINDOWS.toString(), "chrome", "27" });
		env.add(new String[] { Platform.WINDOWS.toString(), "firefox", "20" });
		env.add(new String[] { Platform.WINDOWS.toString(), "ie", "9" });
		env.add(new String[] { Platform.WINDOWS.toString(), "opera", "12.14" });

		return env;
	}

	@Test
	public void testBuscaComSucesso() throws Exception {
	    webDriver.get("https://www.google.com.br/?gws_rd=ssl");
	    webDriver.findElement(By.id("gbqfq")).clear();
	    webDriver.findElement(By.id("gbqfq")).sendKeys("teste browserstack");
	    webDriver.findElement(By.id("gbqfb")).click();
	    for (int second = 0;; second++) {
	    	if (second >= 60) fail("timeout");
	    	try { if ("www.browserstack.com/".equals(webDriver.findElement(By.cssSelector("cite._Rm")).getText())) break; } catch (Exception e) {}
	    	Thread.sleep(1000);
	    }
	}

}
