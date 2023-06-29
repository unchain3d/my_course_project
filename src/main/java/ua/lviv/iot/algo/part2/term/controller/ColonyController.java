package ua.lviv.iot.algo.part2.term.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.iot.algo.part2.term.model.Entity;
import ua.lviv.iot.algo.part2.term.model.Colony;
import ua.lviv.iot.algo.part2.term.service.ColonyService;

import java.util.List;

@RestController
@RequestMapping("/colonies")
public class ColonyController {

    @Autowired
    private final ColonyService colonyService;

    public ColonyController(final ColonyService colonyService) {
        this.colonyService = colonyService;
    }

    @GetMapping
    public final List<? extends Entity> getColonies() {
        return colonyService.getColonies();
    }

    @GetMapping(path = "/{id}")
    public final Object getColonyById(final @PathVariable("id") Integer id) {
        return colonyService.getColonyById(id) == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : colonyService.getColonyById(id);
    }

    @PostMapping(produces = {"application/json"})
    public final ResponseEntity<Colony> createColony(final @RequestBody Colony colony) {
        Colony createdColony = colonyService.createColony(colony);
        if (createdColony != null) {
            return ResponseEntity.ok(createdColony);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping(path = "/{id}")
    public final ResponseEntity<Colony> updateColony(final @PathVariable("id") Integer id,
                                                     final @RequestBody Colony colony) {
        Colony updatedColony = colonyService.updateColony(id, colony);
        return updatedColony == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).build()
                : ResponseEntity.ok(updatedColony);
    }

    @DeleteMapping(path = "/{id}")
    public final ResponseEntity<Void> deleteColony(final @PathVariable("id") Integer id) {
        return colonyService.deleteColony(id)
                ? ResponseEntity.status(HttpStatus.OK).build()
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}