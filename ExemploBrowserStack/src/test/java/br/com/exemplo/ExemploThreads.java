package br.com.exemplo;

import java.util.LinkedList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.Platform;

import br.com.exemplo.po.ExemploPO;
import br.com.exemplo.webdriver.Parallelized;
import br.com.exemplo.webdriver.SetupWebDriverParallelized;

@RunWith(Parallelized.class)
public class ExemploThreads extends SetupWebDriverParallelized {
	ExemploPO home;

	public ExemploThreads(String platform, String browserName, String browserVersion) {
		super(platform, browserName, browserVersion);
	}

	@Parameterized.Parameters
	public static LinkedList<String[]> getEnvironments() throws Exception {
		LinkedList<String[]> env = new LinkedList<String[]>();
		env.add(new String[] { Platform.MAC.toString(), "chrome", "38" });
		env.add(new String[] { Platform.MAC.toString(), "firefox", "31.0" });
		//env.add(new String[] { Platform.WINDOWS.toString(), "ie", "9" });
		//env.add(new String[] { Platform.WINDOWS.toString(), "opera", "12.14" });

		return env;
	}

	@Test
	public void testNavegarNaHome() throws Exception {
		home = new ExemploPO(webDriver);
		
		home.abrirSite();
		home.selecionarArtigo();
		home.fecharArtigo();
		home.fecharSite();
	}
}
