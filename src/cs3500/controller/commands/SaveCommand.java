package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

public class SaveCommand extends AIMECommand {

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    mdl.save();
    vw.write("saved current image to image history");

  }
}
