package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.view.GUIView;
/**
 * All Noise Commands follow the same execution with different parameters, which are specified by
 * each subclass that extends this abstract class.
 */
public abstract class ANoiseCommand extends AGUICommand{

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public ANoiseCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    String widthInp = frame.getDialogInput("Please enter the width of the noise image");
    String heightInp = frame.getDialogInput("Please enter the height of the noise image");

    try {
      int width = Integer.parseInt(widthInp);
      int height = Integer.parseInt(heightInp);

      model.setProgrammaticImage(this.factoryProgrammaticImage(), width, height, 1);
      frame.setImage();
    } catch (NumberFormatException e) {
      frame.errorPopup("Cannot create a noise input with the specified width \""
          + widthInp + "\" and height \"" + heightInp + "\"", "Bad noise image dimensions");
    }
  }

  /**
   * Returns the subclass programmed Image.
   */
  protected abstract IProgramImage factoryProgrammaticImage();
}