package tobyspring.springreview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.springreview.exrate.CachedExRateProvider;
import tobyspring.springreview.exrate.WebApiExRateProvider;
import tobyspring.springreview.payment.ExRateProvider;
import tobyspring.springreview.payment.ExRateProviderStub;
import tobyspring.springreview.payment.PaymentService;

import java.math.BigDecimal;

@Configuration
public class TestObjectFactory {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }

}
