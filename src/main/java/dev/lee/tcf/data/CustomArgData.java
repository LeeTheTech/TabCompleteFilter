package dev.lee.tcf.data;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CustomArgData {
  private final String command;
  private final boolean permissionCheck;
  private final String permission;
  private final Map<Integer, List<String>> args;
}
