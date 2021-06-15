package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

/**
 * TODO
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
