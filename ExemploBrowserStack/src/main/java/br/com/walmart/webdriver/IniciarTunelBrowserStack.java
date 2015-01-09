package br.com.walmart.webdriver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import br.com.walmart.configuracao.Propriedades;
import static org.junit.Assert.*;

public class IniciarTunelBrowserStack {

	public void start(boolean parallelized) {
		Properties prop = new Propriedades().getProp();
		BufferedReader commandResult = null;
		try {
			Runtime runtime = Runtime.getRuntime();
			String diretorioJarBrowserStack = new File("pom.xml")
					.getAbsolutePath().replace("pom.xml", "src/test/resource/");
			StringBuilder stringBuilder = new StringBuilder();
			stringBuilder.append("java -jar ");
			stringBuilder.append("BrowserStackTunnel.jar ");
			stringBuilder.append(prop.getProperty("prop.selenium.hub.key") + " ");

			// URLs de QA que devem estar no túnnel
			stringBuilder.append("http://www.xpto1.com.br/,80,0, ");
			//stringBuilder.append("http://www.xpto2.com.br/,80,0, ");
			//stringBuilder.append("http://www.xpto3.com.br/,80,0, ");
			//stringBuilder.append("http://www.xpto4.com.br/,80,0, ");

			if (parallelized) {
				stringBuilder.append(" -localIdentifier " + Math.random());
			} else {
				stringBuilder.append(" -force");
			}

			System.out.println(stringBuilder.toString());
			InputStream input = runtime.exec(stringBuilder.toString(), null,
					new File(diretorioJarBrowserStack)).getInputStream();
			Thread.sleep(3000);
			BufferedInputStream buffer = new BufferedInputStream(input);
			commandResult = new BufferedReader(new InputStreamReader(buffer));
		} catch (Exception e) {
			e.printStackTrace();
		}

		String resultado = "";
		boolean check = true;

		try {
			for (int i = 0; (resultado = commandResult.readLine()) != null; i++) {
				System.out.println(resultado);
				if (resultado.contains("There is another JAR already running.")) {
					check = false;
					break;
				}

				if (resultado.contains("Press Ctrl-C to exit")) {
					check = false;
					break;
				}

				if (i >= 300) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (check) {
			fail("Não conseguiu criar o túnel com o BrowserStack");
		}
	}

}
