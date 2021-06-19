package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#deleteLayer(int)} call in the {@link IMultiLayerModel}, to be used to
 * implement the <i>command design pattern</i> in the {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>delete [n]</code>", where <code>[n]</code> represents the index of the layer to be deleted,
 * starting from 0, indexed left to right, (see {@link IMultiLayerModel#setCurrentLayer(int)}.
 */
public class DeleteCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IMEView vw) {
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