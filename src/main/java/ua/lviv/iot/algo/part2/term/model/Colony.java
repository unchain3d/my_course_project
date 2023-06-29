package ua.lviv.iot.algo.part2.term.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Component
public class Colony implements Entity {

    private static final String HEADERS = "id;beeKind;numberOfBees;yetToBeBornBees;hiveId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String beeKind;
    private Integer numberOfBees;
    private Integer yetToBeBornBees;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("colonies")
    private Hive hive;

    @JsonIgnore
    private Integer hiveId;

    public int getHiveId() {
        return (hiveId != null) ? hiveId : (hive != null ? hive.getId() : 0);
    }

    @JsonIgnore
    public final String getHeaders() {
        return HEADERS;
    }

    public final String toCSV() {
        return id + ";"
                + beeKind + ";"
                + numberOfBees + ";"
                + yetToBeBornBees + ";"
                + hiveId;
    }

}
