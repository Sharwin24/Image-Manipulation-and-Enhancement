package cs3500.controller.commands;

import cs3500.Utils;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * An abstract implementation of a text command that can be passed to the
 * {@link cs3500.controller.IMultiLayerIMEController}. Helps to reduce duplicate code in its
 * extending concrete subclasses.
 */
public abstract class AIMECommand implements IIMECommand {

  @Override
  public void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException {
    Utils.checkNotNull(lineScan, "cannot process a command with a null scanner");
    Utils.checkNotNull(mdl, "cannot process a command with a null model");
    Utils.checkNotNull(vw, "cannot process a command with a null view");

    vw.renderLayers();

    this.handleArgs(lineScan, mdl, vw);
  }

  /**
   * Processes the input contained in the given scanner of a line of a command.
   * Reads the first word in the line to determine the command, then uses a function object that
   * is a concrete subclass of this class to execute that command based on the (optional) arguments
   * that come after it, and communicates this information to the given {@link IMultiLayerModel} to
   * execute the command from the model's perspective. Also communicates any relevant I/O
   * to the given {@link IIMEView}.
   *
   * @param lineScan the {@link Scanner} over the line of a command to be processed.
   * @param mdl the model that that should execute the processed command.
   * @param vw the view to which I/O data should be sent.
   */
  protected abstract void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw);

}
