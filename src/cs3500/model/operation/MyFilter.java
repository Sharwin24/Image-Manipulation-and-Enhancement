package cs3500.model.operation;

import cs3500.model.image.IImage;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;

/**
 * Class for custom made filter with custom kernel.
 */
public class MyFilter extends AFilter {

  /**
   * Constructs a MyFilter with the kernel.
   */
  public MyFilter() {
    super();
  }


  @Override
  protected IMatrix<Double> initKernel() {
    return new MatrixImpl<>(1.0, 3, 3);
  }

  @Override
  public IImage apply(IImage applyTo) {
    return this.applyFilterToAllChannels(applyTo);
  }

  @Override
  public String toString() {
    return "MyFilter";
  }
}