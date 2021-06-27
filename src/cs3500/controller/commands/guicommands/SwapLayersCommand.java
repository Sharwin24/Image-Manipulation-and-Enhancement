package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for swapping two layers.
 */
public class SwapLayersCommand extends AGUICommand {

  /**
   * Constructs a SwapLayersCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public SwapLayersCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String layerIndex1 = frame.getDialogInput("Enter the index for the first layer to swap");
    String layerIndex2 = frame.getDialogInput("Enter the index for the second layer to swap");
    try {
      model.swapLayers(Integer.parseInt(layerIndex1), Integer.parseInt(layerIndex2));
      frame.setImage();
    } catch (IllegalArgumentException e) {
      frame.write("Swap Failed: " + e.getMessage());
    }
  }
}