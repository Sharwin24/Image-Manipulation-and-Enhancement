package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for selecting the current layer.
 */
public class CurrentLayerCommand extends AGUICommand {

  /**
   * Constructs a CurrentLayerCommand based on the model to manipulate and the view that will
   * reflect these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
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