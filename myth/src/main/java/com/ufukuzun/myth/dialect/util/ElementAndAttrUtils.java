package com.ufukuzun.myth.dialect.util;

import java.util.List;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Document;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;

import com.ufukuzun.myth.dialect.MythDialect;

public class ElementAndAttrUtils {

	public static Element getBodyElement(Document document) {
		return getFirstLevelElement(document, "body");
	}
	
	public static Element getHeadElement(Document document) {
		return getFirstLevelElement(document, "head");
	}
	
	private static Element getFirstLevelElement(Document document, String elementName) {
		for (Element each : getElementByName(document.getElementChildren(), "html").getElementChildren()) {
			if (each.getUnprefixedNormalizedName().equalsIgnoreCase(elementName)) {
				return each;
			}
		}
		
		return null;
	}
	
	public static Element getElementByName(List<Element> elements, String elementName) {
		for (Element each : elements) {
			if (each.getNormalizedName().equalsIgnoreCase(elementName)) {
				return each;
			} else if (hasElementChildren(each)) {
				return getElementByName(each.getElementChildren(), elementName);
			}
		}
		
		return null;
	}
	
	public static Element getElementById(List<Element> elements, String elementId) {
		for (Element each : elements) {
			if (equalsAttribute(each, "id", elementId)) {
				return each;
			} else if (hasElementChildren(each)) {
				return getElementById(each.getElementChildren(), elementId);
			}
		}
		
		return null;
	}
	
	public static boolean equalsAttribute(Element element, String attribute, String value) {
		return element.hasAttribute(attribute) && element.getAttributeValue(attribute).equals(value);
	}
	
	public static String getPrefixedName(String elementName) {
		return MythDialect.DIALECT_PREFIX + ":" + elementName;
	}
	
	public static boolean hasElementChildren(Element element) {
		return !element.getElementChildren().isEmpty();
	}
	
	public static String getProcessedAttributeValue(final Arguments arguments, final Element element, final String attributeName) {
		String attributeValue = element.getAttributeValue(attributeName);
		return getProcessedAttributeValue(arguments, attributeValue);
	}
	
	public static String getProcessedAttributeValue(final Arguments arguments, final String attributeValue) {
		Object result = null;
		try {
			result = StandardExpressionProcessor.processExpression(arguments, attributeValue);
		} catch (Exception e) {
			// TODO: log the exception with Spring's logger
		}

		return (result == null ? attributeValue : result.toString());
	}
	
}
