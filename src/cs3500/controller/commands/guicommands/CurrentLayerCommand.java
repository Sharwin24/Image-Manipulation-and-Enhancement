package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for selecting the current layer.
 */
public class CurrentLayerCommand extends AGUICommand {

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public CurrentLayerCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String desiredLayerInp = frame.getDialogInput("Enter the layer you want to "
        + "switch to");
    try {
      int desiredLayer = Integer.parseInt(desiredLayerInp);
      model.setCurrentLayer(desiredLayer);
    } catch (NumberFormatException e) {
      frame.errorPopup("Cannot switch to layer: \"" + desiredLayerInp + "\"",
          "Bad layer number");

    }
  }
}