//package CS3500.model.matrix;
//
//import CS3500.model.matrix.IMatrix;
//import java.util.function.BiFunction;
//
///**
// * TODO: JavaDocs
// */
//public interface IAlignableMatrix<X> extends IMatrix<X> {
//
//  IAlignableMatrix<X> alignedRowWiseOperation(BiFunction<X, X, X> binaryOperation, IMatrix<X> toCombine,
//      int firstAlignmentRow, int firstAlignmentCol, int secondAlignmentRow,
//      int secondAlignmenetCol) throws IllegalArgumentException;
//
//}