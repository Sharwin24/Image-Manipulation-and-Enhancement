package cs3500.controller.commands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#addLayer()} call in the {@link IMultiLayerModel}, to be used to implement
 * the <i>command design pattern</i> in the {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>new</code>", to create a <code>new</code> layer to be edited.</p>
 */
public class NewLayerCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw) {
    vw.write("adding new layer at index " + mdl.getLayers().size());
    mdl.addLayer();
    vw.write("added!");
  }
}