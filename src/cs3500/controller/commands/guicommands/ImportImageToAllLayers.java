package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.layer.ILayer;
import cs3500.view.GUIView;

/**
 * Imports one image to every present layer in the multi layer model.
 */
public class ImportImageToAllLayers extends AImportCommand {

  /**
   * Constructs an {@link AGUICommand} based on the model it manipulates and the {@link GUIView}
   * that it renders these changes to.
   *
   * @param model the model to be manipulated.
   * @param frame the view that will reflect the changes to the model.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ImportImageToAllLayers(IMultiLayerExtraOperations model,
      GUIView frame) throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String path = "";
    try {
      path = this.getAbsolutePathOfFile();
    } catch (IllegalArgumentException e) {
      return;
    }
    for (ILayer layer : model.getLayers()) {
      layer.modelLoad(this.fileFormat.importImage(path));
    }
    frame.setImage();
  }
}