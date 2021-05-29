package yourcourt.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import yourcourt.model.Court;

public class CourtSerializer extends StdSerializer<Court> {
  /**
   *
   */private static final long serialVersionUID = 1L;

  public CourtSerializer() {
    this(null);
  }

  public CourtSerializer(Class<Court> t) {
    super(t);
  }

  @Override
  public void serialize(Court item, JsonGenerator generator, SerializerProvider provider)
    throws IOException, JsonProcessingException {
    Long id = item.getId();

    generator.writeObject(id);
  }
}
