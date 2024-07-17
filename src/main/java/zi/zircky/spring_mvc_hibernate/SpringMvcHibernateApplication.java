package zi.zircky.spring_mvc_hibernate;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import zi.zircky.spring_mvc_hibernate.config.JavaConfig;

//@SpringBootApplication
public class SpringMvcHibernateApplication {

    public static void main(String[] args) {
        //SpringApplication.run(SpringMvcHibernateApplication.class, args);
        AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(JavaConfig.class);

        //UserService userService = context.getBean(UserService.class);
    }

}
