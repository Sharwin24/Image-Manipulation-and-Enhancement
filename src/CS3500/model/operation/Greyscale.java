package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;

/**
 * Constructs a Greyscale Color Transform Object that can be used to apply the Greyscale Color
 * Transform.
 */
public class Greyscale extends AColorTransform {

  private final IMatrix<Double> greyscaleKernel;

  /**
   * Constructs a Greyscale Color Transform object to apply the greyscale transform to an IImage.
   */
  public Greyscale() {
    this.greyscaleKernel = this.initGreyscaleMatrix();
  }

  /**
   * Creates an {@link IMatrix} for the Greyscale kernel.
   *
   * @return a 3x3 {@link IMatrix<Double>} to represent the kernel.
   */
  private IMatrix<Double> initGreyscaleMatrix() {
    IMatrix<Double> matrix = new MatrixImpl<>(0.0, 3, 3);
    for (int i = 0; i < 3; i++) {
      matrix.updateEntry(0.2126, i, 0);
      matrix.updateEntry(0.7152, i, 1);
      matrix.updateEntry(0.0722, i, 2);
    }
    return matrix;
  }

  @Override
  public void apply(IImage image)
      throws IllegalArgumentException {
    this.applyColorTransform(image, this.greyscaleKernel);
  }
}