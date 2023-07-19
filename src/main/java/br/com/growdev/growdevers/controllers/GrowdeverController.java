package br.com.growdev.growdevers.controllers;

import br.com.growdev.growdevers.database.DataBase;
import br.com.growdev.growdevers.dtos.*;
import br.com.growdev.growdevers.enums.EStatus;
import br.com.growdev.growdevers.models.Growdever;
import br.com.growdev.growdevers.repositories.GrowdeverRepository;
import br.com.growdev.growdevers.repositories.specifications.GrowdeverSpecification;
import jakarta.transaction.Transactional;
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
        var specification = GrowdeverSpecification.filterByNameAndStatus(name, status);

        var data = growdeverRepository.findAll(specification).stream().map(
                (growdever) -> new GrowdeverList(growdever)
        ).toList();

//        var data = dataDB.stream().filter(growdever -> {
//            // nome e status ao mesmo tempo
//            if(name != null && status != null) {
//                return growdever.getName().toLowerCase().contains(name.toLowerCase()) && growdever.getStatus().equals(status);
//            }
//
//
//            if(name != null || status != null) {
//                var filterByName = false;
//                var filterByStatus = false;
//                if(name != null) {
//                    filterByName = growdever.getName().toLowerCase().contains(name.toLowerCase());
//                }
//                if(status != null) {
//                    filterByStatus = growdever.getStatus().equals(status);
//                }
//
//            }
//            return true;}).map((growdever) -> new GrowdeverList(growdever)).toList();

        return ResponseEntity.ok(data);
    }

    @GetMapping("/{id}")
    public ResponseEntity getGrowdever(@PathVariable UUID id) {
        var optional = growdeverRepository.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }

        var growDetail = new GrowdeverDetail(optional.get());

        return ResponseEntity.ok(growDetail);
    }

    @PostMapping
    @Transactional
    public ResponseEntity createGrowdever(@RequestBody @Valid CreateGrowdeverDTO data) {
        if(growdeverRepository.existsByCpf(data.cpf())) {
            return ResponseEntity.badRequest().body(new ErrorData("CPF já cadastrado."));
        }

        if(growdeverRepository.existsByEmail(data.email())) {
           return ResponseEntity.badRequest().body(new ErrorData("E-mail já cadastrado."));
        }


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

        if (!growdeverRepository.existById(id)) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }

        growdeverRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity updateGrowdever(@PathVariable UUID id, @RequestBody UpdateGrowdever data) {

        if (!growdeverRepository.existById(id)) {
            return ResponseEntity.badRequest().body(new ErrorData("Growdever não localizado."));
        }
        if(data.email() != null && growdeverRepository.existsByEmail(data.email())) {
            return ResponseEntity.badRequest().body(new ErrorData("Já existe um growdever com este e-mail. "));
        }
        var growdever = growdeverRepository.getReferenceById(id);
        growdever.updateInfo(data);
        //growdeverRepository.save(growdever);

        return ResponseEntity.noContent().build();
    }

}
