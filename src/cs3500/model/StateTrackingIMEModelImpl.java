package cs3500.model;

import cs3500.Utils;
import cs3500.model.programmaticImages.IProgramImage;
import cs3500.model.fileFormat.IFileFormat;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.operation.IOperation;
import java.util.Stack;

/**
 * A class to represent a model to track the state of an Image and to apply operations to it.
 */
public class StateTrackingIMEModelImpl implements IStateTrackingIMEModel<IImage> {

  private IImage image; // The current image
  private final Stack<IImage> undoHistory;
  private final Stack<IImage> redoHistory;


  /**
   * Constructs a Model that tracks the state of an image, enabling the user to undo/redo
   * operations. Initializes the current Image to an empty one.
   */
  private StateTrackingIMEModelImpl() {
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
    this.image = Utils.checkNotNull(image, "cannot construct an IME model with a null "
        + "image");
    this.undoHistory = new Stack<>();
    this.undoHistory.push(image);
    this.redoHistory = new Stack<>();
  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {
    for (IOperation op : operations) {
      IImage recentImage = this.image.copy();
      System.out.println("Applying " + op.toString() + "...");
      this.setImage(op.apply(recentImage));
    }
  }


  @Override
  public void undo() // moves left
      throws IllegalArgumentException {
    // Undoing pops off from the undo stack, and sets the current image to it, and pushes to redo
    this.redoHistory.push(this.image.copy());
    this.image = this.undoHistory.pop().copy();

  }

  @Override
  public void redo() // moves right
      throws IllegalArgumentException {
    // Redo pops from redo, and pushes to undo

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
  public IImage retrieve() {
    return this.image;
  }

  @Override
  public void importImage(IFileFormat format, String fileName)
      throws IllegalArgumentException {
    this.setImage((IImage) format.importImage(fileName)); // safe up-cast
  }

  @Override
  public void exportImage(IFileFormat format, String fileName)
      throws IllegalArgumentException {
    System.out.println("Exporting \"" + fileName + "\"...");
    format.exportImage(fileName, this.image);
    System.out.println("Finished Exporting \"" + fileName + "\"!");
  }

  @Override
  public void setProgrammaticImage(IProgramImage imgToSet, int widthPx, int heightPx,
      int unitSizePx)
      throws IllegalArgumentException {
    this.setImage((IImage) imgToSet.createProgramImage(widthPx, heightPx, unitSizePx)); // safe cast
  }

  /**
   * Sets the current image to the given image, using a deep copy to avoid immutability.
   *
   * @param newImage the image to set the current image to.
   * @throws IllegalArgumentException if the given image is null.
   */
  private void setImage(IImage newImage)
      throws IllegalArgumentException {
    Utils.checkNotNull(newImage, "cannot set a new image that is null");
    this.save();
    this.image = newImage.copy();
  }


}