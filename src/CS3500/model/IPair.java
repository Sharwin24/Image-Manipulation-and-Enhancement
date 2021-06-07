package CS3500.model;

/**
 * A pair of two things, parameterized over their types {@code X} and {@code Y}, respectively.
 */
public interface IPair<X, Y> {

  /**
   * Returns the first item in this {@link IPair}.
   *
   * @return the first item in this {@link IPair}.
   */
  X getFirst();

  /**
   * Returns the second item in this {@link IPair}.
   *
   * @return the second item in this {@link IPair}.
   */
  Y getSecond();

}
