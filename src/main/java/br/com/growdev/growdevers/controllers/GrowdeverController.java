package br.com.growdev.growdevers.controllers;

import br.com.growdev.growdevers.database.DataBase;
import br.com.growdev.growdevers.dtos.*;
import br.com.growdev.growdevers.enums.EStatus;
import br.com.growdev.growdevers.models.Growdever;
import br.com.growdev.growdevers.repositories.GrowdeverRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/growdevers")
public class GrowdeverController {

    @Autowired // injeção de dependencia
    private GrowdeverRepository growdeverRepository;

    @GetMapping
    public ResponseEntity<List<GrowdeverList>> listGrowdevers(@RequestParam(required = false) String name, @RequestParam(required = false) EStatus status) {
        //var data = DataBase.getGrowdevers().stream().map((growdever) -> new GrowdeverList(growdever)).toList();

        var dataDB = growdeverRepository.findAll();

        var data = dataDB.stream().filter(growdever -> {
            // nome e status ao mesmo tempo
            if(name != null && status != null) {
                return growdever.getName().toLowerCase().contains(name.toLowerCase()) && growdever.getStatus().equals(status);
            }


            if(name != null || status != null) {
                var filterByName = false;
                var filterByStatus = false;
                if(name != null) {
                    filterByName = growdever.getName().toLowerCase().contains(name.toLowerCase());
                }
                if(status != null) {
                    filterByStatus = growdever.getStatus().equals(status);
                }

            }
            return true;}).map((growdever) -> new GrowdeverList(growdever)).toList();

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity getGrowdever(@PathVariable UUID id) {
        var growdever = DataBase.getGrowdeverById(id);

        if (growdever == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }

        var growDetail = new GrowdeverDetail(growdever);

        return ResponseEntity.ok(growDetail);
    }

    @PostMapping
    public ResponseEntity createGrowdever(@RequestBody @Valid CreateGrowdeverDTO data) {
//        if(DataBase.growdeverExistByCPF(data.cpf())) {
//            return ResponseEntity.badRequest().body(new ErrorData("CPF já cadastrado."));
//        }
//
//        if(DataBase.growdeverExistByEmail(data.email())) {
//            return ResponseEntity.badRequest().body(new ErrorData("E-mail já cadastrado."));
//        }

        var growdever = new Growdever(
                data.name(),
                data.email(),
                data.cpf(),
                data.phone(),
                data.status()
        );

        growdeverRepository.save(growdever);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteGrowdever(@PathVariable UUID id) {
        var growdever = DataBase.getGrowdeverById(id);

        if (growdever == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }

        DataBase.removeGrowdever(growdever);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateGrowdever(@PathVariable UUID id, @RequestBody UpdateGrowdever data) {
        var growdever = DataBase.getGrowdeverById(id);

        if (growdever == null) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }
        if(data.email() != null && DataBase.growdeverExistByEmail(data.email())) {
            return ResponseEntity.badRequest().body(new ErrorData("Já existe um growdever com este e-mail. "));
        }

        growdever.updateInfo(data);
        DataBase.updateGrowdever(growdever);

        return ResponseEntity.noContent().build();
    }

}
