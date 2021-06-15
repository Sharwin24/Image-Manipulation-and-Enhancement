package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

public class CurrentCommand extends AIMECommand {


  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    if (lineScan.hasNext()) {
      String inp = lineScan.next();

      try {
        int layerNum = Integer.parseInt(inp);
        try {
          vw.write("setting the current working layer to layer #" + layerNum);
          mdl.setCurrentLayer(layerNum);
        } catch (IllegalArgumentException e) {
          vw.write("invalid layer number: " + layerNum);
        }
      } catch (NumberFormatException e) {
        vw.write("invalid layer number: " + inp);
      }
    }
  }
}
