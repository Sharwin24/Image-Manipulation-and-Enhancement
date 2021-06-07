package CS3500.model;

/**
 * A pair of two items. Essentially a utility class to allow methods to return two (related)
 * values.
 *
 * @param <X> the first item in this {@link PairImpl}, with type {@code X}.
 * @param <Y> the second item in this {@link PairImpl}, with type {@code Y}.
 */
public class PairImpl<X, Y> implements IPair<X, Y> {

  private final X first;
  private final Y second;


  /**
   * Constructs a {@link PairImpl} with two items.
   *
   * @param first  the first item in this {@link PairImpl}.
   * @param second the second item in this {@link PairImpl}.
   * @throws IllegalArgumentException if either the {@code first} or {@code second} item is
   * {@code null}.
   */
  public PairImpl(X first, Y second) throws IllegalArgumentException {
    if (first == null || second == null) {
      throw new IllegalArgumentException("pairs cannot contain null elements");
    }
    this.first = first;
    this.second = second;
  }

  @Override
  public X getFirst() {
    return this.first;
  }

  @Override
  public Y getSecond() {
    return this.second;
  }
}
