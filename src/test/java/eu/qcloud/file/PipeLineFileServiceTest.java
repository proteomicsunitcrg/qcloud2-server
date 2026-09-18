package eu.qcloud.file;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import eu.qcloud.labsystem.LabSystem;
import eu.qcloud.labsystem.LabSystemService;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Plain JUnit (no Mockito): the version shipped with Spring Boot 2.0.5 cannot
 * generate mock classes on the JDK 11 this machine uses. The repository is
 * faked with a java.lang.reflect.Proxy and the lab system service with a small
 * subclass, both injected through ReflectionTestUtils.
 */
public class PipeLineFileServiceTest {

	private static final LabSystem LS = new LabSystem();

	private static class FakeLabSystemService extends LabSystemService {
		@Override
		public Optional<LabSystem> findSystemByApiKey(UUID apikey) {
			return Optional.of(LS);
		}
	}

	private PipeLineFileService serviceWithRecentFiles(List<PipeLineFile> recentFiles) {
		PipeLineFileRepository repo = (PipeLineFileRepository) Proxy.newProxyInstance(
				PipeLineFileRepository.class.getClassLoader(),
				new Class<?>[] { PipeLineFileRepository.class },
				(proxy, method, args) -> {
					if (method.getDeclaringClass() == Object.class) {
						switch (method.getName()) {
							case "hashCode":
								return System.identityHashCode(proxy);
							case "equals":
								return proxy == args[0];
							default:
								return "PipeLineFileRepository(proxy)";
						}
					}
					if (method.getName().equals("findFirst10ByLabSystemOrderByIdDesc")) {
						return recentFiles;
					}
					throw new UnsupportedOperationException("Unexpected repository call: " + method.getName());
				});
		PipeLineFileService service = new PipeLineFileService();
		ReflectionTestUtils.setField(service, "pipeLineFileRepository", repo);
		ReflectionTestUtils.setField(service, "labSystemService", new FakeLabSystemService());
		return service;
	}

	private static PipeLineFile fileWithStatus(PipelineFileStatus status) {
		PipeLineFile pf = new PipeLineFile();
		pf.setStatus(status);
		return pf;
	}

	@Test
	public void hasError_trueWhenRecentWindowContainsError() {
		PipeLineFileService service = serviceWithRecentFiles(Arrays.asList(
				fileWithStatus(PipelineFileStatus.PROCESSED),
				fileWithStatus(PipelineFileStatus.ERROR),
				fileWithStatus(PipelineFileStatus.PROCESSED)));

		assertTrue(service.hasError(UUID.randomUUID()));
	}

	@Test
	public void hasError_falseWhenRecentWindowHasNoError() {
		PipeLineFileService service = serviceWithRecentFiles(Arrays.asList(
				fileWithStatus(PipelineFileStatus.PROCESSED),
				fileWithStatus(PipelineFileStatus.PROCESSED)));

		assertFalse(service.hasError(UUID.randomUUID()));
	}

	@Test
	public void hasError_falseWhenNoRecentFiles() {
		PipeLineFileService service = serviceWithRecentFiles(Collections.emptyList());

		assertFalse(service.hasError(UUID.randomUUID()));
	}

}