package cs3500.view;

import cs3500.Utility;
import java.awt.Color;

/**
 * Class for representing the color theme of the GUI.
 */
public class GUITheme {

  private final Color primary;
  private final Color secondary;
  private final Color accent;

  /**
   * Constructs a {@link GUITheme} with parameters for the colors.
   *
   * @param primary the primary color for the theme.
   * @param secondary the secondary color for the theme.
   * @param accent the accent color for the theme.
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
   * Gets the primary color.
   *
   * @return a Color object representing the primary color.
   */
  public Color getPrimary() {
    return this.primary;
  }

  /**
   * Gets the secondary color.
   *
   * @return Color object representing the secondary color.
   */
  public Color getSecondary() {
    return this.secondary;
  }
  /**
   * Gets the accent color.
   *
   * @return a Color object representing the accent color.
   */
  public Color getAccent() {
    return this.accent;
  }


}