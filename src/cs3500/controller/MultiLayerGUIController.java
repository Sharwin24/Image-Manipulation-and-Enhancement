package cs3500.controller;

import cs3500.controller.commands.guicommands.BWNoiseCommand;
import cs3500.controller.commands.guicommands.CurrentLayerCommand;
import cs3500.controller.commands.guicommands.CustomNoiseCommand;
import cs3500.controller.commands.guicommands.DownScaleCommand;
import cs3500.controller.commands.guicommands.ExportOneCommand;
import cs3500.controller.commands.guicommands.GUIMosaicCommand;
import cs3500.controller.commands.guicommands.IGUICommand;
import cs3500.controller.commands.guicommands.ImportOneCommand;
import cs3500.controller.commands.guicommands.NewLayerCommand;
import cs3500.controller.commands.guicommands.OperationCommand;
import cs3500.controller.commands.guicommands.PureNoiseCommand;
import cs3500.controller.commands.guicommands.RainbowNoiseCommand;
import cs3500.controller.commands.guicommands.RedoCommand;
import cs3500.controller.commands.guicommands.RunScriptCommand;
import cs3500.controller.commands.guicommands.SwapLayersCommand;
import cs3500.controller.commands.guicommands.UndoCommand;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.view.GUIView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

public class MultiLayerGUIController implements IMultiLayerIMEController, ActionListener {

  private IMultiLayerExtraOperations model;
  private GUIView frame;
  private final Map<String, IGUICommand> actionsMap;

  /**
   * A Trivial initialization constructor that constructs a {@link MultiLayerIMEControllerImpl}
   * based on a given model to control, the view to render feedback to, and the {@link Readable} and
   * {@link Appendable} to read/write data I/O data from/to (resp.).
   *
   * @param model the model to control
   * @param frame The frame of the GUI.
   * @throws IllegalArgumentException if any of the parameters are {@code null}.
   */
  public MultiLayerGUIController(IMultiLayerExtraOperations model, GUIView frame)
      throws IllegalArgumentException {
    this.model = model;
    this.frame = frame;
    this.actionsMap = frame.initActionsMap();
  }


  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void run() {

  }
}