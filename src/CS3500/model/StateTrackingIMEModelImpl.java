package CS3500.model;

import CS3500.Utils;
import CS3500.model.channel.IChannel;
import CS3500.model.fileFormat.IFileFormat;
import CS3500.model.image.IImage;
import CS3500.model.operation.IOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * TODO: JavaDoc comments
 */
public class StateTrackingIMEModelImpl implements IStateTrackingIMEModel<IImage> {

  private IImage image; // The current image
  private final List<IImage> history; // I1 I2 I3 I4 I5 ...
  private int historyCounter;

  public StateTrackingIMEModelImpl(IImage image) {
    this.image = Utils.checkNotNull(image, "cannot construct an IME model with a null "
        + "image");
    this.history = new ArrayList<>();
    this.historyCounter = 0;
  }

  @Override
  public void applyOperations(IOperation... operations) throws IllegalArgumentException {
    // Todo:
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
  public void importImage(IFileFormat format, String fileName) {
    this.setImage(format.importImage(fileName));
  }

  @Override
  public void exportImage(IFileFormat format, String fileName) {

  }

  private void setImage(IImage newImage) {
    this.save();
    this.image = newImage;
  }

}