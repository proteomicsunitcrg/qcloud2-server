package eu.qcloud.file.tic;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.file.File;

@Repository
public interface TicRepository extends CrudRepository<Tic, Long> {

	Tic findByFile(File file);

}
