package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command to delete a layer by prompting user to input layer index.
 */
public class DeleteLayerCommand extends AGUICommand {

  /**
   * Constructs a DeleteLayerCommand based on the model to manipulate and the view that will
   * reflect these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public DeleteLayerCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String layerToDeleteInp = frame.getDialogInput("Enter the index of the layer to delete");
    try { // Todo: Getters
      int layerToDelete = Integer.parseInt(layerToDeleteInp);
      model.deleteLayer(layerToDelete);
      frame.allLayers.remove(layerToDelete);
      frame.layersPanel.remove(layerToDelete);
      frame.setImage();
      frame.renderLayers();
    } catch (IllegalArgumentException e) {
      frame.errorPopup("Please enter a valid number between 0 and " +
          (model.getLayers().size() - 1), "Bad layer number");
    }
  }
}