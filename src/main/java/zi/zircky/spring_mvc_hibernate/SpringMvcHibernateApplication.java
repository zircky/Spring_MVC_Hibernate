package zi.zircky.spring_mvc_hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import zi.zircky.spring_mvc_hibernate.config.JavaConfig;
import zi.zircky.spring_mvc_hibernate.service.UserService;

//@SpringBootApplication
public class SpringMvcHibernateApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringMvcHibernateApplication.class, args);
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(JavaConfig.class);

        UserService userService = context.getBean(UserService.class);
    }

}
