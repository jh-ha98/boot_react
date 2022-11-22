package jh.project.myproject.user;

public enum SignValid {

  True("TRUE"),
  False("FALSE");

  private String valid;

  SignValid(String valid) {
    this.valid = valid;
  }

  public String valid() {
    return this.valid;
  }

}