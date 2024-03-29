/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package yourcourt.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Formula;

@MappedSuperclass
public class NamedEntity extends BaseEntity {
  @Size(min = 3, max = 50)
  @Column(name = "name")
  private String name;

  @OneToOne(cascade = CascadeType.DETACH)
  private Image image;

  @Formula("(SELECT image.image_url FROM images image WHERE image.id=image_id)")
  private String imageUrl;

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return this.getName();
  }
}
