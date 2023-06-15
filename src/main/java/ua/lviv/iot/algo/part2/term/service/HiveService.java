package ua.lviv.iot.algo.part2.term.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part2.term.writer.EntityReader;
import ua.lviv.iot.algo.part2.term.writer.EntityWriter;
import ua.lviv.iot.algo.part2.term.writer.FilePathManager;
import ua.lviv.iot.algo.part2.term.model.Entity;
import ua.lviv.iot.algo.part2.term.model.Hive;
import ua.lviv.iot.algo.part2.term.model.Colony;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class HiveService {

    private static final String PATH = "src/main/resources/entities/";
    private final Map<Class<? extends Entity>, List<Entity>> entitiesMap;
    private final AtomicInteger idCounter;

    @Autowired
    public HiveService() {
        this.entitiesMap = EntityReader.readEntities(PATH);
        this.idCounter = new AtomicInteger(EntityReader.getLastId(Hive.class, PATH));
    }


    public List<? extends Entity> getHives() {
        List<Entity> hiveList = entitiesMap.getOrDefault(Hive.class, new ArrayList<>());
        for (Entity entity : hiveList) {
            if (entity instanceof Hive hive) {
                Set<Colony> colonyList = getColoniesForHive(hive);
                hive.setColonies(colonyList);
            }
        }
        return hiveList;
    }

    private Set<Colony> getColoniesForHive(Hive hive) {
        Set<Colony> colonyList = new HashSet<>();
        List<Entity> colonyEntities = entitiesMap.getOrDefault(Colony.class, new ArrayList<>());
        for (Entity entity : colonyEntities) {
            if (entity instanceof Colony colony && colony.getHiveId() == hive.getId()) {
                colonyList.add(colony);
            }
        }
        return colonyList;
    }

    public final Hive createHive(final Hive hive) {
        hive.setId(idCounter.incrementAndGet());
        entitiesMap.computeIfAbsent(Hive.class, k -> new LinkedList<>()).add(hive);
        String path = FilePathManager.getFileName(hive);
        EntityWriter.writeToCSV(hive, path);
        return hive;
    }

    public final Hive getHiveById(final Integer id) {
        return (Hive) entitiesMap
                .getOrDefault(Hive.class, new ArrayList<>())
                .stream()
                .filter(hive -> hive.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public final Hive updateHive(final Integer id, final Hive hive) {
        Hive hiveFromDB = getHiveById(id);
        if (hiveFromDB != null) {
            hive.setId(id);
            Set<Colony> coloniesToUpdate = hiveFromDB.getColonies();
            for (Colony colony : coloniesToUpdate) {
                colony.setHive(hive);
            }
            entitiesMap.get(Hive.class).remove(hiveFromDB);
            entitiesMap.get(Hive.class).add(hive);
            EntityReader.updateEntityInCsv(hive, PATH);
            for (Colony colony : coloniesToUpdate) {
                EntityReader.updateEntityInCsv(colony, PATH);
            }
            return hive;
        } else {
            return null;
        }
    }

    public final boolean deleteHive(final Integer id) {
        Hive hive = getHiveById(id);
        if (hive != null) {
            entitiesMap.get(Hive.class).remove(hive);
            EntityReader.deleteEntityFromCSV(hive, PATH);

            hive.getColonies().forEach(colony -> {
                colony.setHive(null);
                colony.setHiveId(0);
                EntityReader.updateEntityInCsv(colony, PATH);
            });

            return true;
        } else {
            return false;
        }
    }
}
