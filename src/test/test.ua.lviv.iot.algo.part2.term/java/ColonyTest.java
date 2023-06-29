import ua.lviv.iot.algo.part2.term.model.Colony;
import ua.lviv.iot.algo.part2.term.model.Hive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class ColonyTest {

    @Test
    public void testGetHeaders() {
        Colony colony = new Colony();
        String expectedHeaders = "id;beeKind;numberOfBees;yetToBeBornBees;hiveId";
        String actualHeaders = colony.getHeaders();

        Assertions.assertEquals(expectedHeaders, actualHeaders);
    }

    @Test
    public void testToCSV() {

        Colony colony = new Colony(1, "smart", 4526, 352, null, 0);
        colony.setHiveId(1);

        String expectedCSV = "1;smart;4526;352;1";
        String actualCSV = colony.toCSV();

        Assertions.assertEquals(expectedCSV, actualCSV);
    }

    @Test
    public void testGetHiveId() {
        Hive hive = new Hive(1, 17.5, 10.7, 29, 0.7, new HashSet<>());
        Colony colony = new Colony();
        colony.setHive(hive);

        Assertions.assertEquals(1, colony.getHiveId());
    }

    @Test
    public void testSettersAndGetters() {

        Colony colony = new Colony();
        colony.setId(1);
        colony.setBeeKind("smart");
        colony.setNumberOfBees(4526);
        colony.setYetToBeBornBees(352);
        colony.setHiveId(1);

        Assertions.assertEquals(1, colony.getId());
        Assertions.assertEquals("smart", colony.getBeeKind());
        Assertions.assertEquals(4526, colony.getNumberOfBees());
        Assertions.assertEquals(352, colony.getYetToBeBornBees());
        Assertions.assertEquals(1, colony.getHiveId());
    }

    @Test
    public void testHiveAssociation() {
        Hive hive = new Hive(1, 17.5, 10.7, 29, 0.7, new HashSet<>());
        Colony colony = new Colony();
        colony.setHive(hive);

        Assertions.assertEquals(hive, colony.getHive());
    }
}