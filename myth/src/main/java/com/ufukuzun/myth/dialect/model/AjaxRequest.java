package com.ufukuzun.myth.dialect.model;

import java.util.List;

public class AjaxRequest<T> {

	private T model;
	
	private transient boolean modelValid = true;
	
	private List<RequestUpdate> update;

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}

	public List<RequestUpdate> getUpdate() {
		return update;
	}

	public void setUpdate(List<RequestUpdate> update) {
		this.update = update;
	}

	public boolean isModelValid() {
		return modelValid;
	}
	
}
