package br.com.growdev.growdevers.repositories;

import br.com.growdev.growdevers.models.Growdever;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// repository = padrão de projeto = design pattern
public interface GrowdeverRepository extends JpaRepository<Growdever, UUID> {
}
