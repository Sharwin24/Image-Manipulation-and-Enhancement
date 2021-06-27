package cs3500.controller.commands.textcommands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.view.IMEView;
import java.util.Scanner;

/**
 * An interface for function objects that represent a command that can be supported
 * by the {@link IMultiLayerModel} interface. To be used to implement the command design pattern
 * in the {@link cs3500.controller.IMultiLayerIMEController} interface.
 * Each function object has an {@link IIMECommand#execute(Scanner, IMultiLayerExtraOperations,
 * IMEView)}
 * command with the signature {@code Scanner, IMultiLayerModel, IMEView -> void}, where the method
 * is assumed to act on the provided {@link IMultiLayerModel}, executing some action native to that
 * interface, and the provided {@link Scanner} is used to read that command specifically,
 * sending I/O feedback to the provided {@link IMEView}.
 */
public interface IIMECommand {

  /**
   * Executes the command specified by the first word in the line and communicates
   * this information to the model to actually execute the command, also provides
   * I/O data to the view.
   *
   * @param lineScan a {@link Scanner} over the line containing the input to execute a command
   *                 from.
   * @param mdl the model that will execute the command.
   * @param vw the view to send data to.
   * @throws IllegalArgumentException if any of the parameters are {@code null}.
   */
  void execute(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw)
      throws IllegalArgumentException;
}