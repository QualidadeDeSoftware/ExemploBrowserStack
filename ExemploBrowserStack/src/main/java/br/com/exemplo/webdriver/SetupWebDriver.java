package br.com.exemplo.webdriver;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.exemplo.configuracao.Propriedades;

public class SetupWebDriver extends TestCase {
	static final Properties prop = new Propriedades().getProp();
	
	static protected WebDriver webDriver = null;

	@Override
	public void setUp() throws Exception {
		super.setUp();

		String URL = "http://" + prop.getProperty("prop.selenium.hub.username") + ":" 
				+ prop.getProperty("prop.selenium.hub.key")
				+ prop.getProperty("prop.selenium.hub.url");
		
		if (prop.getProperty("prop.selenium.capability.remote").equals("true")) {
			DesiredCapabilities capability = new DesiredCapabilities();
			capability.setCapability("browser", prop.getProperty("prop.selenium.capability.browser"));
			capability.setCapability("browser_version", prop.getProperty("prop.selenium.capability.browser_version"));
			
			capability.setCapability("os", prop.getProperty("prop.selenium.capability.os"));
			capability.setCapability("os_version", prop.getProperty("prop.selenium.capability.os_version"));
			
			capability.setCapability("browserstack.debug", prop.getProperty("prop.selenium.capability.debug"));
			capability.setCapability("browserstack.local", "true");
			capability.setCapability("project", "Projeto Exemplo");
			capability.setCapability("speed", "1");

			new IniciarTunelBrowserStack().start(false);
			webDriver = new RemoteWebDriver(new URL(URL), capability);
			webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			webDriver.manage().window().maximize();
		} else {
			webDriver = new FirefoxDriver();
		}
	}
	
	@Override
	public void tearDown() throws Exception {
		super.tearDown();
		webDriver.quit();
	}

}
