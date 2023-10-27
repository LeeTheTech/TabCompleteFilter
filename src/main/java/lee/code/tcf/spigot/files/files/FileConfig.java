package lee.code.tcf.spigot.files.files;

import lee.code.tcf.spigot.TabCompleteFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FileConfig {
  OP_BYPASS("op-player-filter-bypass", "false", FileDataType.BOOLEAN),
  ;
  @Getter private final String path;
  @Getter private final String string;
  @Getter private final  FileDataType fileDataType;

  public boolean getBoolean() {
    return TabCompleteFilter.getInstance().getFileManager().getBooleanFromFile(File.CONFIG.name().toLowerCase(), path);
  }
}
