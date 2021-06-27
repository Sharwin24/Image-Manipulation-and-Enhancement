package cs3500.controller.commands.textcommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.view.IMEView;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Command for the Mosaic operation.
 */
public class MosaicCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw) {
    try {
      int numSeeds = Integer.parseInt(lineScan.next());
      mdl.mosaic(numSeeds);
      vw.write("mosaiced with " + numSeeds + " seeds");
    } catch (NumberFormatException | NoSuchElementException e) {
      vw.write("failed to mosaic");
    }
  }
}