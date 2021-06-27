package cs3500.controller.commands;

import cs3500.model.IMultiLayerExtraOperations;
import cs3500.model.IMultiLayerModel;
import cs3500.model.operation.Greyscale;
import cs3500.model.operation.IOperation;
import cs3500.model.operation.ImageBlur;
import cs3500.model.operation.Sepia;
import cs3500.model.operation.Sharpening;
import cs3500.view.IMEView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * <p>A function object used to represent the execution of a
 * {@link IMultiLayerModel#applyOperations(IOperation...)} call in the {@link IMultiLayerModel}, to
 * be used to implement the <i>command design pattern</i> in the {@link
 * cs3500.controller.IMultiLayerIMEController} class.</p>
 *
 * <p>This class, in particular, allows the user to input a command in the form
 * "<code>apply [op1] [op2] ... [opn]</code>", where <code>[opi]</code> represents the
 * <code>i</code>'th <code>op</code>eration to be applied to an image out of
 * <code>n</code> total
 * operations, where <code>i <= n</code>, and <code>i</code> and <code>n</code> are positive
 * integers, and where an operation is defined as one of the key value entries in {@link
 * ApplyCommand#initOperationsMap()}, a textual representation of a {@link IOperation} function
 * object, such as "<code>sepia, sharpen, </code>, ... etc. (See {@link
 * ApplyCommand#initOperationsMap()} for the full list of supported operations represented by
 * single-word commands).</p>
 */
public class ApplyCommand extends AIMECommand {

  private final Map<String, IOperation> operations = this.initOperationsMap();

  @Override
  public void handleArgs(Scanner lineScan, IMultiLayerExtraOperations mdl, IMEView vw)
      throws IllegalArgumentException, IllegalStateException {
    ArrayList<IOperation> toApply = new ArrayList<>();

    while (lineScan.hasNext()) {
      String anOperation = lineScan.next();
      if (operations.containsKey(anOperation)) {
        toApply.add(operations.get(anOperation));
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

  /**
   * <p>Initializes a {@link Map} between single-word, lowercase textual representations of {@link
   * IOperation} function objects, and a real object of their specific subtype, in an explicit
   * one-to-one mapping as defined below:.</p>
   * <p><table style="width:150%">
   *   <tr>
   *     <th>Textual Command  </th>
   *     <th>{@link IOperation} Function Object  </th>
   *     <th>Description</th>
   *   </tr>
   *   <tr>
   *     <td><code>sepia</code></td>
   *     <td>{@link Sepia}</td>
   *     <td>Applies a sepia filter to the image</td>
   *   </tr>
   *   <tr>
   *     <td><code>greyscale</code></td>
   *     <td>{@link Greyscale}</td>
   *     <td>applies a greyscale filter to the image</td>
   *   </tr>
   *   <tr>
   *     <td><code>sharpen</code></td>
   *     <td>{@link Sharpening}</td>
   *     <td>applies a sharpen filter to the image</td>
   *   </tr>
   *   <tr>
   *     <td><code>blur</code></td>
   *     <td>{@link ImageBlur}</td>
   *     <td>applies a blur filter to the image</td>
   *   </tr>
   * </table></p>
   *
   * @return a {@link Map} representing the above table.
   */
  private Map<String, IOperation> initOperationsMap() {
    Map<String, IOperation> operations = new HashMap<>();
    operations.putIfAbsent("sepia", new Sepia());
    operations.putIfAbsent("greyscale", new Greyscale());
    operations.putIfAbsent("sharpen", new Sharpening());
    operations.putIfAbsent("blur", new ImageBlur());

    return operations;
  }


  /**
   * Utility method to convert a {@link List} to an Array,
   * in order to more efficiently handle the Array signature of a varargs method,
   * in particular {@link AIMECommand#handleArgs(Scanner, IMultiLayerExtraOperations, IMEView)}.
   *
   * @param operations the {@link List} of operations to convert to an Array
   * @return the {@link List} of operations, converted to an Array
   * @throws IllegalArgumentException if the given List is {@code null}.
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