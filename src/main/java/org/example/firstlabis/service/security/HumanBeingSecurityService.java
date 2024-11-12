package org.example.firstlabis.service.security;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HumanBeingSecurityService extends TrackEntitySecurityService<HumanBeing, Long> {

    private final HumanBeingRepository humanBeingRepository;

    @Override
    protected HumanBeing findById(Long id) {
        return humanBeingRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("HumanBeing not found with id: " + id));
    }

}