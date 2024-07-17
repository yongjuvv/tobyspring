package tobyspring.springreview.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

//@Component
public class PaymentService {
    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(ExRateProvider exRateProvider, Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(Long orderId, String currency, BigDecimal foreignCurrencyAmount) throws IOException {
        //환율 가져오기
        BigDecimal exRate = exRateProvider.getExRate(currency);

        //금액 계산
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);

        //유효 시간 계산
        LocalDateTime validUntil = LocalDateTime.now(clock).plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount,exRate, convertedAmount, validUntil);
    }

}
