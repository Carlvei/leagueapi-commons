package at.adesso.leagueapi.commons.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectToStringMapper {
    public String serialize(final Object object) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
