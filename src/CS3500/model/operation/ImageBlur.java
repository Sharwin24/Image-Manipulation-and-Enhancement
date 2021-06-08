package CS3500.model.operation;

import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;

/**
 * Todo:
 */
public class ImageBlur extends AFilter {

  /**
   * Constructs an ImageBlur filter to apply to an image.
   */
  public ImageBlur() throws IllegalArgumentException {
    super();
  }

  @Override
  protected IMatrix<Double> initKernel() {
    IMatrix<Double> matrix = new MatrixImpl<>(0.0, 3, 3);
    matrix.updateEntry(0.0625, 0, 0);
    matrix.updateEntry(0.0625, 2, 0);
    matrix.updateEntry(0.0625, 0, 2);
    matrix.updateEntry(0.0625, 2, 2);
    matrix.updateEntry(0.125, 0, 1);
    matrix.updateEntry(0.125, 1, 0);
    matrix.updateEntry(0.125, 1, 2);
    matrix.updateEntry(0.125, 2, 1);
    matrix.updateEntry(0.25, 1, 1);
    return matrix;
  }
}