package cs3500.controller.commands;

import cs3500.Utils;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * TODO
 */
public abstract class AIMECommand implements IIMECommand {

  @Override
  public void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException {
    Utils.checkNotNull(lineScan, "cannot process a command with a null scanner");
    Utils.checkNotNull(mdl, "cannot process a command with a null model");
    Utils.checkNotNull(vw, "cannot process a command with a null view");

    this.handleArgs(lineScan, mdl, vw);
    vw.write("line execution complete");
  }

  /**
   * TODO
   *
   * @param lineScan
   * @param mdl
   * @param vw
   */
  protected abstract void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw);

}
