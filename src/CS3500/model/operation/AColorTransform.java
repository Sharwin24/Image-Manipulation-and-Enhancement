package CS3500.model.operation;

import CS3500.ImageUtil;
import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;

/**
 * Abstract class to represent a Color Transform.
 */
public abstract class AColorTransform implements IOperation {

  /**
   * Applies the object's color transform matrix to the image.
   *
   * @param image the image to apply the color transform to.
   */
  public abstract void apply(IImage image);

  /**
   * Applies the given matrix transform to the given image.
   *
   * @param image                the image to apply
   * @param colorTransformMatrix the matrix to apply to the image.
   */
  protected void applyColorTransform(IImage image, IMatrix<Double> colorTransformMatrix) {

  }
}