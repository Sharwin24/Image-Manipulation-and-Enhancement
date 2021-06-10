package cs3500.model;

import cs3500.model.image.IImage;
import cs3500.model.matrix.IMatrix;
import java.util.List;
import java.util.Stack;

/**
 * : JavaDoc comments
 *
 * @param <Z>
 */
public interface IStateTrackingIMEModel<Z> extends IIMEModel<Z> {

  /**
   * Restores the most recently saved state of the image that is being modified
   *
   * @throws IllegalArgumentException if there is no state to revert to--Undoing nothing is \
   *                                  impossible.
   */
  void undo()
      throws IllegalArgumentException;

  /**
   * Restores the state of the image before the user reverted to an older state.
   *
   * @throws IllegalArgumentException if the current state is the most recent--redoing when there
   *                                  was no undoing in the first place is impossible.
   */
  void redo()
      throws IllegalArgumentException;

  /**
   * Saves the current state of the image being edited.
   */
  void save();

  /**
   * Retrieves the last saved IImage.
   *
   * @return the last saved IImage.
   */
  IImage retrieve();

  Stack<IImage> getUndo();

  Stack<IImage> getRedo();

}