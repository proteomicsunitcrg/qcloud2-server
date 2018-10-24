package eu.qcloud.file.tic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicRepository extends CrudRepository<Tic, Long> {

}
