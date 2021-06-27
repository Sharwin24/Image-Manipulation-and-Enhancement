package cs3500.model;

import cs3500.Utility;
import cs3500.model.channel.EChannelType;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.layer.ILayer;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.Downscale;
import cs3500.model.operation.IOperation;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import cs3500.model.programmaticimages.IProgramImage;
import java.util.Stack;

/**
 * A class to represent a model to track the state of an Image and to apply operations to it. In
 * this implementation, each time a change is made to an image, that change is kept track of in the
 * image history. This implementation also uses two {@link Stack}s to keep track of the
 * redone/undone states of images after/before successive operations are applied to them.
 */
public class StateTrackingIMEModelImpl implements IStateTrackingIMEModel {

  private IImage image; // The current image
  private final Stack<IImage> undoHistory;
  private final Stack<IImage> redoHistory;


  /**
   * Constructs a Model that tracks the state of an image, enabling the user to undo/redo
   * operations. Initializes the current Image to an empty one.
   */
  public StateTrackingIMEModelImpl() {
    this.image = new ImageImpl(new MatrixImpl<>());
    this.undoHistory = new Stack<>();
    this.redoHistory = new Stack<>();
  }

  /**
   * Constructs a Model with a given Image to start with.
   *
   * @param image the image to start the model with.
   * @throws IllegalArgumentException if the image is null.
   */
  public StateTrackingIMEModelImpl(IImage image)
      throws IllegalArgumentException {
    this.image = Utility.checkNotNull(image, "cannot construct an IME model with a null "
        + "image");
    this.undoHistory = new Stack<>();
    this.undoHistory.push(image);
    this.redoHistory = new Stack<>();
  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {
    for (IOperation op : operations) {
      IImage recentImage = this.image.copy();
      // System.out.println("Applying " + op.toString() + "...");
      this.setImage(op.apply(recentImage));
    }
  }


  @Override
  public void undo()
      throws IllegalArgumentException {
    if (this.undoHistory.empty()) {
      throw new IllegalArgumentException("Nothing to Undo");
    }
    this.redoHistory.push(this.image.copy());
    this.image = this.undoHistory.pop().copy();
  }

  @Override
  public void redo()
      throws IllegalArgumentException {
    if (this.redoHistory.empty()) {
      throw new IllegalArgumentException("Nothing to Redo");
    }
    this.image = this.redoHistory.pop().copy();
    this.undoHistory.push(this.image.copy());
  }

  @Override
  public void save() {
    IImage imageCopy = this.image.copy();
    this.undoHistory.push(imageCopy);
    this.redoHistory.clear();
  }

  @Override
  public void load(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Image is null");
    }
    this.image = image.copy();
  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx)
      throws IllegalArgumentException {
    this.setImage(imgToSet.createProgramImage(widthPx, heightPx, unitSizePx));
  }

  @Override
  public IImage getImage() {
    return image;
  }

  @Override
  public IStateTrackingIMEModel copy() {
    return new StateTrackingIMEModelImpl(this.image.copy());
  }

  /**
   * Sets the current image to the given image, using a deep copy to avoid immutability.
   *
   * @param newImage the image to set the current image to.
   * @throws IllegalArgumentException if the given image is null.
   */
  private void setImage(IImage newImage)
      throws IllegalArgumentException {
    Utility.checkNotNull(newImage, "cannot set a new image that is null");
    this.save();
    this.image = newImage.copy();
  }

  @Override
  public void mosaic(int numSeeds) {
    this.setImage(this.image.mosaic(numSeeds));
  }

  @Override
  public void downscaleLayers(int newHeight, int newWidth)
      throws IllegalArgumentException {
    IImage image = this.getImage();
    IMatrix<IPixel> pixels = image.getPixelArray();
    IMatrix<IPixel> newPixels = new MatrixImpl<>(new PixelImpl(0, 0, 0), newHeight,
        newWidth);
    int originalWidth = image.getWidth();
    int originalHeight = image.getHeight();
    for (int r = 0; r < newPixels.getHeight(); r++) {
      for (int c = 0; c < newPixels.getWidth(); c++) {
        double oldX = (c / (double) newWidth) * originalWidth;
        double oldY = (r / (double) newHeight) * originalHeight;
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
    this.setImage(new ImageImpl(newPixels));
  }

}