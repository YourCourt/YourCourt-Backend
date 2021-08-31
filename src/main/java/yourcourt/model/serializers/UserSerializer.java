package yourcourt.model.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import yourcourt.security.model.User;

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

      generator.writeStartObject();
      generator.writeNumberField("id",item.getId());
      generator.writeStringField("username",item.getUsername());
      generator.writeStringField("email",item.getEmail());
      generator.writeStringField("phone",item.getPhone());
      generator.writeStringField("birthDate",item.getBirthDate().toString());
      generator.writeStringField("creationDate",item.getCreationDate().toString());
      generator.writeStringField("membershipNumber",item.getMembershipNumber());
      generator.writeObjectField("image",item.getImage());
      generator.writeStringField("imageUrl",item.getImageUrl());
      generator.writeEndObject();
  }
}
