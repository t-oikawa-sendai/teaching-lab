package jp.kwebs.pcshop.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jp.kwebs.pcshop.service.PcService;
import jp.kwebs.pcshop.util.Util;

@Configuration
public class DatabaseInitializer {

	@Bean
	CommandLineRunner init(PcService ps) {
		CommandLineRunner clr = s->ps.createAllPcs(Util.getPcs());
		return clr;
	}
}
