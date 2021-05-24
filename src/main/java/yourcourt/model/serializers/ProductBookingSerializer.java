package yourcourt.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import yourcourt.model.ProductBooking;

public class ProductBookingSerializer extends StdSerializer<ProductBooking> {
  /**
   *
   */private static final long serialVersionUID = 1L;

  public ProductBookingSerializer() {
    this(null);
  }

  public ProductBookingSerializer(Class<ProductBooking> t) {
    super(t);
  }

  @Override
  public void serialize(
    ProductBooking item,
    JsonGenerator generator,
    SerializerProvider provider
  )
    throws IOException, JsonProcessingException {
    Long id = item.getId();

    generator.writeObject(id);
  }
}
