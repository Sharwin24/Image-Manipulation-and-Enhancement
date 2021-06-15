package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * TODO
 */
public class DeleteCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    if (lineScan.hasNext()) {
      String inp = lineScan.next();

      try {
        int indexToDelete = Integer.parseInt(inp);

        try {
          vw.write("deleting layer " + indexToDelete);
          mdl.deleteLayer(indexToDelete);
        } catch (IllegalArgumentException e) {
          vw.write("could not delete layer " + indexToDelete + ": "
          + e.getMessage());
        }

      } catch (NumberFormatException e) {
        vw.write("Illegal index " + inp + ". Must be an integer in the inclusive range"
            + " [0," + mdl.getLayers().size() + "]");
      }
    }
  }
}
