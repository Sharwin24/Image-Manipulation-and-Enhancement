package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.model.programmaticimages.PureNoise;
import cs3500.view.GUIView;

/**
 * Command to create a PureNoise image.
 */
public class PureNoiseCommand extends ANoiseCommand{

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public PureNoiseCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  protected IProgramImage factoryProgrammaticImage() {
    return new PureNoise();
  }
}