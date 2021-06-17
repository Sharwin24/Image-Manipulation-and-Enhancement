package cs3500.model.layer;

import cs3500.model.IMultiLayerModel;
import cs3500.model.IStateTrackingIMEModel;
import cs3500.model.image.IImage;

public class Layer implements ILayer<IImage> {

  private boolean isInvisible;
  private IStateTrackingIMEModel<IImage> model;

  @Override
  public boolean isInvisible() {
    return false;
  }

  @Override
  public void importImage(IImage image) {

  }

  @Override
  public IMultiLayerModel<IImage> getModel() {
    return null;
  }
}