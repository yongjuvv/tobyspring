package tobyspring.springreview;

import java.io.IOException;
import java.math.BigDecimal;

public class SimpleExRateProvider implements ExRateProvider{

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        if(currency.equals("USD")) return BigDecimal.valueOf(1_000);

        throw new IllegalArgumentException("지원되지 않는 통화입니다.");
    }
}
