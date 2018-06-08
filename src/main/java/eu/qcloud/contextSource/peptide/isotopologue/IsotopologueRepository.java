package eu.qcloud.contextSource.peptide.isotopologue;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IsotopologueRepository extends CrudRepository<Isotopologue, Long> {
	
	public List<Isotopologue> findByMainPeptideId(Long mainPeptideId);

}
