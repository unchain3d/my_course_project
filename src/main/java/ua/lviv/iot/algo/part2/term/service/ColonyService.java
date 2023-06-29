package ua.lviv.iot.algo.part2.term.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.lviv.iot.algo.part2.term.writer.EntityReader;
import ua.lviv.iot.algo.part2.term.writer.FilePathManager;
import ua.lviv.iot.algo.part2.term.model.Entity;
import ua.lviv.iot.algo.part2.term.model.Hive;
import ua.lviv.iot.algo.part2.term.model.Colony;
import ua.lviv.iot.algo.part2.term.writer.EntityWriter;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ColonyService {

    private static final String PATH = "src/main/resources/entities/";
    private final HiveService hiveService;
    private final Map<Class<? extends Entity>, List<Entity>> entitiesMap;
    private final AtomicInteger idCounter;

    @Autowired
    public ColonyService(final HiveService hiveService) {
        this.hiveService = hiveService;
        this.entitiesMap = EntityReader.readEntities(PATH);
        this.idCounter = new AtomicInteger(EntityReader.getLastId(Colony.class, PATH));
    }


    public final List<? extends Entity> getColonies() {
        List<Entity> colonyList = entitiesMap.getOrDefault(Colony.class, new ArrayList<>());
        for (Entity entity : colonyList) {
            if (entity instanceof Colony colony) {
                Hive hive = hiveService.getHiveById(colony.getHiveId());
                if (hive != null) {
                    colony.setHive(hive);
                }
            }
        }
        return colonyList;
    }

    public final Colony createColony(final Colony colony) {
        String path = FilePathManager.getFileName(colony);
        Hive hive = hiveService.getHiveById(colony.getHiveId());
        if (hive != null) {
            colony.setHive(hive);
            colony.setId(idCounter.incrementAndGet());
            colony.setHiveId(hive.getId());
            EntityWriter.writeToCSV(colony, path);
            Set<Colony> colonies = hive.getColonies();
            colonies.add(colony);
            hive.setColonies(colonies);
            hiveService.updateHive(hive.getId(), hive);
            entitiesMap.computeIfAbsent(Colony.class, k -> new LinkedList<>()).add(colony);
            return colony;
        } else {
            return null;
        }
    }

    public final Colony getColonyById(final Integer id) {
        return (Colony) entitiesMap.getOrDefault(
                        Colony.class, new ArrayList<>())
                .stream()
                .filter(colony -> colony.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public final Colony updateColony(final Integer id, final Colony colony) {
        Colony colonyFromDB = getColonyById(id);
        if (colonyFromDB != null) {
            colony.setId(id);
            entitiesMap.get(Colony.class).remove(colonyFromDB);
            entitiesMap.get(Colony.class).add(colony);

            Hive hiveFromDB = hiveService.getHiveById(colonyFromDB.getHiveId());
            Hive newHive = hiveService.getHiveById(colony.getHiveId());

            if (newHive == null) {
                return null;
            }

            if (hiveFromDB != null) {
                hiveFromDB.getColonies().remove(colonyFromDB);
            }

            newHive.getColonies().add(colony);

            colony.setHive(newHive);
            colony.setHiveId(newHive.getId());

            EntityReader.updateEntityInCsv(colony, PATH);

            return colony;
        } else {
            return null;
        }
    }

    public final boolean deleteColony(final Integer id) {
        Colony colony = getColonyById(id);
        if (colony != null) {
            entitiesMap.get(Colony.class).remove(colony);
            EntityReader.deleteEntityFromCSV(colony, PATH);

            Hive hive = hiveService.getHiveById(colony.getHiveId());
            if (hive != null) {
                hive.getColonies().remove(colony);
            }

            return true;
        } else {
            return false;
        }
    }

}