package lee.code.tcf.spigot.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
public class CustomArgData {
    @Getter private final boolean permissionCheck;
    @Getter private final String permission;
    @Getter private final HashMap<Integer, List<String>> args;
}
