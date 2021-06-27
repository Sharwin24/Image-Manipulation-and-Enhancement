package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Class for Importing/Loading an image into the GUI.
 */
public class ImportOneCommand extends AImportCommand {

  /**
   * Constructs a ImportOneCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ImportOneCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
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
    try {
      model.load(this.fileFormat.importImage(path));
    } catch (IllegalArgumentException e) {
      return;
    }
    frame.setImage();
  }
}