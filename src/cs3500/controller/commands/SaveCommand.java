package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

public class SaveCommand implements IIMECommand {

  @Override
  public void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {

    mdl.save();
    vw.write("saved to current image to image history");

  }
}
