package eu.qcloud.logo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LogoRepository extends CrudRepository<Logo, Long> {
    
    public Logo findByApiKey(UUID apiKey);

    public Optional <Logo> findOneByActiveTrue();
}