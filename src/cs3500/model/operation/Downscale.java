package cs3500.model.operation;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.StateTrackingIMEModelImpl;
import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.layer.ILayer;
import cs3500.model.layer.Layer;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;

/**
 * Class to represent the operation of Downscaling an image.
 */
public class Downscale {

  private IMultiLayerModel model;
  private int newHeight;
  private int newWidth;

  /**
   * Constructs a Downscale function object for a given model's image(s) to the given new height and
   * width.
   *
   * @param model     the model to get the images from to downscale.
   * @param newHeight the new height to downscale the images to.
   * @param newWidth  the new width to downscale the images to.
   * @throws IllegalArgumentException if any arguments are null, or invalid.
   */
  public Downscale(IMultiLayerModel model, int newHeight, int newWidth)
      throws IllegalArgumentException {
    if (model == null) {
      throw new IllegalArgumentException("Arguments are null");
    }
    if (newHeight <= 0 || newWidth <= 0) {
      throw new IllegalArgumentException("Height and Width cannot be negative or zero");
    }
    int currentHeight = model.getImage().getHeight();
    int currentWidth = model.getImage().getWidth();
    if (newHeight >= currentHeight || newWidth >= currentWidth) {
      throw new IllegalArgumentException("New Height/Width have to be smaller than original");
    }
    this.model = model;
    this.newHeight = newHeight;
    this.newWidth = newWidth;
  }

  /**
   * Applies a downscale operation to all layers in the multi-layer model.
   */
  public void apply() {
    for (ILayer layer : this.model.getLayers()) {
      this.applyToLayer(layer);
    }
  }

  /**
   * Applies a downscale to a single layer.
   *
   * @param layer the layer to apply the downscale to.
   */
  private void applyToLayer(ILayer layer) {
    IImage image = layer.getModel().getImage();
    IMatrix<IPixel> pixels = image.getPixelArray();
    IMatrix<IPixel> newPixels = new MatrixImpl<>(new PixelImpl(0, 0, 0), this.newHeight,
        this.newWidth);
    int originalWidth = image.getWidth();
    int originalHeight = image.getHeight();
    for (int r = 0; r < newPixels.getHeight(); r++) {
      for (int c = 0; c < newPixels.getWidth(); c++) {
        double oldX = (c / (double) this.newWidth) * originalWidth;
        double oldY = (r / (double) this.newHeight) * originalHeight;
        if (oldX % 1 == 0 || oldY % 1 == 0) { // If either is an int
          newPixels.updateEntry(pixels.getElement((int) oldY, (int) oldX), r, c);
        } else {
          // floor and ceiling of (x,y)
          int floorX = (int) Math.floor(oldX);
          int floorY = (int) Math.floor(oldY);
          int ceilX = (int) Math.ceil(oldX);
          int ceilY = (int) Math.ceil(oldY);
          // Pixels ABCD:
          IPixel pixelA = pixels.getElement(floorY, floorX);
          IPixel pixelB = pixels.getElement(floorY, ceilX);
          IPixel pixelC = pixels.getElement(ceilY, floorX);
          IPixel pixelD = pixels.getElement(ceilY, ceilX);
          int red = 0;
          int green = 0;
          int blue = 0;
          for (EChannelType channel : EChannelType.values()) { // for all color channels
            double m = pixelB.getIntensity(channel) * (oldX - floorX)
                + pixelA.getIntensity(channel) * (ceilX - oldX);
            double n = pixelD.getIntensity(channel) * (oldX - floorX)
                + pixelC.getIntensity(channel) * (ceilX - oldX);
            double cp = n * (oldY - floorY) + m * (ceilY - oldY);
            switch (channel) {
              case RED:
                red = (int) cp;
                break;
              case GREEN:
                green = (int) cp;
                break;
              case BLUE:
                blue = (int) cp;
                break;
            }
          }
          IPixel newPixel = new PixelImpl(red, green, blue);

          newPixels.updateEntry(newPixel, r, c);
        }
      }
    }
    layer.modelLoad(new ImageImpl(newPixels));
//    layer = new Layer(new StateTrackingIMEModelImpl(new ImageImpl(newPixels)), layer.isInvisible(),
//        newPixels.getHeight(), newPixels.getWidth());
  }


}