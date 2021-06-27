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
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
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