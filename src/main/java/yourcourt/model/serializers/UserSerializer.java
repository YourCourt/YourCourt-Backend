package yourcourt.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import yourcourt.security.model.User;

import java.io.IOException;

public class UserSerializer extends StdSerializer<User> {
  /**
   *
   */private static final long serialVersionUID = 1L;

  public UserSerializer() {
    this(null);
  }

  public UserSerializer(Class<User> t) {
    super(t);
  }

  @Override
  public void serialize(User item, JsonGenerator generator, SerializerProvider provider)
    throws IOException, JsonProcessingException {
    String username = item.getUsername();

    generator.writeObject(username);
  }
}
