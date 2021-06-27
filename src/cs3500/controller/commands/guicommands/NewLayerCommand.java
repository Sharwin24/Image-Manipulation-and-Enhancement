package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for creating a new layer.
 */
public class NewLayerCommand extends AGUICommand {

  /**
   * Constructs a NewLayerCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public NewLayerCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    model.addLayer();
    frame.getAllLayers().add(frame.createLayerRow(model.getLayers().size() - 1, true));
    frame.getLayersPanel().add(frame.getAllLayers().get(frame.getAllLayers().size() - 1));
    frame.renderLayers();
  }
}