package tobyspring.springreview.learningtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTest {
    //Clock을 이용해서 LocalDateTime.now?
    @Test
    void clock() {
        Clock clock = Clock.systemDefaultZone();

        LocalDateTime now1 = LocalDateTime.now(clock);
        LocalDateTime now2 = LocalDateTime.now(clock);

        Assertions.assertThat(now2).isAfter(now1);
    }
    //Clock을 Test에서 사용할 때 내가 원하는 시간을 지정해서 현재 시간을 가져오게 할 수 있는가?

    @Test
    void fixedClockTest() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        LocalDateTime now1 = LocalDateTime.now(clock);
        LocalDateTime now2 = LocalDateTime.now(clock);

        Assertions.assertThat(now1).isEqualTo(now2);
    }
}
