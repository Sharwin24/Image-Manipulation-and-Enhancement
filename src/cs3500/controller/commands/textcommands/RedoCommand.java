package cs3500.controller.commands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#redo()} call in the {@link IMultiLayerModel}, to be used to
 * implement the <i>command design pattern</i> in the
 * {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>redo</code>", in order to <code>redo</code> the most recent change to the layer.
 */

public class RedoCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    try {
      mdl.redo();
      vw.write("successfully redone");
    } catch (IllegalArgumentException e) {
      vw.write("Failed to redo: " + e.getMessage());
    }

  }
}