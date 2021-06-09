package CS3500.model.operation;

import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;

public class MyFilter extends AFilter {

  /**
   * A Class for my own filter.
   */
  public MyFilter() {
    super();
  }


  @Override
  protected IMatrix<Double> initKernel() {
    return new MatrixImpl<>(1.0, 3, 3);
  }
}