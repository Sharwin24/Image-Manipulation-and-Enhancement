package cs3500.controller;

import cs3500.model.IMultiLayerModel;

public interface IMultiLayerIMEController<Z> {

  void run(IMultiLayerModel<Z> mdl)
      throws IllegalArgumentException, IllegalStateException;
}
