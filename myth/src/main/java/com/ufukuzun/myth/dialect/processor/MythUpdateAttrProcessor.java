package com.ufukuzun.myth.dialect.processor;

import java.util.HashMap;
import java.util.Map;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;

import com.ufukuzun.myth.dialect.builder.AjaxEventBindingBuilder;
import com.ufukuzun.myth.dialect.util.ElementAndAttrUtils;

public class MythUpdateAttrProcessor extends AbstractAttributeModifierAttrProcessor {

	public final static String ATTR_NAME = "update";
	
	public static final String ATTR_NAME_WITH_PREFIX = ElementAndAttrUtils.getPrefixedName(ATTR_NAME);
	
	public MythUpdateAttrProcessor() {
		super(ATTR_NAME);
	}
	
	@Override
	public int getPrecedence() {
		return 12000;
	}

	@Override
	protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
		Map<String, String> values = new HashMap<String, String>();
		
		String[] binding = AjaxEventBindingBuilder.build(arguments, element, attributeName);
		values.put(binding[0], binding[1]);
		
		return values;
	}
    
	@Override
	protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
		return ModificationType.SUBSTITUTION;
	}

	@Override
	protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
		return true;
	}

	@Override
	protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String attributeName) {
		return false;
	}

}
