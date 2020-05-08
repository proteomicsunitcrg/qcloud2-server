package eu.qcloud.logo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eu.qcloud.exceptions.InvalidActionException;
import eu.qcloud.exceptions.NotFoundException;

@Service
public class LogoService {

    @Autowired
    LogoRepository logoRepository;

    public Logo insertLogo(Logo logo) {
        logo.setApiKey(UUID.randomUUID());
        return logoRepository.save(logo);
    }

    public Logo updateLogo(Logo logo) {
        return logoRepository.save(logo);
    }

    public List<Logo> getAll() {
        List<Logo> logos = new ArrayList<>();
        logoRepository.findAll().forEach(logos::add);
        return logos;
    }

    public Logo enableDisable(Logo logo) throws InvalidActionException {
        setAllLogosDisabled();
        return logoRepository.save(logo);
    }

    public void setAllLogosDisabled() {
        List<Logo> logos = getAll();
        logos.forEach(logo -> {
            logo.setActive(false);
        });
        logoRepository.saveAll(logos);
    }

    public void delete(UUID logoApiKey) {
        Logo logo = logoRepository.findByApiKey(logoApiKey);
        if (!logo.isActive()) {
            logoRepository.delete(logo);
        } else {
            throw new InvalidActionException("Cannot delete an active logo");
        }
    }

    public Logo getEnabledLogo() throws NotFoundException {
        Optional<Logo> logo = logoRepository.findOneByActiveTrue();
        if (logo.isPresent()) {
            return logo.get();
        } else {
            throw new NotFoundException("Nothing enabled found");
        }
    }

    public Logo getByApiKey(UUID apiKey) {
        return logoRepository.findByApiKey(apiKey);
    }

}