package cs3500.controller.commands.textcommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#save()} call in the {@link IMultiLayerModel}, to be used to
 * implement the <i>command design pattern</i> in the
 * {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>save</code>", in order to <code>save</code> the most recent iteration of this layer
 * to the layer's history.
 */
public class SaveCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    mdl.save();
    vw.write("saved current image to image history");

  }
}