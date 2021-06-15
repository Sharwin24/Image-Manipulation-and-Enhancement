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
import java.util.Map;
import java.util.Scanner;

public class ApplyCommand implements IIMECommand {

  private final Map<String, IOperation> operations = this.initOperationsMap();

  @Override
  public void execute(Scanner lineScan, IMultiLayerModel mdl, IIMEView vw)
      throws IllegalArgumentException, IllegalStateException {
    ArrayList<IOperation> toApply = new ArrayList<>();

    while(lineScan.hasNext()) {
      String anOperation = lineScan.next();
      if (operations.containsKey(anOperation)) {
        toApply.add(operations.get(anOperation));
      }
    }

    try {
      mdl.applyOperations((IOperation[]) toApply.toArray());
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
}
