package sample;

import org.junit.jupiter.api.Test;

import cn.renlm.plugins.MyGeneratorUtil;

/**
 * ไปฃ็ ็ๆ
 * 
 * @author Renlm
 *
 */
public class GeneratorTest {

	@Test
	public void run() {
		MyGeneratorUtil.run("mysql.xml");
		MyGeneratorUtil.run("postgres.xml");
	}

}
