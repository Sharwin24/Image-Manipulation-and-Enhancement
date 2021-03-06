package cs3500.model.operation;

import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;

/**
 * Abstract class to represent any Filter Operation to be applied to an Image. Supports the
 * extension of filters and offers methods to apply the filter to all channels or one.
 */
public abstract class AFilter implements IFilter {

  private final IMatrix<Double> kernelToApply;

  /**
   * Constructs an AFilter with a subclass' kernel.
   *
   * @throws IllegalArgumentException if kernel is invalid size.
   */
  protected AFilter() throws IllegalArgumentException {
    this.kernelToApply = this.initKernel();
    if (this.kernelToApply.getWidth() % 2 == 0 || this.kernelToApply.getHeight() % 2 == 0) {
      throw new IllegalArgumentException("Kernel cannot be evenly sized");
    }
  }

  /**
   * Each subclass has a unique kernel to init.
   *
   * @return the IMatrix which represents the Kernel.
   */
  protected abstract IMatrix<Double> initKernel();


  @Override
  public IImage applyFilterToAllChannels(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }
    IImage image1 = this.applyToChannel(image, EChannelType.RED);
    IImage image2 = this.applyToChannel(image1, EChannelType.GREEN);
    IImage image3 = this.applyToChannel(image2, EChannelType.BLUE);
    return image3;
  }

  /**
   * Applies the filter to the Image to the given channelType.
   *
   * @param image       the Image to apply the filter to.
   * @param channelType the channel to apply the filter on.
   * @return the IImage after applying the filter.
   * @throws IllegalArgumentException if given arguments are null.
   */
  private IImage applyToChannel(IImage image, EChannelType channelType)
      throws IllegalArgumentException {
    if (image == null || channelType == null) {
      throw new IllegalArgumentException("Image or ChannelType is null");
    }
    IMatrix<IPixel> pixelArray = image.getPixelArray();
    IMatrix<IPixel> pixelArrayCopy = image.getPixelArray().copy();
    int kernelHeight = this.kernelToApply.getHeight();
    for (int i = 0; i < pixelArray.getHeight(); i++) {
      for (int j = 0; j < pixelArray.getWidth(); j++) {
        IPixel currentPixel = pixelArray.getElement(i, j);
        IMatrix<Integer> channelValues =
            this.getNeighborsOfPixel(i, j, kernelHeight, pixelArray, channelType);
        double newPixelValue = this.dotProductKernel(channelValues);
        IPixel newPixel = this.getNewPixel(currentPixel, channelType, (int) newPixelValue);
        pixelArrayCopy.updateEntry(newPixel, i, j);
      }
    }
    return new ImageImpl(pixelArrayCopy);
  }

  /**
   * Creates a new IPixel with the overwritten value depending on the channel.
   *
   * @param currentPixel the given pixel to overwrite.
   * @param channelType  the channel to overwrite.
   * @param newValue     the value to write in.
   * @return the new {@link IPixel} with the new value written in.
   * @throws IllegalArgumentException if the pixel or channelType is invalid.
   */
  private IPixel getNewPixel(IPixel currentPixel, EChannelType channelType, int newValue)
      throws IllegalArgumentException {
    if (currentPixel == null || channelType == null) {
      throw new IllegalArgumentException("Arguments are NULL");
    }
    IPixel newPixel;
    switch (channelType) {
      case RED:
        newPixel = createNewRed(currentPixel, newValue);
        break;
      case GREEN:
        newPixel = createNewGreen(currentPixel, newValue);
        break;
      case BLUE:
        newPixel = createNewBlue(currentPixel, newValue);
        break;
      default:
        throw new IllegalArgumentException("Invalid Channel Type");
    }
    return newPixel;
  }

  /**
   * Creates a new Red pixel given the new value and the current pixel.
   *
   * @param currentPixel the current pixel to overrwrite.
   * @param newValue     the new value for the channel.
   * @return an {@link IPixel} with the new value.
   */
  private IPixel createNewRed(IPixel currentPixel, int newValue) {
    return new PixelImpl(newValue,
        currentPixel.getIntensity(EChannelType.GREEN),
        currentPixel.getIntensity(EChannelType.BLUE));
  }

  /**
   * Creates a new Green pixel given the new value and the current pixel.
   *
   * @param currentPixel the current pixel to overrwrite.
   * @param newValue     the new value for the channel.
   * @return an {@link IPixel} with the new value.
   */
  private IPixel createNewGreen(IPixel currentPixel, int newValue) {
    return new PixelImpl(currentPixel.getIntensity(EChannelType.RED),
        newValue,
        currentPixel.getIntensity(EChannelType.BLUE));
  }

  /**
   * Creates a new Blue pixel given the new value and the current pixel.
   *
   * @param currentPixel the current pixel to overrwrite.
   * @param newValue     the new value for the channel.
   * @return an {@link IPixel} with the new value.
   */
  private IPixel createNewBlue(IPixel currentPixel, int newValue) {
    return new PixelImpl(currentPixel.getIntensity(EChannelType.RED),
        currentPixel.getIntensity(EChannelType.GREEN),
        newValue);
  }

  /**
   * Calculates the dot product of {@code this.kernelToApply} and the given {@link IMatrix} which
   * represents the channel values.
   *
   * @param channelValues the values from a channel to perform a dot product on.
   * @return the result of the dot product of this instance's kernel and the channel values.
   */
  private double dotProductKernel(IMatrix<Integer> channelValues) {
    // Perform a dot product with this.kernelToApply and channelValues
    // return the calculated value
    double sum = 0;
    for (int i = 0; i < channelValues.getHeight(); i++) {
      for (int j = 0; j < channelValues.getWidth(); j++) {
        sum += (this.kernelToApply.getElement(i, j) * channelValues.getElement(i, j));
      }
    }
    return sum;
  }

  /**
   * Gets a Matrix of the given pixel's neighboring pixel's channel values. Zero is used for
   * neighbors that are out of bounds.
   *
   * @param x           The x, or column, position of the given pixel.
   * @param y           The y. or row, position of the given pixel.
   * @param kLen        The size of the kernel , which determines the size of the returned Matrix.
   * @param pixelMatrix the matrix of pixels that represent the image.
   * @param channelType the channel of pixel's values to fill the Matrix.
   * @return A {@link IMatrix} representing the neighboring pixel's values.
   */
  private IMatrix<Integer> getNeighborsOfPixel(int x, int y, int kLen,
      IMatrix<IPixel> pixelMatrix
      , EChannelType channelType) {
    IMatrix<Integer> valuesMatrix = new MatrixImpl<>(0, kLen, kLen);
    int lim = (int) (0.5 * kLen + 0.5);
    lim = kLen - lim;
    for (int row = -lim; row <= lim; row++) {
      for (int col = -lim; col <= lim; col++) {
        try {
          int pixelChannelValue =
              pixelMatrix.getElement(x + row, y + col).getIntensity(channelType);
          valuesMatrix.updateEntry(pixelChannelValue, (kLen / 2) + row, (kLen / 2) + col);
        } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
          // Do nothing
        }
      }
    }
    return valuesMatrix;
  }

  @Override
  public IImage apply(IImage applyTo) {
    return this.applyFilterToAllChannels(applyTo);
  }
}