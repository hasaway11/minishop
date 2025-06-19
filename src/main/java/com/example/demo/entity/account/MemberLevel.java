package com.example.demo.entity.account;

public enum MemberLevel {
  NORMAL("고마운분"), HONORED("소중한분"), VIP("천생연분");

  private final String label;

  MemberLevel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }
}
