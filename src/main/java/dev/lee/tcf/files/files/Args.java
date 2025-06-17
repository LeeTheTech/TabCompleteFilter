package dev.lee.tcf.files.files;

import dev.lee.tcf.TabCompleteFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Args {
  ENABLED("enabled", false),
  ;

  private final String path;
  private final Object object;

  public boolean getBoolean() {
    return TabCompleteFilter.getInstance().getFileManager().getBoolean("args", path);
  }
}
