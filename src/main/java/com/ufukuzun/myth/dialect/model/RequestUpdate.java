package com.ufukuzun.myth.dialect.model;

import java.util.List;

public class RequestUpdate {
	
	private String renderFragment;

	private List<String> updates;
	
	public String getRenderFragment() {
		return renderFragment;
	}

	public void setRenderFragment(String renderFragment) {
		this.renderFragment = renderFragment;
	}
	
	public List<String> getUpdates() {
		return updates;
	}

	public void setUpdates(List<String> ids) {
		this.updates = ids;
	}

}
