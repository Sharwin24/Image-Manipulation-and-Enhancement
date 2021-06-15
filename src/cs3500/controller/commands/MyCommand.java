package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.view.IIMEView;
import java.util.Scanner;

public class MyCommand extends AIMECommand {

  @Override
  protected void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw) {
    if (lineScan.hasNext()) {
      if (lineScan.next().equals("foo")) {
        vw.write("got foo");
      }
      if (lineScan.hasNext()) {
        if (lineScan.next().equals("bar")) {
          vw.write("got bar");
        }
      }
      vw.write("done looking for bar");
    }
    vw.write("done looking for foo");
  }

}
