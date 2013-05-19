package com.ufukuzun.myth.dialect.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

public class AjaxResponse {

	private transient AjaxRequest<?> ajaxRequest;
	
	private transient ModelAndView modelAndView;

	private final List<ResponseUpdate> updates = new ArrayList<ResponseUpdate>();

	public AjaxResponse() {
	}

	public AjaxResponse(AjaxRequest<?> ajaxRequest, ModelAndView modelAndView) {
		this.ajaxRequest = ajaxRequest;
		this.modelAndView = modelAndView;
	}

	public void add(String id, String content) {
		updates.add(new ResponseUpdate(id, content));
	}

	public List<ResponseUpdate> getUpdates() {
		return updates;
	}

	public AjaxRequest<?> getAjaxRequest() {
		return ajaxRequest;
	}

	public ModelAndView getModelAndView() {
		return modelAndView;
	}

}
