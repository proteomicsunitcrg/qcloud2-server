package eu.qcloud.tip;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface TipRepository extends CrudRepository<Tip, Long> {

    public Optional <List<Tip>> findByPublishedTwitterAndShowAtBefore(boolean published, Date date);

    public Optional <List<Tip>> findByDisplay(boolean display);

    List <Tip> findAll();

    List <Tip> findAllByDisplay(boolean display);

}
