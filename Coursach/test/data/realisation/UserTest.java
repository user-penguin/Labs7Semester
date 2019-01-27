package data.realisation;

import data.entity.Tag;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserTest {
    @Test
    public void toJSONTransformTest() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(Tag.DETECTIVE);
        tags.add(Tag.NOVEL);
        User user = new User("Ivan", "koko", tags);
        user.toJSON();
    }

    @Test
    public void getterTest() {
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(Tag.DETECTIVE);
        tags.add(Tag.NOVEL);
        User user = new User("Ivan", "koko", tags);
        String trueName = "Ivan";
        String expected = user.getUserName();
        assertEquals(trueName, expected);
    }

    @Test
    public void toUserFromJSONTest() throws ParseException {
        JSONObject jsonObject = (JSONObject) new JSONParser().parse("{\n" +
                "      \"name\": \"Ivan\",\n" +
                "      \"id\": 12,\n" +
                "      \"password\": \"6776767\",\n" +
                "      \"tags\":\n" +
                "      [\n" +
                "        \"FANTASY\",\n" +
                "        \"NOVEL\"\n" +
                "      ]\n" +
                "    }");
        User user = User.toUser(jsonObject);
        System.out.println("Complete transform");
    }
}