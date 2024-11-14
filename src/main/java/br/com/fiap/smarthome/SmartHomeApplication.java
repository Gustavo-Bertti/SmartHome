package br.com.fiap.smarthome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
@Controller
@EnableCaching
public class SmartHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartHomeApplication.class, args);
	}

	@RequestMapping
	@ResponseBody
	public String home() {
		return "SmartHome 14/11";
	}

}
