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
   * @param s
   * @param mdl
   * @param vw
   * @throws IllegalArgumentException
   * @throws IllegalStateException
   */
  void execute(Scanner s, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException;
}
