package cs3500.model.operation;

import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to represent the {@link IOperation} for Sharpening an Image.
 */
public class Sharpening extends AFilter {

  /**
   * Constructs a Sharpening AFilter to apply to an image.
   */
  public Sharpening() {
    super();
  }

  @Override
  public IMatrix<Double> initKernel() {
    List<Double> topBottomRows =
        new ArrayList<>(Arrays.asList(-0.125, -0.125, -0.125, -0.125, -0.125));
    List<Double> middleRows =
        new ArrayList<>(Arrays.asList(-0.125, 0.25, 0.25, 0.25, -0.125));
    List<List<Double>> allRows = new ArrayList<>();
    // Create list of rows and add rows
    allRows.add(topBottomRows); // Add top row to list
    allRows.add(middleRows);
    allRows.add(middleRows); // Add 3 middle rows to list
    allRows.add(middleRows);
    allRows.add(topBottomRows); // Add bottom row to list
    IMatrix<Double> matrix = new MatrixImpl<>(allRows);
    matrix.updateEntry(1.0, 2, 2); // Center is 1.0
    return matrix;
  }

  @Override
  public String toString() {
    return "Sharpening";
  }
}