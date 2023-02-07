package hjh.api.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageBoxValid {

  TRUE("TRUE"),
  FALSE("FALSE");

  private String valid;

}