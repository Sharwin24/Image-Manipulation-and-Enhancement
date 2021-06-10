package CS3500.model;

import CS3500.Utils;
import CS3500.model.ProgrammaticImages.IProgramImage;
import CS3500.model.fileFormat.IFileFormat;
import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.MatrixImpl;
import CS3500.model.operation.IOperation;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: JavaDoc comments
 */
public class StateTrackingIMEModelImpl implements IStateTrackingIMEModel<IImage> {

  private IImage image; // The current image
  private final List<IImage> history; // I1 I2 I3 I4 I5 ...
  private int historyCounter;

  /**
   * TODO
   *
   */
  private StateTrackingIMEModelImpl() {
    this.image = new ImageImpl(new MatrixImpl<>());
    this.history = new ArrayList<>();
    this.historyCounter = 0;
  }

  /**
   * TODO
   * @param image
   * @throws IllegalArgumentException
   */
  public StateTrackingIMEModelImpl(IImage image)
  throws IllegalArgumentException {
    this.image = Utils.checkNotNull(image, "cannot construct an IME model with a null "
        + "image");
    this.history = new ArrayList<>();
    this.historyCounter = 0;
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
    if (this.historyCounter == 0 || this.history.size() == 1) {
      throw new IllegalArgumentException("Cannot undo after no changes");
    }
    this.historyCounter--;
    this.image = this.history.get(historyCounter);
  }

  @Override
  public void redo() // moves right
      throws IllegalArgumentException {
    if (this.history.size() == 1 || this.historyCounter == this.history.size() - 1) {
      throw new IllegalArgumentException("Cannot redo if there is nothing to redo");
    }
    this.historyCounter++;
    this.image = this.history.get(historyCounter);
  }

  @Override
  public void save() {
    IImage imageCopy;
    imageCopy = this.image.copy();
    this.history.add(imageCopy);
  }

  @Override
  public IImage retrieve() {
    return this.history.get(history.size() - 1);
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
   * TODO
   *
   * @param newImage
   * @throws IllegalArgumentException
   */
  private void setImage(IImage newImage)
      throws IllegalArgumentException {
    Utils.checkNotNull(newImage, "cannot set a new image that is null");
    this.save();
    this.image = newImage.copy();
  }


}