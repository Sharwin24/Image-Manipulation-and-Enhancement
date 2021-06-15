package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Map;
import java.util.Scanner;

/**
 * TODO
 */
public class VisibilityCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {
    if (lineScan.hasNext()) {
      String inp = lineScan.next();

      try {
        int layerToToggle = Integer.parseInt(inp);
        try {
          vw.write("toggling visibility of layer " + layerToToggle);
          mdl.toggleInvisible(layerToToggle);
        } catch (IllegalArgumentException e) {
          vw.write("could not toggle visibility of layer " + layerToToggle +
              ": " + e.getMessage());
        }
      } catch (NumberFormatException e) {
        vw.write("could not toggle visibility of layer \"" + inp + "\": layers must "
            + "be an integer in valid range of the number of present layers");
      }
    }
  }
}
