package com.ufukuzun.myth.dialect.processor;

import java.util.Collections;
import java.util.Map;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;

import com.ufukuzun.myth.dialect.util.ElementAndAttrUtils;

public class MythUrlAttrProcessor extends AbstractAttributeModifierAttrProcessor {

	public final static String ATTR_NAME = "url";
	
	public static final String ATTR_NAME_WITH_PREFIX = ElementAndAttrUtils.getPrefixedName(ATTR_NAME);
	
	public MythUrlAttrProcessor() {
		super(ATTR_NAME);
	}
	
	@Override
	public int getPrecedence() {
		return 12100;
	}

	@Override
	protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
		return Collections.emptyMap();
	}

	@Override
	protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName, String newAttributeName) {
		return ModificationType.SUBSTITUTION;
	}

	@Override
	protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName, String newAttributeName) {
		return false;
	}

	@Override
	protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element, String attributeName) {
		return false;
	}

}
