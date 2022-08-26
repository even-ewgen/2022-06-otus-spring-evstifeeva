package ru.otus.exam;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.exam.service.ExamService;

@Configuration
@ComponentScan
@PropertySource("classpath:exam.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);

        ExamService service = context.getBean(ExamService.class);
        service.exam();
    }
}
