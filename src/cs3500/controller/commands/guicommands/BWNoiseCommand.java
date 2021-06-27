package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.programmaticimages.BWNoise;
import cs3500.model.programmaticimages.IProgramImage;
import cs3500.view.GUIView;

/**
 * Command to create a BWNoise image.
 */
public class BWNoiseCommand extends ANoiseCommand {

  /**
   * Constructs a black and white noise command based on the model to manipulate and the view
   * that will
   * reflect these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public BWNoiseCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  protected IProgramImage factoryProgrammaticImage() {
    return new BWNoise();
  }
}