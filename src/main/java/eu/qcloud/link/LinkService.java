package eu.qcloud.link;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

	@Autowired
	LinkRepository linkRepository;

	public List<Link> getAll() {
		List<Link> links = new ArrayList<>();
		linkRepository.findAll().forEach(links::add);
		return links;
	}

	public Link updateLink(Link link) {
		return linkRepository.save(link);
	}

	public Link getByApiKey(@NotNull @NotEmpty UUID apiKey) {
		return linkRepository.findOneByApiKey(apiKey);
	}

}