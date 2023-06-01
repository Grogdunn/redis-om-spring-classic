package it.grogdunn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(Config.class);
        final var bean = ac.getBean(Facade.class);
        try {
            bean.generateScrapData();
        } catch (Exception e) {
            System.out.println("Boom, YOLO");
            e.printStackTrace();
        }
        try {
            bean.enumerateSql();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
