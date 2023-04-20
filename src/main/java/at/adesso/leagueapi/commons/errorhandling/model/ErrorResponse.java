package at.adesso.leagueapi.commons.errorhandling.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private String correlationId;
    private LocalDateTime timeStamp;
    private String code;
    private String message;
    private List<String> details;

}
