package tobyspring.springreview.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.springreview.TestObjectFactory;
import tobyspring.springreview.exrate.WebApiExRateProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = TestObjectFactory.class)
@ExtendWith(SpringExtension.class)
class PaymentServiceTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    ExRateProviderStub exRateProviderStub;

    @Test
    void prepareWithStub() throws IOException {
        //given
        BigDecimal testExRate = BigDecimal.valueOf(1_000);

        //when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        //then
        Assertions.assertThat(payment.getExRate()).isNotNull();
        Assertions.assertThat(payment.getExRate()).isEqualByComparingTo(testExRate);
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(testExRate.multiply(BigDecimal.TEN));
        Assertions.assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
        Assertions.assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void convertedAmountTest() throws IOException {
        //1. exRate : 1000

        //given

        //when
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //then
        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(1_000).multiply(BigDecimal.TEN));
        //2. exRate : 500
        //given
        exRateProviderStub.setExRate(BigDecimal.valueOf(500));

        //when
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //then
        Assertions.assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(500).multiply(BigDecimal.TEN));

    }
}