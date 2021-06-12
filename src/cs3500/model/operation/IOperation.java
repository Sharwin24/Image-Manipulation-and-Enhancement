package cs3500.model.operation;

import cs3500.model.image.IImage;

/**
 * An interface to represent an operation (i.e. a filter, color transformation, etc)
 * to be applied to an {@link IImage},
 * used as a function object whose {@link IOperation#apply(IImage)} method can apply it to a
 * given {@link IImage}.
 */
public interface IOperation {

  /**
   * When new Operations get added, they can extend this interface.
   *
   * @param applyTo the image to apply to
   * @return the Image after the application.
   */
  IImage apply(IImage applyTo);

  /**
   * Provides a Textual representation of the Operation.
   *
   * @return a String.
   */
  String toString();
}