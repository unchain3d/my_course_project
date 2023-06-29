package ua.lviv.iot.algo.part2.term.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part2.term.model.Entity;
import ua.lviv.iot.algo.part2.term.model.Hive;
import ua.lviv.iot.algo.part2.term.service.HiveService;

import java.util.LinkedList;
import java.util.List;

@RequestMapping("/hives")
@RestController
public class HiveController {

    @Autowired
    private final HiveService hiveService;

    @Autowired
    public HiveController(final HiveService hiveService) {
        this.hiveService = hiveService;
    }

    @GetMapping
    public final List<? extends Entity> getHives() {
        return new LinkedList<>(hiveService.getHives());
    }

    @GetMapping(path = "/{id}")
    public final Object getHiveById(final @PathVariable("id") Integer id) {
        return hiveService.getHiveById(id) == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : hiveService.getHiveById(id);
    }

    @PostMapping(produces = {"application/json"})
    public final Hive createHive(final @RequestBody Hive hive) {
        return hiveService.createHive(hive);
    }

    @PutMapping(path = "/{id}")
    public final ResponseEntity<Hive> updateHive(final @PathVariable("id") Integer id, final @RequestBody Hive hive) {
        Hive updatedHive = hiveService.updateHive(id, hive);
        return updatedHive == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(updatedHive);
    }

    @DeleteMapping(path = "/{id}")
    public final ResponseEntity<Void> deleteHive(final @PathVariable("id") Integer id) {
        return hiveService.deleteHive(id)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}