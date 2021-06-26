package cs3500.frame;

import cs3500.Utility;
import java.awt.Color;

/**
 * TODO
 */
public class GUITheme {

  private final Color primary;
  private final Color secondary;
  private final Color accent;

  /**
   * TODO
   *
   * @param primary
   * @param secondary
   * @param accent
   */
  public GUITheme(Color primary, Color secondary, Color accent) {
    this.primary = Utility.checkNotNull(primary, "cannot create a theme with a null "
        + "primary color");
    this.secondary = Utility.checkNotNull(secondary, "cannot create a theme with a null "
        + "secondary color");
    this.accent = Utility.checkNotNull(accent, "cannot create a theme with a null "
        + "accent color");
  }

  /**
   * TODO
   *
   * @return
   */
  public Color getPrimary() {
    return this.primary;
  }

  /**
   * TODO
   *
   * @return
   */
  public Color getSecondary() {
    return this.secondary;
  }
  /**
   * TODO
   *
   * @return
   */
  public Color getAccent() {
    return this.accent;
  }


}
