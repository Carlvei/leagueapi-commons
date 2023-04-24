package at.adesso.leagueapi.commons.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonStringToObjectMapper<T> {

    private final Class<T> type;

    public JsonStringToObjectMapper(Class<T> type) {
        this.type = type;
    }

    public T deserialize(final String jsonString) {
        final ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (final JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
