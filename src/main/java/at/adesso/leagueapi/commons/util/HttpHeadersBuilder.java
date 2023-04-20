package at.adesso.leagueapi.commons.util;

import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


public class HttpHeadersBuilder {
    final HttpHeaders httpHeaders = new HttpHeaders();

    public HttpHeadersBuilder(@NonNull final MediaType mediaType) {
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, mediaType.toString());
    }

    public static HttpHeadersBuilder withJsonContentType() {
        return new HttpHeadersBuilder(MediaType.APPLICATION_JSON);
    }

    public static HttpHeadersBuilder withTextContentType() {
        return new HttpHeadersBuilder(MediaType.TEXT_PLAIN);
    }

    public static HttpHeadersBuilder withMultipartFormContentType() {
        return new HttpHeadersBuilder(MediaType.MULTIPART_FORM_DATA);
    }

    public HttpHeadersBuilder withBearerToken(@NonNull final String tokenValue) {
        httpHeaders.add(HttpHeaders.AUTHORIZATION, String.format("Bearer %s", tokenValue));
        return this;
    }

    public HttpHeadersBuilder with(@NonNull final String name, @NonNull final String value) {
        httpHeaders.add(name, value);
        return this;
    }

    public HttpHeaders build() {
        return httpHeaders;
    }
}
