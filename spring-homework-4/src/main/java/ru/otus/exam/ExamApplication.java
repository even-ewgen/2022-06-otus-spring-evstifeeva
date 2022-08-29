package ru.otus.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import ru.otus.exam.service.ExamService;

@SpringBootApplication
@EnableConfigurationProperties
public class ExamApplication {

	public static void main(String[] args) {
		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

		ApplicationContext context = SpringApplication.run(ExamApplication.class, args);

		ExamService examineService = context.getBean(ExamService.class);
		examineService.exam();
	}

}
