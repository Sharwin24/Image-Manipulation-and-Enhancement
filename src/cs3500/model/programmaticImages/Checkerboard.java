package cs3500.model.programmaticImages;

import cs3500.Utils;
import cs3500.model.image.IImage;
import cs3500.model.image.ImageImpl;
import cs3500.model.matrix.IMatrix;
import cs3500.model.matrix.MatrixImpl;
import cs3500.model.pixel.IPixel;
import cs3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Todo: Javadocs
 */
public class Checkerboard implements IProgramImage<IImage> {

  @Override
  public IImage createProgramImage(int widthPx, int heightPx, int unitSizePx) {
    // check that arguments are non-negative
    Utils.checkIntBetween(widthPx, 0, Integer.MAX_VALUE);
    Utils.checkIntBetween(heightPx, 0, Integer.MAX_VALUE);
    Utils.checkIntBetween(unitSizePx, 0, Integer.MAX_VALUE);

    // to ensure the board has no incomplete squares;
    heightPx -= heightPx % unitSizePx;
    widthPx -= widthPx % unitSizePx;

    List<List<IPixel>> pxs = new ArrayList<>();

    for (int i = 0; i < heightPx; i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < widthPx; j++) {
        if ((i / unitSizePx % 2) == (j / unitSizePx % 2)) {
          thisRow.add(PixelImpl.BLACK);
        } else {
          thisRow.add(PixelImpl.WHITE);
        }
      }
      pxs.add(thisRow);
    }

    IMatrix<IPixel> pxMatrix = new MatrixImpl<>(pxs);
    return new ImageImpl(pxMatrix);
  }
}