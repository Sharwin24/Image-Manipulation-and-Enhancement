package CS3500.model.operation;

import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;

/**
 * Class to represent a Greyscale {@link AColorTransform}.
 */
public class Greyscale extends AColorTransform {

  /**
   * Constructs a Greyscale Color Transform object to apply the greyscale transform to an IImage.
   */
  public Greyscale() {
    super();
  }

  @Override
  protected IMatrix<Double> initKernel() {
    IMatrix<Double> matrix = new MatrixImpl<>(0.0, 3, 3);
    for (int i = 0; i < 3; i++) {
      matrix.updateEntry(0.2126, i, 0);
      matrix.updateEntry(0.7152, i, 1);
      matrix.updateEntry(0.0722, i, 2);
    }
    return matrix;
  }

  @Override
  public String toString() {
    return "Greyscale";
  }
}