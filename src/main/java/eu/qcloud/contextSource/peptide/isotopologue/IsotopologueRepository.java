package eu.qcloud.contextSource.peptide.isotopologue;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsotopologueRepository extends CrudRepository<Isotopologue, Long> {

}
