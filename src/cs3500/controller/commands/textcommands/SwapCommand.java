package cs3500.controller.commands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#swapLayers(int, int)} call in the {@link IMultiLayerModel}, to be used to
 * implement the <i>command design pattern</i> in the
 * {@link cs3500.controller.IMultiLayerIMEController}
 * class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>swap [i1] [i2]</code>", in order to <code>swap</code> the layers contained at indices
 * <code>[i1]</code> and <code>[i2]</code>, indexed from 0, from left to right (see {@link
 * IMultiLayerModel#swapLayers(int, int)}.
 */
public class SwapCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw) {
    if (lineScan.hasNext()) {
      String inp1 = lineScan.next();
      try {
        int swapIdx1 = Integer.parseInt(inp1);

        if (lineScan.hasNext()) {
          String inp2 = lineScan.next();
          try {
            int swapIdx2 = Integer.parseInt(inp2);

            try {
              vw.write("swapping layers " + swapIdx1 + " and " + swapIdx2);
              mdl.swapLayers(swapIdx1, swapIdx2);
              vw.write("swapped!");
            } catch (IllegalArgumentException e) {
              vw.write("could not swap layers at indices " + swapIdx1
                  + " and " + swapIdx1 + ": " + e.getMessage());
            }
          } catch (NumberFormatException e) {
            vw.write("could not swap to index " + inp2);
          }
        }


      } catch (NumberFormatException e) {
        vw.write("could not swap from index " + inp1);
      }

    }
  }

}