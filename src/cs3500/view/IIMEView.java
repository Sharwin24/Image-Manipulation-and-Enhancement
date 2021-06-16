package cs3500.view;

/**
 *
 */
public interface IIMEView {

  /**
   * @return
   */
  String toString();

  /**
   * @param toWrite
   * @throws IllegalStateException
   * @throws IllegalArgumentException
   */
  void write(String toWrite)
      throws IllegalStateException, IllegalArgumentException;

}