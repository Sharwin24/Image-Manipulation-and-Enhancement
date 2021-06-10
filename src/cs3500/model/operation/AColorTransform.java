package cs3500.model.operation;

import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;

/**
 * Abstract class to represent a Color Transform.
 */
public abstract class AColorTransform implements IColorTransform {

  private final IMatrix<Double> kernel;

  /**
   * Constructs an AColorTransform with the subclass' kernel.
   */
  protected AColorTransform() {
    this.kernel = this.initKernel();
  }

  /**
   * Initializes the kernel for the subclass' specific Color Transform.
   *
   * @return the IMatrix that represents the kernel.
   */
  protected abstract IMatrix<Double> initKernel();


  @Override
  public IImage apply(IImage applyTo)
      throws IllegalArgumentException {
    if (applyTo == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    IMatrix<IPixel> pixelArray = applyTo.getPixelArray();
    IMatrix<IPixel> pixelArrayCopy = applyTo.getPixelArray().copy();
    IMatrix<Double> colorMatrix = this.kernel;
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