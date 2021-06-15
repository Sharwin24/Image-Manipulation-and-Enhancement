package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

public class RedoCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    try {
      mdl.redo();
      vw.write("successfully redone");
    } catch (IllegalArgumentException e) {
      vw.write("Failed to redo: " + e.getMessage());
    }

  }
}
