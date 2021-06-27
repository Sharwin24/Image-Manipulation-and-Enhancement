package cs3500.controller.commands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#undo()} call in the {@link IMultiLayerModel}, to be used to
 * implement the <i>command design pattern</i> in the
 * {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>undo</code>", in order to <code>undo</code> the most recent change to the layer.
 */
public class UndoCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    try {
      mdl.undo();
      vw.write("successfully undone");
    } catch (IllegalArgumentException e) {
      vw.write("Failed to undo: " + e.getMessage());
    }
  }
}