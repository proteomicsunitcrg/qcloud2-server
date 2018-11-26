package eu.qcloud.traceColor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraceColorRepository extends CrudRepository<TraceColor, Long> {
	
	@Query(value="select tc from TraceColor tc")
	public List<TraceColor> findAllTraceColor();
	
	public Optional<TraceColor> findByApiKey(UUID apiKey);
	
	public List<TraceColor> findByIdIn(List<Long> ids);
	
	@Query(value="select tc from TraceColor tc where tc.id NOT IN (?1)")
	public List<TraceColor> findByIdNotIn(List<Long> ids);
	
	
}
