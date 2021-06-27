package cs3500.controller.commands.guicommands;

import cs3500.Utility;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.operation.IOperation;
import cs3500.view.GUIView;
/**
 * Command to apply operations to the GUI's image.
 */
public class OperationCommand extends AGUICommand{
  private final IOperation toApply;

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @param toApply
   * @throws IllegalArgumentException
   */
  public OperationCommand(IMultiLayerExtraOperations model, GUIView frame,
      IOperation toApply)
      throws IllegalArgumentException {
    super(model, frame);
    this.toApply = Utility.checkNotNull(toApply, "cannot make an operation command "
        + "with a null operation");
  }

  @Override
  public void execute() {
    model.applyOperations(toApply);
    frame.setImage();
    frame.renderLayers();
  }
}