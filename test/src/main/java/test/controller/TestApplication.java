package test.controller;

// import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.boot.CommandLineRunner;
import test.flask.FlaskRunner;

@SpringBootApplication
@ComponentScan("test.flask")
public class TestApplication implements CommandLineRunner {
	// public class TestApplication {
	private final FlaskRunner flaskRunner;

	public TestApplication(FlaskRunner flaskRunner) {
		this.flaskRunner = flaskRunner; // 여기에서 초기화
	}

	public static void main(String[] args) {
		SpringApplication.run(TestApplication.class, args);
	}

	@Override
	public void run(String... args) {
		startFlask(8080);
	}

	private void startFlask(int port) {
		flaskRunner.startFlaskServer(port);
	}

}