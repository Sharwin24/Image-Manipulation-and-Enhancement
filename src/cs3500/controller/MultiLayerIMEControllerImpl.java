package cs3500.controller;

import cs3500.model.IMultiLayerModel;
import cs3500.model.MultiLayerModelImpl;
import cs3500.model.image.IImage;
import cs3500.view.IIMEView;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class MultiLayerIMEControllerImpl implements IMultiLayerIMEController<IImage> {

  private final IMultiLayerModel mdl;
  private final Readable rd;
  private final Appendable ap;
  private final IIMEView vw;

  public MultiLayerIMEControllerImpl() {
    this.mdl = new MultiLayerModelImpl();
    this.rd = new InputStreamReader(System.in);
    this.ap = System.out;
    this.vw = new
  }
  @Override
  public void run(IMultiLayerIMEController<IImage> mdl) {

  }
}
