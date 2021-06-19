package cs3500.model;

import cs3500.model.image.IImage;

/**
 * An extension of the {@link IIMEModel} interface to include methods for keeping track of the state
 * of images and of operations applied to them. Such functionality includes the ability to undo and
 * redo changes applied to images, save the current iteration of an image to the history of changes,
 * and retrieve the most recent iteration of an image.
 */
public interface IStateTrackingIMEModel extends IIMEModel {

  /**
   * Restores the most recently saved state of the image that is being modified.
   *
   * @throws IllegalArgumentException if there is no state to revert to--Undoing nothing is
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
   * Returns a copy of this model.
   *
   * @return an {@link IStateTrackingIMEModel} that is a copy of this one.
   */
  IStateTrackingIMEModel copy();
}