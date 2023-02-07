package hjh.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EqualsTest {

  @Test
  @DisplayName("==과 equals의 차이를 느껴보는 테스트")
  public void equals() {
    String test1 = new String("123");
    String test2 = new String("123");

    System.out.println(test1 == test2);
    System.out.println(test1.equals(test2));

    // 동등비교
    assertEquals(false, test1 == test2);
    assertEquals(true, test1.equals(test2));
  }

}