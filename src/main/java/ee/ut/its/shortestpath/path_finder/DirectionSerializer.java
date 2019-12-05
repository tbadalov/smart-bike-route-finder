package ee.ut.its.shortestpath.path_finder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.mapbox.api.directions.v5.models.DirectionsRoute;

import java.io.IOException;

public class DirectionSerializer extends StdSerializer<DirectionsRoute> {

    public DirectionSerializer() {
        super(DirectionsRoute.class);
    }

    @Override
    public void serialize(DirectionsRoute directionsRoute, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartObject();
        jgen.writeFieldName("route");
        jgen.writeRawValue(directionsRoute.toJson());
        jgen.writeEndObject();
    }
}