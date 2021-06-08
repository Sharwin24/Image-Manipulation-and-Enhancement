package CS3500.model.operation;

import CS3500.model.channel.EChannelType;
import CS3500.model.image.IImage;
import CS3500.model.matrix.IMatrix;

/**
 * Todo: Javadocs
 */
public abstract class AFilter implements IOperation {

  private final IMatrix<Double> kernelToApply;

  /**
   * Constructs an AFilter with a subclass' kernel.
   *
   * @throws IllegalArgumentException if kernel is invalid size.
   */
  public AFilter() throws IllegalArgumentException {
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

  /**
   * Applies the filter to the Image to the given channelType.
   *
   * @param image       the Image to apply the filter to.
   * @param channelType the channel to apply the filter on.
   */
  public void apply(IImage image, EChannelType channelType) {
    // Will use the IImage to traverse the matrix of pixels and for each pixel,
    // collect the surrounding pixels up to the size of the kernel. The kernel is centered
    // at the current pixel.
    // Calculate what the new value for the current pixel should be by
    // summing each surrounding pixel's channel multiplied by the corresponding kernel value
    // If a pixel is out of bounds, use 0 as the channel value
  }
}