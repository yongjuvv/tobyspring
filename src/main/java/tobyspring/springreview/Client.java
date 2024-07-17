package tobyspring.springreview;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import tobyspring.springreview.payment.Payment;
import tobyspring.springreview.payment.PaymentService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.valueOf(59.6));
        System.out.println("payment = " + payment);

        TimeUnit.SECONDS.sleep(3);
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.valueOf(59.6));
        System.out.println("payment2 = " + payment2);
    }
}
