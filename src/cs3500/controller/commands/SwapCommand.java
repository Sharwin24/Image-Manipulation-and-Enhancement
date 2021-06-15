package cs3500.controller.commands;

import cs3500.Utils;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * TODO
 */
public class SwapCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
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
