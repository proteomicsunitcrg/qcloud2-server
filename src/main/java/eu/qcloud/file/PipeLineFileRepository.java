package eu.qcloud.file;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import eu.qcloud.labsystem.LabSystem;

@Repository
public interface PipeLineFileRepository extends JpaRepository<PipeLineFile, Long> {

	public Optional<PipeLineFile> findByChecksum(String checksum);

	public Page<PipeLineFile> findByLabSystemInOrderByIdDesc(List<LabSystem> ls, Pageable page);

	public Page<PipeLineFile> findByFilenameContainingAndLabSystemInOrderByIdDesc(String filename, List<LabSystem> ls,
			Pageable page);

}
