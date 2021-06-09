package CS3500.model.operation;

import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;

/**
 * Abstract class to represent a Color Transform Operation to be applied to an Image.
 */
public interface IColorTransform extends IOperation {


  /**
   * Applies the given matrix transform to the given image.
   *
   * @param image the image to apply
   * @return the IImage after the Color Transform.
   * @throws IllegalArgumentException if the arguments are null.
   */
  IImage apply(IImage image)
      throws IllegalArgumentException;
}