package org.example.firstlabis.service.domain;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.example.firstlabis.dto.domain.request.HumanBeingCreateDTO;
import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.dto.domain.response.HumanBeingResponseDTO;
import org.example.firstlabis.mapper.domain.HumanBeingMapper;
import org.example.firstlabis.mapper.socket.HumanBeingMessageMapper;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.repository.HumanBeingRepository;
import org.example.firstlabis.utils.DateHumanUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class HumanBeingServiceTest {

    @Mock
    HumanBeingRepository humanBeingRepository;

    @Mock
    HumanBeingMapper humanBeingMapper;

    @Mock
    HumanBeingMessageMapper humanBeingMessageMapper;

    @Mock
    SimpMessagingTemplate messagingTemplate;

    @Mock
    EntityManager entityManager;

    @InjectMocks
    HumanBeingService humanBeingService;

    DateHumanUtil dateHumanUtil = new DateHumanUtil();

    @Test
    @DisplayName("Проверка вызова операции save и возвращаемого значения, при поступлении нового HumanBeing")
    public void createHumanBeing_ShouldSaveAndReturnResponse_WhenValidDtoProvided(){
        //given
        HumanBeingCreateDTO humanBeingCreateDTO = new HumanBeingCreateDTO();
        HumanBeing humanBeing = new HumanBeing();
        HumanBeingResponseDTO expectedResult = new HumanBeingResponseDTO();

        //when
        when(humanBeingRepository.findByName(humanBeingCreateDTO.getName()))
                .thenReturn(Optional.empty());
        when(humanBeingMapper.toEntity(humanBeingCreateDTO)).thenReturn(humanBeing);
        when(humanBeingRepository.save(humanBeing)).thenReturn(humanBeing);
        when(humanBeingMapper.toResponseDto(humanBeing)).thenReturn(expectedResult);
        HumanBeingResponseDTO result = humanBeingService.createHumanBeing(humanBeingCreateDTO);

        //then
        verify(humanBeingRepository, times(1)).save(humanBeing);
        assertEquals(expectedResult, result);
    }

    @Test
    public void createHumanBeing_ShouldThrowException_WhenHumanBeingWithNameAlreadyExist(){
        //given
        HumanBeingCreateDTO humanBeingCreateDTO = new HumanBeingCreateDTO();
        HumanBeing humanBeing = new HumanBeing();
        //when
        when(humanBeingRepository.findByName(humanBeingCreateDTO.getName()))
                .thenReturn(Optional.of(humanBeing));
        assertThrows(EntityNotFoundException.class, () -> humanBeingService.createHumanBeing(humanBeingCreateDTO));
    }

    @Test
    public void createHumanBeing_ShouldThrowException_WhenHumanBeingCreateDTOisNull(){
        HumanBeingCreateDTO humanBeingCreateDTO = null;
        assertThrows(NullPointerException.class, () -> humanBeingService.createHumanBeing(humanBeingCreateDTO));
        verify(humanBeingRepository, never()).findByName(any());
        verify(humanBeingRepository, never()).save(any());
    }

    @Test
    public void updateHumanBeing_ShouldUpdateEntity_WhenValidDTOProvided(){
        HumanBeingUpdateDTO humanBeingUpdateDTO = new HumanBeingUpdateDTO();
        humanBeingUpdateDTO.setName("UpdatedName");
        HumanBeing humanBeing = DateHumanUtil.generateDefaultHumanBeingWithName("Alexey");
        Long id = 1L;

        // when
        when(humanBeingRepository.findById(id)).thenReturn(Optional.of(humanBeing));

        doAnswer(invocation -> {
            HumanBeingUpdateDTO dto = invocation.getArgument(0);
            HumanBeing entity = invocation.getArgument(1);
            entity.setName(dto.getName());
            return null;
        }).when(humanBeingMapper).updateEntityFromDto(humanBeingUpdateDTO, humanBeing);
        when(humanBeingRepository.save(humanBeing)).thenReturn(humanBeing);
        humanBeingService.updateHumanBeing(id, humanBeingUpdateDTO);

        // then
        verify(humanBeingMapper).updateEntityFromDto(humanBeingUpdateDTO, humanBeing);
        assertEquals("UpdatedName", humanBeing.getName());
        verify(humanBeingRepository).save(humanBeing);
    }

    @Test
    void updateHumanBeing() {
    }

    @Test
    void deleteHumanBeing() {
    }

    @Test
    void attachTheCar() {
    }

    @Test
    void findHumanBeingById() {
    }

    @Test
    void getAllWithFilters() {
    }

    @Test
    void getAllByNameContaining() {
    }
}