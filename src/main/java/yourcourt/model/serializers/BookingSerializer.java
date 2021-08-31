package yourcourt.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import yourcourt.model.Booking;

import java.io.IOException;

public class BookingSerializer extends StdSerializer<Booking> {
  /**
   *
   */private static final long serialVersionUID = 1L;

  public BookingSerializer() {
    this(null);
  }

  public BookingSerializer(Class<Booking> t) {
    super(t);
  }

  @Override
  public void serialize(Booking item, JsonGenerator generator, SerializerProvider provider)
    throws IOException, JsonProcessingException {
    Long id = item.getId();

    generator.writeObject(id);
  }
}
