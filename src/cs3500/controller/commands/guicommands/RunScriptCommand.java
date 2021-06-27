package cs3500.controller.commands.guicommands;

import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.io.StringReader;

/**
 * Command to run a script written in the script panel.
 */
public class RunScriptCommand extends AGUICommand {

  /**
   * Todo
   *
   * @param model
   * @param frame
   * @throws IllegalArgumentException
   */
  public RunScriptCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() { // Todo: Getters
    StringReader scriptInput = new StringReader(frame.scriptArea.getText());

    frame.scrptCtrlr = MultiLayerIMEControllerImpl.controllerBuilder().model(model)
        .readable(scriptInput).buildController();

    frame.scrptCtrlr.run();
    frame.setImage();
    frame.renderLayers();
  }
}