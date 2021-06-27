package cs3500.controller.commands.guicommands;

import cs3500.Utility;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUITheme;
import cs3500.view.GUIView;

/**
 * Class to set the GUI's color theme.
 */
public class ThemeCommand extends AGUICommand {

  private final GUITheme theme;

  /**
<<<<<<< HEAD
   * Constructs a ThemeCommand Command for the GUI and passes the model and frame.
   *
   * @param model the multi-layer model for the GUI to use.
   * @param frame the JFrame for the GUI to use.
   * @throws IllegalArgumentException if any arguments are null or invalid.
=======
   * Constructs a ThemeCommand based on the model to manipulate and the view that will reflect
   * these changes, as well as the {@link GUITheme} to be swapped to.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @param theme the {@link GUITheme} to be swapped to.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
>>>>>>> c54148f874420ad03d8208abe08d5f2536795da5
   */
  public ThemeCommand(IMultiLayerExtraOperations model, GUIView frame, GUITheme theme)
      throws IllegalArgumentException {
    super(model, frame);
    this.theme = Utility.checkNotNull(theme, "null theme provided");
  }

  @Override
  public void execute() {
    frame.getMainPanel().setBackground(theme.getPrimary());
    frame.getLayersPanel().setBackground(theme.getPrimary());
    frame.getImageScrollPanel().setBackground(theme.getPrimary());
    //imagePanel.setBackground(theme.getPrimary());
    frame.getConsolePanel().setBackground(theme.getPrimary());

    frame.getScriptArea().setBackground(theme.getPrimary());
    frame.getScriptArea().setForeground(theme.getAccent());

    frame.getMenuRibbon().setBackground(theme.getSecondary());
  }
}