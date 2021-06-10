package CS3500;

import java.util.List;

/**
 * Static utility methods to be used liberally in all parts of the IME project. Supports general,
 * frequently used functions, such as:
 * <ul>
 *   <li>
 *     {@link Utils#checkNotNull(Object, String)} to check if an object is null and throw an error
 *     message.
 *   </li>
 *   <li>
 *     {@link Utils#println(String)} to print a String with a newline below it.
 *   </li>
 *   <li>
 *     {@link Utils#paddedPrint(String)} to print a String padded above and below with a newline.
 *   </li>
 * </ul>
 */
public class Utils {

  /**
   * Checks if the argument {@code toCheck} is {@code null}. If it is, an {@link
   * IllegalArgumentException} is thrown with the desired {@code errorMsg}.
   *
   * @param toCheck  the argument to check for nullness
   * @param errorMsg the message to be thrown if {@code toCheck} is {@code null}.
   * @param <X>      the type of the argument {@code toCheck}.
   * @return {@code toCheck}, if it is not {@code null}.
   * @throws IllegalArgumentException if {@code toCheck} is {@code null}.
   */
  public static <X> X checkNotNull(X toCheck, String errorMsg)
      throws IllegalArgumentException {
    if (toCheck == null) {
      throw new IllegalArgumentException(errorMsg);
    }
    return toCheck;
  }

  /**
   * Checks that each element of the provided list is not {@code null}, and throws an
   * {@link IllegalArgumentException} if it is.
   *
   * @param toCheckContents the list to check for null contents.
   * @param errorMsg the message to display if a null list element is found.
   * @param <X> the type of element in the provided list
   * @throws IllegalArgumentException if any of the contents of the provided list are {@code null}.
   */
  public static <X> void checkNotNullListContents(List<X> toCheckContents, String errorMsg) {
    for (X x : toCheckContents) {
      checkNotNull(x, errorMsg);
    }
  }

  /**
   * Checks if the int {@code toCheck} is less than or equal to {@code upperBound} and greater than
   * or equal to {@code lowerBound}, and throws an {@link IllegalArgumentException} if it is not.
   * Otherwise, returns the int {@code toCheck}.
   *
   * @param toCheck    the int to check whether it is in the inclusive range [{@code
   *                   lowerBound},{@code upperBound}].
   * @param lowerBound the lowest possible integer that {@code toCheck} can be.
   * @param upperBound the highest possible integer that {@code toCheck} can be.
   * @return {@code toCheck}, if it is in the inclusive range [{@code lowerBound},{@code
   * upperBound}].
   * @throws IllegalArgumentException if {@code toCheck} does not lie in the inclusive range [{@code
   *                                  lowerBound},{@code upperBound}].
   */
  public static int checkIntBetween(int toCheck, int lowerBound, int upperBound)
      throws IllegalArgumentException {
    if (toCheck < lowerBound || toCheck > upperBound) {
      throw new IllegalArgumentException("integer " + toCheck + " out of bounds for range ["
          + lowerBound + "," + upperBound + "].");
    }
    return toCheck;
  }

  /**
   * Checks if {@code toSet} lies in the inclusive range of integers
   * [{@code lowerBound},{@code upperBound}]. If it is, {@code toSet} is returned. Otherwise,
   * whatever number of {{@code lowerBound}, {@code upperBound}} that {@code toSet} is closest to is
   * returned.
   * @param toSet the number to return in the range of [{@code lowerBound},{@code upperBound}].
   * @param lowerBound the smallest possible {@code int} that {@code toSet} can be set to.
   * @param upperBound the greatest possible {@code int} that {@code toSet} can be set to.
   * @return an {@code int} in the inclusive range of [{@code lowerBound},{@code upperBound}], as
   * described above.
   */
  public static int setIntBetween(int toSet, int lowerBound, int upperBound) {
    if (toSet > upperBound) {
      return upperBound;
    } else if (toSet < lowerBound) {
      return lowerBound;
    }
    return toSet;
  }

  /**
   * Similar to {@link System#out#println(String)}, but instead of printing to the console, this
   * method just returns the given {@link String} {@code toPad}, padded with a newline below it.
   *
   * @param toPad the {@link String} to pad with a newline below it.
   * @return {@code toPad}, padded with a newLine below it
   * @throws IllegalArgumentException if {@code toPad} is {@code null}.
   */
  public static String println(String toPad)
      throws IllegalArgumentException {
    return checkNotNull(toPad, "cannot pad a null String") + "\n";
  }

  /**
   * Pads the given {@link String} {@code toPad} with a newline, above and below.
   *
   * @param toPad the {@link String} to pad with a newline, above and below
   * @return {@code toPad}, padded with a newline above and below
   * @throws IllegalArgumentException if {@code toPad} is {@code null}.
   */
  public static String paddedPrint(String toPad)
      throws IllegalArgumentException {
    return println("") + println(checkNotNull(
        toPad, "cannot padded print a null String"));
  }

}