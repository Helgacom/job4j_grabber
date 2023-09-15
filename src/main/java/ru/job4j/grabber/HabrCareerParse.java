package ru.job4j.grabber;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.grabber.utils.DateTimeParser;
import ru.job4j.grabber.utils.HabrCareerDateTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {

    private static int count = 5;
    private final DateTimeParser dataTimeParser;

    public HabrCareerParse(DateTimeParser dataTimeParser) {
        this.dataTimeParser = dataTimeParser;
    }

    @Override
    public List<Post> list(String link) throws IOException {
        List<Post> rsl = new ArrayList<>();
        int page = 1;
        do {
            String pages = String.format("%s?page%s", link, page++);
            Connection connection = Jsoup.connect(pages);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> rsl.add(postParse(row)));
            count--;
        } while (count > 0);
        return rsl;
    }

    public static void main(String[] args) throws IOException {
        HabrCareerParse habrCareerParse = new HabrCareerParse(new HabrCareerDateTimeParser());
        List<Post> rsl = habrCareerParse.list("https://career.habr.com/vacancies/java_developer");
        for (Post post : rsl) {
            System.out.println(post);
        }
    }

    private static String retrieveDescription(String link) {
        String description = null;
        try {
            description = Jsoup.connect(link).get().select("meta").get(2).attr("content");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return description;
    }

    private Post postParse(Element el) {
        Element titleElement = el.select(".vacancy-card__title").first();
        Element linkElement = titleElement.child(0);
        Element created = el.select(".vacancy-card__date").first().child(0);
        String pageLink = String.format("%s%s", "https://career.habr.com", linkElement.attr("href"));
        Post post = new Post();
        post.setTitle(titleElement.text());
        post.setLink(pageLink);
        post.setDescription(retrieveDescription(pageLink));
        post.setCreated(dataTimeParser.parse(created.attr("datetime")));
        return post;
    }
}
