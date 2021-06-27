package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for downscaling an image.
 */
public class DownScaleCommand extends AGUICommand {

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public DownScaleCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String widthInp = frame.getDialogInput("Enter the new width of the image");

    String heightInp = frame.getDialogInput("Enter the new height of the image");

    try {
      int height = Integer.parseInt(heightInp);
      int width = Integer.parseInt(widthInp);
      model.downscaleLayers(height, width);
      frame.setImage();
      frame.renderLayers();
    } catch (IllegalArgumentException e) {
      frame.errorPopup("Please try again and enter an integer greater than or equal to 0 for the "
          + "number of seeds", "Invalid number of seeds");
    }

  }
}