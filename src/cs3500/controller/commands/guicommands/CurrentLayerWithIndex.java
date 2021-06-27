package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for setting the current layer with the layer button.
 */
public class CurrentLayerWithIndex extends AGUICommand {

  private final int layerNum;

  /**
   * Constructs a CurrentLayerCommand based on the model to manipulate and the view that will
   * reflect these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @param layerNum the index of the layer to switch to.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public CurrentLayerWithIndex(IMultiLayerExtraOperations model, GUIView frame, int layerNum)
      throws IllegalArgumentException {
    super(model, frame);
    if (layerNum < 0) {
      throw new IllegalArgumentException("Layer Number cannot be less than zero");
    }
    this.layerNum = layerNum;
  }

  @Override
  public void execute() {
    model.setCurrentLayer(layerNum);
    frame.write("Layer " + layerNum + " selected");
    frame.setImage();
  }
}