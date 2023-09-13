package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;

public class HabrCareerParse {

    private static final String SOURCE_LINK = "https://career.habr.com";

    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer", SOURCE_LINK);

    private static int count = 5;

    public static void main(String[] args) throws IOException {
        int page = 1;
        do {
            System.out.printf("Page %s%n", page);
            String pages = String.format("%s?page%s", PAGE_LINK, page++);
            Connection connection = Jsoup.connect(pages);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element linkElement = titleElement.child(0);
                String vacancyName = titleElement.text();
                String dt = row.select(".vacancy-card__date")
                        .first()
                        .child(0)
                        .attr("datetime");
                String datetime = new HabrCareerDateTimeParser().parse(dt).toString();
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                System.out.printf("%s [%s] %s%n", vacancyName, datetime, link);
            });
            count--;
        } while (count > 0);
    }
}
