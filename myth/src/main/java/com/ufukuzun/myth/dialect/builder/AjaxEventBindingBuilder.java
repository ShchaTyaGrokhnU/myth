package com.ufukuzun.myth.dialect.builder;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.ufukuzun.myth.dialect.processor.MythEventAttrProcessor;
import com.ufukuzun.myth.dialect.processor.MythProcessAttrProcessor;
import com.ufukuzun.myth.dialect.processor.MythUpdateAttrProcessor;
import com.ufukuzun.myth.dialect.processor.MythUrlAttrProcessor;
import com.ufukuzun.myth.dialect.util.ElementAndAttrUtils;
import com.ufukuzun.myth.dialect.util.ExpressionUtils;

public class AjaxEventBindingBuilder {
	
	public final static String requestUpdateFormat = "{\"renderFragment\":%s, \"updates\":[%s]}";
	
	public final static String ajaxEventBindingFormat = "Myth.ajax(this, {\"update\":[%s], \"process\":[%s], \"url\":\"%s\"}); return false;";

	public static String[] build(Arguments arguments, Element element, String attributeName) {
		String[] binding = new String[2];
		
		binding[0] = getEventString(arguments, element);
		binding[1] = String.format(
				ajaxEventBindingFormat,
				getUpdateString(arguments, element),
				getProcessString(arguments, element),
				getUrlString(arguments, element)
			);
		
		return binding;
	}
	
	private static String getEventString(Arguments arguments, Element element) {
		String event = ElementAndAttrUtils.getProcessedAttributeValue(arguments, element, MythEventAttrProcessor.ATTR_NAME_WITH_PREFIX);
		return "on" + (StringUtils.isNotBlank(event) ? event : "click");
	}
	
	private static String getProcessString(Arguments arguments, Element element) {
		return getProcessedFragmentIdsWithQoute(arguments, element, MythProcessAttrProcessor.ATTR_NAME_WITH_PREFIX);
	}
	
	private static String getUrlString(Arguments arguments, Element element) {
		return ElementAndAttrUtils.getProcessedAttributeValue(arguments, element, MythUrlAttrProcessor.ATTR_NAME_WITH_PREFIX);
	}
	
	private static String getUpdateString(Arguments arguments, Element element) {
		StringBuilder stringBuilder = new StringBuilder();
		
		String attributeValue = element.getAttributeValue(MythUpdateAttrProcessor.ATTR_NAME_WITH_PREFIX);
		
		List<String> renderExpressions = ExpressionUtils.extractRenderExpressions(attributeValue);
		for (String each : renderExpressions) {
			stringBuilder.append(getRequestUpdateString(arguments, ExpressionUtils.splitRenderFragmentAndUpdates(each)) + ", ");
		}
		
		List<String> updates = ExpressionUtils.splitIdFragments(ExpressionUtils.removeRenderExpressions(attributeValue));
		for (String each : updates) {
			stringBuilder.append(getRequestUpdateString(arguments, each) + ", ");
		}
		
		return StringUtils.removeEnd(stringBuilder.toString(), ", ");
	}
	
	private static String getRequestUpdateString(Arguments arguments, String value) {
		return getRequestUpdateString(arguments, new String[] {value, value});
	}
	
	private static String getRequestUpdateString(Arguments arguments, String[] array) {
		return String.format(
				requestUpdateFormat,
				getIdsWithQoute(arguments, array[0], false, true),
				getProcessedFragmentIdsWithQoute(arguments, array[1])
			);
	}
	
	private static String getProcessedFragmentIdsWithQoute(Arguments arguments, Element element, String attributeName) {
		return getIdsWithQoute(arguments, element.getAttributeValue(attributeName), true, false);
	}
	
	private static String getProcessedFragmentIdsWithQoute(Arguments arguments, String attributeValue) {
		return getIdsWithQoute(arguments, attributeValue, true, false);
	}
	
	private static String getIdsWithQoute(Arguments arguments, String attributeValue, boolean process, boolean encode) {
		List<String> ids = ExpressionUtils.splitIdFragments(attributeValue);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (String each : ids) {
			String id = each;
			if (process) {
				id = ElementAndAttrUtils.getProcessedAttributeValue(arguments, each);
			}
			if (encode) {
				id = new String(Base64.encodeBase64(each.getBytes()));
			}
			stringBuilder.append("\"" + id + "\", ");
		}
		
		return StringUtils.removeEnd(stringBuilder.toString(), ", ");
	}
	
}
