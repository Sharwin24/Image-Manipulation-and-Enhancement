package cs3500.controller;

import cs3500.controller.commands.guicommands.IGUICommand;
import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.GUIView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Map;
import javax.swing.JCheckBox;

/**
 * Controls all of the functions of the GUI by implementing the command pattern to look up and
 * perform {@link IGUICommand}s/listeners from a {@link Map} in order to implement all of the
 * functionality of the {@link IMultiLayerExtraOperations} within the {@link GUIView}.
 */
public class MultiLayerGUIController implements IMultiLayerIMEController, ActionListener,
    ItemListener {

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
    this.actionsMap = frame.initActionsMap();
  }


  @Override
  public void actionPerformed(ActionEvent e) {
    if (!(actionsMap.containsKey(e.getActionCommand()))) {
      return; // throw new IllegalArgumentException("command not recognized");
    }
    actionsMap.get(e.getActionCommand()).execute();
  }

  @Override
  public void run() {
    // uses listener methods to accomplish "running"
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (!(actionsMap.containsKey(((JCheckBox) e.getItemSelectable()).getActionCommand()))) {
      return;
    }
    actionsMap.get(((JCheckBox) e.getItemSelectable()).getActionCommand()).execute();
  }
}