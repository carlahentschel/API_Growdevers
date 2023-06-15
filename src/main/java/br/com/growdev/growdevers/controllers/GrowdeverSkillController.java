package br.com.growdev.growdevers.controllers;

import br.com.growdev.growdevers.database.DataBase;
import br.com.growdev.growdevers.dtos.AddSkills;
import br.com.growdev.growdevers.dtos.ErrorData;
import br.com.growdev.growdevers.models.GrowdeverSkill;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("growdevers")
public class GrowdeverSkillController {

    @PostMapping("/{idGrowdever}/skills")
    public ResponseEntity createSkills(@PathVariable UUID idGrowdever, @RequestBody @Valid AddSkills newSkills) {
        var growdever = DataBase.getGrowdeverById(idGrowdever);

        if (growdever == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }

        for (String skill : newSkills.skills()) {
            var findSkill = growdever.getSkills().stream().filter(gs -> gs.getName().equalsIgnoreCase(skill)).findAny();

            if (findSkill.isEmpty()) {
                growdever.getSkills().add(new GrowdeverSkill(skill));
            }
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{idGrowdever}/skills/{skill}")
    public ResponseEntity deleteSkill(@PathVariable UUID idGrowdever, @PathVariable String skill) {
        var growdever = DataBase.getGrowdeverById(idGrowdever);

        if (growdever == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }

        var skillOptional = growdever.getSkills().stream().filter(gs -> gs.getName().equalsIgnoreCase(skill)).findAny();

        if (skillOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("Skill não encontrada."));
        }

        growdever.getSkills().remove(skillOptional.get());

        return ResponseEntity.ok().build();
    }

    // filtrar os growdevers por nome e por status

}
