package tobyspring.springreview.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.springreview.TestPaymentConfig;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ContextConfiguration(classes = TestPaymentConfig.class)
@ExtendWith(SpringExtension.class)
class PaymentServiceTest {

    @Autowired
    Clock clock;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ExRateProviderStub exRateProviderStub;

    @Test
    void prepareWithStub()  {
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
    void convertedAmountTest()  {
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

    @Test
    void validUntilTest()  {
        //given
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);
        //when
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDateTime expectedUntil = now.plusMinutes(30);
        //then
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedUntil);
    }
}