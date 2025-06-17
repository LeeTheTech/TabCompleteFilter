package dev.lee.tcf.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Placeholders {
  ONLINE_PLAYERS("%online_players%"),
  ;
  private final String placeholder;
}
