import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lviv.iot.algo.part2.term.model.Colony;
import ua.lviv.iot.algo.part2.term.model.Hive;

import java.util.HashSet;
import java.util.Set;

public class HiveTest {

    @Test
    public void testGetHeaders() {
        Hive hive = new Hive();
        String expectedHeaders = "id;longitude;latitude;temperature;humidity";
        String actualHeaders = hive.getHeaders();

        Assertions.assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    public void testToCSV() {
        Hive hive = new Hive(1, 17.5, 10.7, 29, 0.7, new HashSet<>());
        String expectedCSV = "1;17.5;10.7;29;0.7";
        String actualCSV = hive.toCSV();

        Assertions.assertEquals(expectedCSV, actualCSV);
    }

    @Test
    public void testSettersAndGetters() {
        Hive hive = new Hive();
        hive.setId(1);
        hive.setLongitude(17.5);
        hive.setLatitude(10.7);
        hive.setTemperature(29);
        hive.setHumidity(0.7);

        Assertions.assertEquals(1, hive.getId());
        Assertions.assertEquals(17.5, hive.getLongitude());
        Assertions.assertEquals(10.7, hive.getLatitude());
        Assertions.assertEquals(29, hive.getTemperature());
        Assertions.assertEquals(0.7, hive.getHumidity());
    }

    @Test
    public void testColonies() {
        Set<Colony> colonies = new HashSet<>();
        Colony colony1 = new Colony();
        Colony colony2 = new Colony();

        colonies.add(colony1);
        colonies.add(colony2);

        Hive hive = new Hive();
        hive.setColonies(colonies);

        Assertions.assertEquals(colonies, hive.getColonies());
    }
}