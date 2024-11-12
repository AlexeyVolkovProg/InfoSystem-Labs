package org.example.firstlabis.service;


import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.mapper.domain.HumanBeingMapper;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.repository.CarRepository;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.example.firstlabis.service.domain.HumanBeingService;
import org.example.firstlabis.utils.DateHumanUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class HumanBeingServiceTests {
    @Mock
    private HumanBeingRepository humanBeingRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    HumanBeingMapper humanBeingMapper;
    @InjectMocks
    private HumanBeingService humanBeingService;
}
