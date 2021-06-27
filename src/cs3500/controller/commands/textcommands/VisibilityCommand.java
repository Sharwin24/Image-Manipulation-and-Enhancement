package cs3500.controller.commands.textcommands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#toggleInvisible(int)} ()} call in the {@link IMultiLayerModel},
 * to be used to
 * implement the <i>command design pattern</i> in the
 * {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>visibility [n]</code>", in order to toggle the <code>visibility</code> of the layer at
 * index <code>[n]</code>, indexed from 0, from left to right (see
 * {@link IMultiLayerModel#toggleInvisible(int)}.
 */
public class VisibilityCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IMEView vw)
      throws IllegalArgumentException, IllegalStateException {
    if (lineScan.hasNext()) {
      String inp = lineScan.next();

      try {
        int layerToToggle = Integer.parseInt(inp);
        try {
          vw.write("toggling visibility of layer " + layerToToggle);
          mdl.toggleInvisible(layerToToggle);
          vw.write("toggled!");
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