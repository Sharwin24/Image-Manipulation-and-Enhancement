package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#setCurrentLayer(int)} call in the {@link IMultiLayerModel}, to
 * be used to implement the <i>command design pattern</i> in the {@link
 * cs3500.controller.IMultiLayerIMEController} class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>current [n]</code>", where <code>n</code> represents the index of the layer
 * to be set to the current, starting from 0, indexed left to right,
 * (see {@link IMultiLayerModel#setCurrentLayer(int)}.</p>
 */
public class CurrentCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    if (lineScan.hasNext()) {
      String inp = lineScan.next();

      try {
        int layerNum = Integer.parseInt(inp);
        try {
          vw.write("setting the current working layer to layer #" + layerNum);
          mdl.setCurrentLayer(layerNum);
        } catch (IllegalArgumentException e) {
          vw.write("invalid layer number: " + layerNum + ", out of bounds for "
              + "layers 0-" + mdl.getLayers().size());
        }
      } catch (NumberFormatException e) {
        vw.write("invalid layer number: " + inp + ": provide a number");
      }
    }
  }
}
