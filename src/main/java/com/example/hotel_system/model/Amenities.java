package com.example.hotel_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "amenities")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "rooms")
@EqualsAndHashCode(exclude = "rooms")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String icon;

    @JsonIgnore
    @ManyToMany(mappedBy = "amenities", fetch = FetchType.LAZY)
    private Set<RoomModel> rooms;
}


