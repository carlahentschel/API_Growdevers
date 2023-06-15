package br.com.growdev.growdevers.database;

import br.com.growdev.growdevers.models.Growdever;

import java.util.ArrayList;
import java.util.UUID;

public class DataBase {
    private static ArrayList<Growdever> growdevers = new ArrayList<>();

    public static void addGrowdever(Growdever growdever) {
        if(growdever.getId() == null) {
            throw new RuntimeException("Growdever inválido");
        }
        growdevers.add(growdever);
    }

    public static void removeGrowdever(Growdever growdever) {
        if(growdever.getId() == null) {
            throw new RuntimeException("Growdever inválido");
        }
        growdevers.remove(growdever);
    }

    public static ArrayList<Growdever> getGrowdevers() {
        return growdevers;
    }

    public static boolean growdeverExistByCPF(String cpf) {
        var growdeversFiltered = growdevers.stream().filter((growdever) -> growdever.getCpf().equals(cpf)).findAny();
        return growdeversFiltered.isPresent();
    }

    public static boolean growdeverExistByEmail(String email) {
        var growdeversFiltered = growdevers.stream().filter((growdever) -> growdever.getEmail().equalsIgnoreCase(email)).findAny();
        return growdeversFiltered.isPresent();
    }

    public static Growdever getGrowdeverById(UUID id) {
        var growdeversFiltered = growdevers.stream().filter((growdever) -> growdever.getId().equals(id)).findAny();

        if(growdeversFiltered.isEmpty()) return null;

        // verificar:
        return growdeversFiltered.get();
    }

    public static void updateGrowdever(Growdever growdever) {
        growdevers.removeIf(g -> g.getId().equals(growdever.getId()));
        growdevers.add(growdever);
    }
}
