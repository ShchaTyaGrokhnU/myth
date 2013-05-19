package com.ufukuzun.myth.dialect.spec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;
import org.thymeleaf.dom.Node;
import org.thymeleaf.fragment.IFragmentSpec;

public class AttributeNameAndValueFragmentSpec implements IFragmentSpec {

	private List<String> attributeNames;
	
	private String attributeValue;
	
	public AttributeNameAndValueFragmentSpec(String attributeValue, String ... attributeNames) {
		this.attributeNames = Arrays.asList(attributeNames);
		this.attributeValue = attributeValue;
	}

	@Override
	public List<Node> extractFragment(Configuration configuration, List<Node> nodes) {
		List<Node> extraction = new ArrayList<Node>();
		
		for (String eachAttributeName: attributeNames) {
			extraction = extractFragmentByAttributeNameAndValue(nodes, eachAttributeName, attributeValue);
			if (!extraction.isEmpty()) {
				break;
			}
		}
		
		return extraction.isEmpty() ? extraction : Arrays.asList(extraction.get(0));
	}

	private List<Node> extractFragmentByAttributeNameAndValue(List<Node> nodes, String attributeName, String attributeValue) {
		String normalizedAttributeName = Node.normalizeName(attributeName);

		List<Node> fragmentNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			List<Node> extraction = extractFragmentFromNode(node, normalizedAttributeName, attributeValue);
			if (extraction != null) {
				fragmentNodes.addAll(extraction);
			}
		}

		return fragmentNodes;
	}

	private List<Node> extractFragmentFromNode(Node node, String attributeName, String attributeValue) {
		if (node instanceof NestableNode) {
			NestableNode nestableNode = (NestableNode) node;
			if (nestableNode instanceof Element) {
				Element element = (Element) nestableNode;
				if (attributeName != null) {
					if (element.hasNormalizedAttribute(attributeName)) {
						String elementAttrValue = element.getAttributeValue(attributeName);
						if (elementAttrValue != null && elementAttrValue.trim().equals(attributeValue)) {
							return Collections.singletonList((Node) nestableNode);
						}
					}
				}
			}
			
			List<Node> extraction = new ArrayList<Node>();
			List<Node> children = nestableNode.getChildren();
			for (Node child : children) {
				List<Node> childResult = extractFragmentFromNode(child, attributeName, attributeValue);
				if (childResult != null) {
					extraction.addAll(childResult);
				}
			}
			return extraction;
		}

		return null;
	}

}
