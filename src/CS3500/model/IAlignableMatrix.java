package CS3500.model;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * TODO: JavaDocs
 */
public interface IAlignableMatrix<X> extends IMatrix<X> {

  void alignedRowWiseOperation(BiFunction<X, X, X> binaryOperation, IMatrix<X> toCombine);

}
