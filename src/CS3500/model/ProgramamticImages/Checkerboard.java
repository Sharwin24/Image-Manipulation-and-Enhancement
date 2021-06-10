package CS3500.model.ProgramamticImages;

import CS3500.model.image.IImage;
import CS3500.model.image.ImageImpl;
import CS3500.model.matrix.IMatrix;
import CS3500.model.matrix.MatrixImpl;
import CS3500.model.pixel.IPixel;
import CS3500.model.pixel.PixelImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * Todo: Javadocs
 */
public class Checkerboard implements IProgramImage<IImage> {

  @Override
  public IImage createProgramImage(int widthPx, int heightPx, int unitSizePx) {
    List<List<IPixel>> pxs = new ArrayList<>();

    for (int i = 0; i < heightPx; i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < widthPx; j++) {
        if ((i / unitSizePx % 2) == (j / unitSizePx % 2)) {
          thisRow.add(new PixelImpl(0, 0, 0));
        } else {
          thisRow.add(new PixelImpl(255, 255, 255));
        }
      }
      pxs.add(thisRow);
    }

    IMatrix<IPixel> pxMatrix = new MatrixImpl<>(pxs);
    return new ImageImpl(pxMatrix);
  }
}