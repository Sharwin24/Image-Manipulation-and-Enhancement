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
   * TODO
   *
   * @param lineScan
   * @param mdl
   * @param vw
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   */ // TODO : check for null arguments in implementations
  void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException;
}
