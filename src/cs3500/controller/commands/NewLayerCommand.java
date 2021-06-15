package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

public class NewLayerCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    vw.write("adding new layer");
    mdl.addLayer();
  }
}
