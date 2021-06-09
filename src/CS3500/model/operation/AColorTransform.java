package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.IMatrix;
import CS3500.model.pixel.IPixel;
import CS3500.model.pixel.PixelImpl;

/**
 * Abstract class to represent a Color Transform.
 */
public abstract class AColorTransform implements IColorTransform {

  private final IMatrix<Double> kernel;

  /**
   * Constructs an AColorTransform with the subclass' kernel.
   */
  public AColorTransform() {
    this.kernel = this.initKernel();
  }

  /**
   * Initializes the kernel for the subclass' specific Color Transform.
   *
   * @return the IMatrix that represents the kernel.
   */
  protected abstract IMatrix<Double> initKernel();

  /**
   * Applies the given matrix transform to the given image.
   *
   * @param image       the image to apply
   * @param colorMatrix the matrix to apply to the image.
   */
  protected IImage applyColorTransform(IImage image, IMatrix<Double> colorMatrix) {
    // Iterate over the pixels in the image and for each pixel,
    // each row gets multiplied by r, g, b to create the new r', g', b'
    IMatrix<IPixel> pixelArray = image.getPixelArray();
    IMatrix<IPixel> pixelArrayCopy = image.getPixelArray().copy();
    for (int i = 0; i < pixelArray.getHeight(); i++) {
      for (int j = 0; j < pixelArray.getWidth(); j++) {
        IPixel currentPixel = pixelArray.getElement(i, j);
        int currentR = currentPixel.getIntensity(EChannelType.RED);
        int currentG = currentPixel.getIntensity(EChannelType.GREEN);
        int currentB = currentPixel.getIntensity(EChannelType.BLUE);
        int newR = (int) ((currentR * colorMatrix.getElement(0, 0))
            + (currentG * colorMatrix.getElement(0, 1))
            + (currentB * colorMatrix.getElement(0, 2)));
        int newG = (int) ((currentR * colorMatrix.getElement(1, 0))
            + (currentG * colorMatrix.getElement(1, 1))
            + (currentB * colorMatrix.getElement(1, 2)));
        int newB = (int) ((currentR * colorMatrix.getElement(2, 0))
            + (currentG * colorMatrix.getElement(2, 1))
            + (currentB * colorMatrix.getElement(2, 2)));
        pixelArrayCopy.updateEntry(new PixelImpl(newR, newG, newB), i, j);
      }
    }
    return new ImageImpl(pixelArrayCopy);
  }
}