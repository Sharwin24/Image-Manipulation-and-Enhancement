package CS3500.model.operation;

import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;

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
  protected IMatrix<Double> initKernel() {
    IMatrix<Double> matrix = new MatrixImpl<>(0.0, 5, 5);
    matrix.updateEntry(1.0, 2, 2);
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (i == 0 || i == 4 || j == 0 || j == 4) {
          matrix.updateEntry(-0.125, i, j);
        }
        if ((i == 1 && (j == 1 || j == 2 || j == 3)) // second row
            || (i == 2 && (j == 1 || j == 3)) // middle row
            || (i == 3 && (j == 1 || j == 2 || j == 3))) { // third row
          matrix.updateEntry(0.25, i, j);
        }
      }
    }
    return matrix;
  }
}