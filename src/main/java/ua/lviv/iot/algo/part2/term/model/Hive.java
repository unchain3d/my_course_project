package ua.lviv.iot.algo.part2.term.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Component
public class Hive implements Entity {

    private static final String HEADERS = "id;longitude;latitude;temperature;humidity";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double longitude;
    private double latitude;
    private double temperature;
    private double humidity;

    @JsonIgnoreProperties("hive")
    @OneToMany(mappedBy = "hive", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Colony> colonies = new HashSet<>();

    @JsonIgnore
    public final String getHeaders() {
        return HEADERS;
    }

    public final String toCSV() {
        return id + ";" + longitude + ";" + latitude + ";" + temperature + ";" + humidity;
    }
}
