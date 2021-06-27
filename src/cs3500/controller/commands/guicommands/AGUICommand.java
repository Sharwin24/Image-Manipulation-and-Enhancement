package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Abstract class for GUI commands.
 */
public abstract class AGUICommand implements IGUICommand {

  protected final IMultiLayerExtraOperations model;
  protected final GUIView frame;

  /**
   * Constructs an {@link AGUICommand} based on the model it manipulates and the
   * {@link GUIView} that it renders these changes to.
   *
   * @param model the model to be manipulated.
   * @param frame the view that will reflect the changes to the model.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public AGUICommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    if (model == null || frame == null) {
      throw new IllegalArgumentException("Arguments are nul");
    }
    this.model = model;
    this.frame = frame;
  }
}