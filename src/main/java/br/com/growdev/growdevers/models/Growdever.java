package br.com.growdev.growdevers.models;

import br.com.growdev.growdevers.dtos.UpdateGrowdever;
import br.com.growdev.growdevers.enums.EStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Entity
@Table(name = "growdevers", schema = "growdevers")
public class Growdever {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private  String email;
    private String cpf;
    @Column(name="phone")
    private String numberPhone;
    @Enumerated(EnumType.STRING)
    private EStatus status;
    private ArrayList<GrowdeverSkill> skills;

    public Growdever(String name, String email, String cpf, String numberPhone, EStatus status) {
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.numberPhone = numberPhone;
        this.status = status;
        skills = new ArrayList<>();
    }

    public void updateInfo(UpdateGrowdever data) {

        if(data.name() != null) {
            name = data.name();
        }
        if(data.email() != null) {
            email = data.email();
        }

        if(data.phone() != null) {
            numberPhone = data.phone();
        }

        if(data.status() != null) {
            status = data.status();
        }
    }
}
