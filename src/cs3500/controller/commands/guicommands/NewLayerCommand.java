package cs3500.controller.commands.guicommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;

/**
 * Command for creating a new layer.
 */
public class NewLayerCommand extends AGUICommand {

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public NewLayerCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() { // Todo: Change to Getters
    model.addLayer();
    frame.allLayers.add(frame.createLayerRow(model.getLayers().size() - 1, true));
    frame.layersPanel.add(frame.allLayers.get(frame.allLayers.size() - 1));
    frame.renderLayers();
  }
}