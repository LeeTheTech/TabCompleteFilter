package lee.code.tcf.spigot.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PlaceHolders {

    ONLINE_PLAYERS("%online_players%"),
    ;


    @Getter private final String placeholder;
}
