package br.com.exemplo;

import org.junit.Test;

import br.com.exemplo.po.ExemploPO;
import br.com.exemplo.webdriver.SetupWebDriver;

public class ExemploErro extends SetupWebDriver {
	ExemploPO home;
	
	public ExemploErro() {
	}
	
	@Test
	public void testNavegarNaHome() throws Exception {
		home = new ExemploPO(webDriver);
		
		home.abrirSite();
		home.selecionarArtigo();
		home.gerarErro();
		home.fecharArtigo();
		home.fecharSite();
	}
}
