package br.com.exemplo;

import org.junit.Test;

import br.com.exemplo.po.ExemploPO;
import br.com.exemplo.webdriver.SetupWebDriver;

public class ExemploSingular extends SetupWebDriver {
	ExemploPO home;
	
	public ExemploSingular() {
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
