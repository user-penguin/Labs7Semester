package data.realisation;

import data.entity.Tag;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class StandardBookTest {

    @Test
    public void toJSON() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(Tag.NOVEL);
        tags.add(Tag.DETECTIVE);
        tags.add(Tag.SCIFI);
        StandardBook book = new StandardBook("Nilod", "shtolc", "2003", tags);
        book.setId(15);
        System.out.println(book.toJSON().toString());
    }

    @Test
    public void toBook() throws ParseException {
        JSONObject bookJSON = (JSONObject) new JSONParser().parse("{\n" +
                "      \"id\": 2,\n" +
                "      \"name\": \"Vij\",\n" +
                "      \"author\": \"Gogol\",\n" +
                "      \"year\": \"1835\",\n" +
                "      \"tags\":\n" +
                "      [\n" +
                "        \"FANTASY\",\n" +
                "        \"NOVEL\"\n" +
                "      ]\n" +
                "    }");
        StandardBook book = StandardBook.toBook(bookJSON);
        System.out.println("Job done");
    }
}