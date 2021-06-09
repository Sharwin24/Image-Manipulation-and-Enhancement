package CS3500.model.ProgramaticImages;

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
public class Checkerboard implements IProgramImage {

  @Override
  public IImage createProgramImage() {
    List<List<IPixel>> pxs = new ArrayList<>();

    for (int i = 0; i < 200; i++) {
      List<IPixel> thisRow = new ArrayList<>();
      for (int j = 0; j < 200; j++) {
        if ((i / 25 % 2) == (j / 25 % 2)) {
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