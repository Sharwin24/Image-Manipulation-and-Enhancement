package cs3500.controller;

import cs3500.controller.commands.guicommands.DownScaleCommand;
import cs3500.controller.commands.guicommands.ExportOneCommand;
import cs3500.controller.commands.guicommands.GUIMosaicCommand;
import cs3500.controller.commands.guicommands.IGUICommand;
import cs3500.controller.commands.guicommands.ImportOneCommand;
import cs3500.controller.commands.guicommands.NewLayerCommand;
import cs3500.controller.commands.guicommands.OperationCommand;
import cs3500.controller.commands.guicommands.SwapLayersCommand;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.view.GUIView;
import cs3500.view.GUIView.CheckerBoardCommand;
import cs3500.view.GUIView.CurrentLayerCommand;
import cs3500.view.GUIView.DeleteLayerCommand;
import cs3500.view.GUIView.LoadScriptCommand;
import cs3500.view.GUIView.ThemeCommand;
import cs3500.view.GUIView.ViewGitHubCommand;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

public class MultiLayerGUIController implements IMultiLayerIMEController, ActionListener {

  private IMultiLayerExtraOperations model;
  private GUIView frame;
  private final Map<String, IGUICommand> actionsMap = this.initActionsMap();

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
  }

  /**
   * Returns a Hashmap of the string commands to the command objects. Helps to implement the command
   * pattern for all listeners of the supported events in the GUI frame.
   *
   * @return a {@link HashMap} of the commands from a string to their function objects that execute
   * the promised action.
   */
  private Map<String, IGUICommand> initActionsMap() {

    Map<String, IGUICommand> actionsMap = new HashMap<>();

    actionsMap.putIfAbsent("new", new NewLayerCommand(model, frame));
    actionsMap.putIfAbsent("mosaic", new GUIMosaicCommand(model, frame));
    actionsMap.putIfAbsent("import one", new ImportOneCommand(model, frame));
    actionsMap.putIfAbsent("sepia", new OperationCommand(model, frame, new Sepia()));
    actionsMap.putIfAbsent("greyscale", new OperationCommand(model, frame, new Greyscale()));
    actionsMap.putIfAbsent("sharpen", new OperationCommand(model, frame, new Sharpening()));
    actionsMap.putIfAbsent("blur", new OperationCommand(model, frame, new ImageBlur()));
    actionsMap.putIfAbsent("downscale", new DownScaleCommand(model,frame));
    actionsMap.putIfAbsent("export one", new ExportOneCommand(model,frame));
    actionsMap.putIfAbsent("set current layer", new CurrentLayerCommand(model,frame));
    actionsMap.putIfAbsent("checkerboard", new CheckerBoardCommand(model,frame));
    actionsMap.putIfAbsent("delete", new DeleteLayerCommand(model,frame));
    actionsMap.putIfAbsent("bw noise", new BWNoiseCommand(model,frame));
    actionsMap.putIfAbsent("rainbow noise", new RainbowNoiseCommand(model,frame));
    actionsMap.putIfAbsent("pure noise", new PureNoiseCommand(model,frame));
    actionsMap.putIfAbsent("custom noise", new CustomNoiseCommand(model,frame));
    actionsMap.putIfAbsent("undo", new UndoCommand(model,frame));
    actionsMap.putIfAbsent("redo", new RedoCommand(model,frame));
    actionsMap.putIfAbsent("run script", new RunScriptCommand(model,frame));
    actionsMap.putIfAbsent("load script", new LoadScriptCommand(model,frame));
    actionsMap.putIfAbsent("light theme", new ThemeCommand(model,frame,LIGHT_THEME));
    actionsMap.putIfAbsent("dark theme", new ThemeCommand(model,frame,DARK_THEME));
    actionsMap.putIfAbsent("matrix theme", new ThemeCommand(model,frame,MATRIX_THEME));
    actionsMap.putIfAbsent("retro theme", new ThemeCommand(model,frame,RETRO_THEME));
    actionsMap.putIfAbsent("load script", new LoadScriptCommand(model,frame));
    actionsMap.putIfAbsent("swap", new SwapLayersCommand(model, frame));
    actionsMap.putIfAbsent("github", new ViewGitHubCommand(model,frame));

    return actionsMap;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  }

  @Override
  public void run() {

  }
}