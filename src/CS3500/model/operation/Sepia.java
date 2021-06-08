package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;

/**
 * Class to represent a Sepia {@link AColorTransform}
 */
public class Sepia extends AColorTransform {

  private final IMatrix<Double> sepiaKernel;

  /**
   * Constructs a Sepia Color Transform Object that can be used to apply the Sepia Color Transform.
   */
  public Sepia() {
    this.sepiaKernel = this.initSepiaMatrix();
  }

  /**
   * Creates an {@link IMatrix} for the Sepia .
   *
   * @return a 3x3 {@link IMatrix<Double>} to represent the .
   */
  private IMatrix<Double> initSepiaMatrix() {
    IMatrix<Double> matrix = new MatrixImpl<>(1.0, 3, 3);
    matrix.updateEntry(0.393, 0, 0);
    matrix.updateEntry(0.769, 0, 1);
    matrix.updateEntry(0.189, 0, 2);
    matrix.updateEntry(0.349, 1, 0);
    matrix.updateEntry(0.686, 1, 1);
    matrix.updateEntry(0.168, 1, 2);
    matrix.updateEntry(0.272, 2, 0);
    matrix.updateEntry(0.534, 2, 1);
    matrix.updateEntry(0.131, 2, 2);
    return matrix;
  }

  @Override
  public void apply(IImage image)
      throws IllegalArgumentException {
    this.applyColorTransform(image, this.sepiaKernel);
  }
}