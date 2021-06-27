package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for applying the Mosaic operation to the current image.
 */
public class GUIMosaicCommand extends AGUICommand {

  /**
   * Constructs a GUIMosaicCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public GUIMosaicCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String input = frame.getDialogInput("Enter the number of seeds with which to "
        + "mosaic the image");

    try {
      int seeds = Integer.parseInt(input);
      model.mosaic(seeds);
      frame.setImage();
    } catch (IllegalArgumentException e) {
      frame.errorPopup("Please try again and "
              + "enter an integer greater than or equal to 0 for the number of seeds",
          "Invalid seed number");
    }
  }
}