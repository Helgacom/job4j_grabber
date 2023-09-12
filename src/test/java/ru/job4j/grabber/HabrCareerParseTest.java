package ru.job4j.grabber;

import org.junit.jupiter.api.Test;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.*;

class HabrCareerParseTest {

    @Test
    public void dateTimeParser() {
        String parse = "2022-03-17T14:10:36+03:00";
        HabrCareerDateTimeParser datetime = new HabrCareerDateTimeParser();
        LocalDateTime rsl = datetime.parse(parse);
        LocalDateTime expected = LocalDateTime.parse("2022-03-17T14:10:36");
        assertThat(rsl).isEqualTo(expected);
    }
}