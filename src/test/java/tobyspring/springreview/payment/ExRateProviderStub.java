package tobyspring.springreview.payment;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tobyspring.springreview.TestObjectFactory;

import java.io.IOException;
import java.math.BigDecimal;


public class ExRateProviderStub implements ExRateProvider {

    private BigDecimal exRate;

    public ExRateProviderStub(BigDecimal exRate) {
        this.exRate = exRate;
    }

    @Override
    public BigDecimal getExRate(String currency) throws IOException {
        return exRate;
    }

    public BigDecimal getExRate() {
        return exRate;
    }

    public void setExRate(BigDecimal exRate) {
        this.exRate = exRate;
    }
}
