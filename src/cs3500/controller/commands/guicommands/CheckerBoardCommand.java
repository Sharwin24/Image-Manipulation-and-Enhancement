package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.programmaticimages.Checkerboard;
import cs3500.view.GUIView;

/**
 * Command for creating a checkerboard image.
 */
public class CheckerBoardCommand extends AGUICommand{

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public CheckerBoardCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String widthInp = frame.getDialogInput("Please enter the width of the checkerboard");
    String heightInp = frame.getDialogInput("Please enter the height of the checkerboard");
    String unitInp = frame.getDialogInput("Please enter the size of a square in the "
        + "checkerboard");

    try {
      int width = Integer.parseInt(widthInp);
      int height = Integer.parseInt(heightInp);
      int unit = Integer.parseInt(unitInp);

      model.setProgrammaticImage(new Checkerboard(), width, height, unit);
      frame.setImage();
    } catch (NumberFormatException e) {
      frame.errorPopup("Please enter a width height and unit size that are non-negative "
          + "integers", "Bad dimensions");
    }

  }
}