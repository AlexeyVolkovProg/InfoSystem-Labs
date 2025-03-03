package org.example.firstlabis.mapper.domain;

import org.example.firstlabis.dto.domain.request.HumanBeingUpdateDTO;
import org.example.firstlabis.model.domain.HumanBeing;
import org.example.firstlabis.model.domain.enums.Mood;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HumanBeingMapperTest {

    @Autowired
    private HumanBeingMapperImpl mapper;

    @Test
    void updateEntityFromDto() {
        HumanBeingUpdateDTO dto = new HumanBeingUpdateDTO();
        dto.setMood(Mood.RAGE);
        HumanBeing entity = new HumanBeing();
        entity.setName("John");
        entity.setMood(Mood.APATHY);
        mapper.updateEntityFromDto(dto, entity);
        assertEquals("John", entity.getName());
        assertEquals(Mood.RAGE, entity.getMood());
    }
}