package tobyspring.springreview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import tobyspring.springreview.api.ApiTemplate;
import tobyspring.springreview.api.SimpleApiExecutor;
import tobyspring.springreview.api.SimpleExRateExtractor;
import tobyspring.springreview.exrate.CachedExRateProvider;
import tobyspring.springreview.exrate.RestTemplateExRateProvider;
import tobyspring.springreview.payment.ExRateProvider;
import tobyspring.springreview.exrate.WebApiExRateProvider;
import tobyspring.springreview.payment.PaymentService;

import java.time.Clock;

@Configuration
//@ComponentScan
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public ApiTemplate apiTemplate() {
        return new ApiTemplate();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
