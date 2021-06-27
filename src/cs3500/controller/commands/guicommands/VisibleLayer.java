package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Action listener to toggle the visibility of a desired layer.
 */
public class VisibleLayer extends AGUICommand {

  private final int layerNum;

  /**
   * Constructs a VisibleLayer with a given layer index.
   *
   * @param layerNum the index of the layer.
   * @throws IllegalArgumentException if the layer index is invalid.
   */
  public VisibleLayer(IMultiLayerExtraOperations model, GUIView frame, int layerNum)
      throws IllegalArgumentException {
    super(model, frame);
    if (layerNum < 0) {
      throw new IllegalArgumentException("Layer Number cannot be less than zero");
    }
    this.layerNum = layerNum;
  }

  @Override
  public void execute() {
    try {
      model.toggleInvisible(layerNum);
      frame.setImage();
      if (model.getLayers().get(layerNum).isInvisible()) {
        frame.write("Layer " + layerNum + " is invisible");
      } else {
        frame.write("Layer " + layerNum + " is visible");
      }
    } catch (IllegalArgumentException e) {
      frame.write("Visibility Toggle Failed: " + e.getMessage());
    }

  }
}