package CS3500.model.operation;

import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;

/**
 * Todo: Javadocs
 */
public abstract class AFilter implements IOperation{
  private final IMatrix<Double> kernelToApply;

  public AFilter() {
    this.kernelToApply = this.initKernel();
  }

  protected abstract IMatrix<Double> initKernel();

  public void apply(IImage image) {

  }
}