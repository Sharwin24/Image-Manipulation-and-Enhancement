package CS3500.model;

/**
 * TODO: JavaDoc comments
 * @param <Z>
 */
public interface IStateTrackingIMEModel<Z> extends IIMEModel<Z> {

  IStateTrackingIMEModel undo();

  IStateTrackingIMEModel redo();

  void save();

}
