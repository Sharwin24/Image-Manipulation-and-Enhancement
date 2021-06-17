package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * An interface for function objects that represent a command that can be supported
 * by the {@link IMultiLayerModel} interface. To be used to implement the command design pattern
 * in the {@link cs3500.controller.IMultiLayerIMEController} interface.
 * Each function object has an {@link IIMECommand#execute(Scanner, IMultiLayerModel, IIMEView)}
 * command with the signature {@code Scanner, IMultiLayerModel, IIMEView -> void}, where the method
 * is assumed to act on the provided {@link IMultiLayerModel}, executing some action native to that
 * interface.
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
  void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException;
}
