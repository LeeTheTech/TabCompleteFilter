package lee.code.tcf.spigot.files.files;

import lee.code.tcf.spigot.TabCompleteFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum FileArgs {
  ENABLED("enabled", "false", FileDataType.BOOLEAN),
  ;

  @Getter private final String path;
  @Getter private final String string;
  @Getter private final FileDataType fileDataType;

  public boolean getBoolean() {
    return TabCompleteFilter.getInstance().getFileManager().getBooleanFromFile(File.ARGS.name().toLowerCase(), path);
  }
}
