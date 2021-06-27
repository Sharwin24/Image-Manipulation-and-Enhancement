package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for a delete button to delete that specific layer.
 */
public class DeleteLayerWithIndexCommand extends AGUICommand {
  private final int layerNum;

  /**
   * Constructs a DeleteLayerWithIndexCommand based on the model to manipulate and the view that
   * will
   * reflect these changes, as well as the layer to be deleted.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @param layerNum the layer to be deleted.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public DeleteLayerWithIndexCommand(IMultiLayerExtraOperations model,
      GUIView frame, int layerNum) throws IllegalArgumentException {
    super(model, frame);
    if (layerNum < 0) {
      throw new IllegalArgumentException("Layer Number cannot be less than zero");
    }
    this.layerNum = layerNum;
  }

  @Override
  public void execute() {
    try {
      model.deleteLayer(layerNum);
      frame.allLayers.remove(layerNum);
      frame.setImage();
      frame.renderLayers();
    } catch (IllegalArgumentException e) {
      frame.write("Delete Failed: " + e.getMessage());
    }
  }
}