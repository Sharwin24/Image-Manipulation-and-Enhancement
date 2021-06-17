package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.operation.IOperation;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#addLayer()} call in the {@link IMultiLayerModel}, to be used to implement
 * the <i>command design pattern</i> in the {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>create</code>", to <code>create</code> a new layer to be edited.</p?
 */

public class CreateCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    vw.write("creating a new layer at index " + mdl.getLayers().size());
    mdl.addLayer();
  }
}
