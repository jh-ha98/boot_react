package hjh.mag.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Valid {

  True("TRUE"),
  False("FALSE");

  private String valid;

}