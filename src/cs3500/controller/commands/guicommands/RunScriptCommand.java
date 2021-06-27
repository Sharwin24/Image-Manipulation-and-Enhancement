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
   * Constructs a RunScriptCommand based on the model to manipulate and the view that will reflect
   * these changes.
   *
   * @param model the model to manipulate.
   * @param frame view that will reflect these changes.
   * @throws IllegalArgumentException if any arguments are <code>null</code>.
   */
  public RunScriptCommand(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    super(model, frame);
  }

  @Override
  public void execute() {
    StringReader scriptInput = new StringReader(frame.getScriptArea().getText());
   // frame.getScrptCtrlr() = MultiLayerIMEControllerImpl.controllerBuilder().model(model)
     //   .readable(scriptInput).buildController();
    // Fixme: Use the frame's controller to run the script.
    frame.getScrptCtrlr().run();
    frame.setImage();
    frame.renderLayers();
  }
}