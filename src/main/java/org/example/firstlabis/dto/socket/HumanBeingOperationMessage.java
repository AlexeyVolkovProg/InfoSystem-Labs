package org.example.firstlabis.dto.socket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.firstlabis.dto.socket.dto.HumanBeingSocketMessageDTO;
import org.example.firstlabis.dto.socket.enums.OperationType;

@Getter
@Setter
@AllArgsConstructor
public class HumanBeingOperationMessage {
    private OperationType operationType;
    private HumanBeingSocketMessageDTO humanBeing;
}
