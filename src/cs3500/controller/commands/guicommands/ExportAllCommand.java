package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command to Export all layers and create a text file with the paths of the exported paths.
 */
public class ExportAllCommand extends AGUICommand {

  /**
   * Constructs an {@link ExportAllCommand} based on the model it manipulates and the {@link
   * GUIView} that it renders these changes to.
   *
   * @param model the model to be manipulated.
   * @param frame the view that will reflect the changes to the model.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public ExportAllCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {

  }
}