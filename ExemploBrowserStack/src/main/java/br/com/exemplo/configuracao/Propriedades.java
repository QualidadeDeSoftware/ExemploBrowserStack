package br.com.exemplo.configuracao;

import java.io.FileInputStream;
import java.util.Properties;

public class Propriedades {
	public static Properties getProp() {
		Properties props = null;
		try {
			props = new Properties();
			FileInputStream file = new FileInputStream("selenium.properties");
			props.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}
}
