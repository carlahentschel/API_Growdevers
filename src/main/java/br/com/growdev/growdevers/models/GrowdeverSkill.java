package br.com.growdev.growdevers.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "skills", schema = "growdevers")
public class GrowdeverSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;

    public GrowdeverSkill(String name) {
        this.name = name;
    }
}
