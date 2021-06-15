package cs3500.controller.commands;

import cs3500.model.IMultiLayerModel;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.view.IIMEView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ApplyCommand extends AIMECommand {

  private final Map<String, IOperation> operations = this.initOperationsMap();

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {
    ArrayList<IOperation> toApply = new ArrayList<>();

    while (lineScan.hasNext()) {
      String anOperation = lineScan.next();
      if (operations.containsKey(anOperation)) {
        toApply.add(operations.get(anOperation));
        vw.write("got operation \"" + anOperation + "\"");
      } else {
        vw.write("did not recognize operation \"" + anOperation + "\"");
      }
    }

    try {
      IOperation[] ops = this.operationsToArray(toApply);
      vw.write("applying operations: " + toApply);
      mdl.applyOperations(ops);
    } catch (IllegalArgumentException e) {
      vw.write("could not apply operations: " + e.getMessage());
    }

  }

  private Map<String, IOperation> initOperationsMap() {
    Map<String, IOperation> operations = new HashMap<>();
    operations.putIfAbsent("sepia", new Sepia());
    operations.putIfAbsent("greyscale", new Greyscale());
    operations.putIfAbsent("sharpen", new Sharpening());
    operations.putIfAbsent("blur", new ImageBlur());

    return operations;
  }


  /**
   * TODO
   *
   * @param operations
   * @return
   * @throws IllegalArgumentException
   */
  private IOperation[] operationsToArray(List<IOperation> operations)
      throws IllegalArgumentException {
    int sz = operations.size();
    IOperation[] opsAsArray = new IOperation[sz];

    for (int i = 0; i < sz; i++) {
      opsAsArray[i] = operations.get(i);
    }

    return opsAsArray;
  }
}
