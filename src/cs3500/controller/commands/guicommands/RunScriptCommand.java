package cs3500.controller.commands.guicommands;

import cs3500.controller.IMultiLayerIMEController;
import cs3500.controller.MultiLayerIMEControllerImpl;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.io.StringReader;

/**
 * Command to run a script written in the script panel.
 */
public class RunScriptCommand extends AGUICommand {

  /**
   * Constructs a RunScript Command for the GUI and passes the model and frame.
   *
   * @param model the multi-layer model for the GUI to use.
   * @param frame the JFrame for the GUI to use.
   * @throws IllegalArgumentException if any arguments are null or invalid.
   */
  public RunScriptCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    StringReader scriptInput = new StringReader(frame.getScriptArea().getText());
    frame.getScrptCtrlr() = MultiLayerIMEControllerImpl.controllerBuilder().model(model)
        .readable(scriptInput).buildController();
    // Fixme: Use the frame's controller to run the script.
    frame.getScrptCtrlr().run();
    frame.setImage();
    frame.renderLayers();
  }
}