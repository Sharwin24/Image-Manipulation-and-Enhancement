package CS3500.model;

// TODO: add new methods

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * A generic matrix of values. Provides greater flexibility than just using a 2D array list
 * to store a grid of values, and supports the ability to:
 * <ul>
 *   <li>
 *     Query a cell for its value
 *   </li>
 *   <li>
 *     Add two matrices together
 *   </li>
 *   <li>
 *     Element-wise multiply two matrices together
 *   </li>
 *   <li>
 *     Sum all of the values in that matrix
 *   </li>
 *   <li>
 *     Custom, deep equals method and suitable hashCode
 *   </li>
 *   <li>
 *     toString
 *   </li>
 * </ul>
 */
public interface IMatrix<X> { // TODO: JavaDocs

  X getElement(int row, int col)
      throws IllegalArgumentException;

  int getWidth();

  int getHeight();

  void fillWith(X entry)
      throws IllegalArgumentException;

  void updateEntry(X newEntry, int row, int col)
      throws IllegalArgumentException;
//
//  void add(IMatrix<X> toAdd)
//      throws IllegalArgumentException;;
//
//  void rowWiseMultiply(IMatrix<X> toMultiply)
//      throws IllegalArgumentException;

  void rowWiseOperation(BiFunction<X, X, X> binaryOperation, IMatrix<X> toCombine)
      throws IllegalArgumentException;

  <Y> IMatrix<Y> map(Function<X, Y> unaryOperation);

  IMatrix<X> copy();

  X sumAll(BiFunction<X, X, X> operation, X base);

  boolean equals(Object o);

  int hashCode();

  String toString();

}
