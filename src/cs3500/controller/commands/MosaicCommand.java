package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * TODO
 */
public class MosaicCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IMEView vw) {
    try {
      int numSeeds = Integer.parseInt(lineScan.next());
      mdl.mosaic(numSeeds);
      vw.write("mosaiced with " + numSeeds + " seeds");
    } catch (NumberFormatException | NoSuchElementException e) {
      vw.write("failed to mosaic");
    }
  }
}
