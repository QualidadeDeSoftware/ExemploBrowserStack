package br.com.exemplo.webdriver;

import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import br.com.exemplo.configuracao.Propriedades;

public abstract class SetupWebDriverParallelized extends TestCase {
	protected WebDriver webDriver = null;
	protected String    platform;
	protected String    browserName;
	protected String    browserVersion;
	private final Properties prop = new Propriedades().getProp();
	
	public SetupWebDriverParallelized(String platform, String browserName, String browserVersion) {
		this.platform       = platform;
		this.browserName    = browserName;
		this.browserVersion = browserVersion;
	}
	
	public SetupWebDriverParallelized(String name) {
		super(name);
	}
	
	public SetupWebDriverParallelized() {
	}

	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capability = new DesiredCapabilities();

		String URL = "http://" + prop.getProperty("prop.selenium.hub.username") + ":" 
				+ prop.getProperty("prop.selenium.hub.key")
				+ prop.getProperty("prop.selenium.hub.url");
		
		capability.setCapability("platform", platform);
		capability.setCapability("browser", browserName);
		capability.setCapability("browserVersion", browserVersion);
		
		capability.setCapability("browserstack.debug", prop.getProperty("prop.selenium.capability.debug"));
		capability.setCapability("browserstack.local", "true");
		capability.setCapability("project", "Projeto Exemplo");
		capability.setCapability("speed", "1");

		new IniciarTunelBrowserStack().start(true);
		webDriver = new RemoteWebDriver(new URL(URL), capability);
		webDriver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
	}

	@After
	public void tearDown() throws Exception {
		webDriver.quit();
	}
}