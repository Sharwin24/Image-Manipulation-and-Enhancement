package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * TODO
 */
public class UndoCommand implements IIMECommand {

  @Override
  public void execute(Scanner s, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    try {
      mdl.undo();
      vw.write("successfully undone");
    } catch (IllegalArgumentException e) {
      vw.write("Failed to undo: " + e.getMessage());
    }
  }
}
