package CS3500.model;

import CS3500.Utils;
import java.awt.Image;
import java.util.Stack;

/**
 * TODO: JavaDoc comments
 */
public class StateTrackingIMEModelImpl implements IStateTrackingIMEModel<IImage> {
  private final IImage image;
  private final Stack<IImage> history;

  public StateTrackingIMEModelImpl(IImage image) {
    this.image = Utils.checkNotNull(image, "cannot construct an IME model with a null "
        + "image");
    this.history = new Stack<>();

  }
  @Override
  public void filter(Kernel kernel, IImage image, IChannel channel, int imageRow, int imageCol) {

  }

  @Override
  public IStateTrackingIMEModel undo() {
    return null;
  }

  @Override
  public IStateTrackingIMEModel redo() {
    return null;
  }

  @Override
  public void save() {
    this.history.push(image);
  }
}
