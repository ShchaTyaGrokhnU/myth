package com.ufukuzun.myth.dialect.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ExpressionUtils {

	public final static Pattern DOLLAR_EXPRESSION_PATTERN = Pattern.compile("\\$\\{.+?\\}");
	
	public final static Pattern RENDER_EXPRESSION_PATTERN = Pattern.compile("\\[\\[(.+?)\\>\\>(.+?)\\]\\]");
	
	public static List<String> splitIdFragments(String value) {
		List<String> idFragments = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(value)) {
			value = StringUtils.remove(value, "${}");
			Matcher matcher = DOLLAR_EXPRESSION_PATTERN.matcher(value);
			while (matcher.find()) {
				idFragments.add(matcher.group());
				value = StringUtils.remove(value, matcher.group());
			}
			
			idFragments.addAll(Arrays.asList(StringUtils.split(value)));
		}
		
		return idFragments;
	}
	
	public static boolean isDollarExpression(String value) {
		List<String> idFragments = splitIdFragments(value);
		return !idFragments.isEmpty() && idFragments.size() == 1 && idFragments.get(0).matches(DOLLAR_EXPRESSION_PATTERN.pattern());
	}
	
	public static String[] splitRenderFragmentAndUpdates(String value) {
		String renderFragment = "";
		String updates = "";
		
		if (StringUtils.isNotBlank(value)) {
			Matcher matcher = RENDER_EXPRESSION_PATTERN.matcher(value);
			matcher.find();
			if (matcher.matches()) {
				renderFragment = matcher.group(1).trim();
				updates = matcher.group(2).trim();
			}
		}
		
		return new String[] {renderFragment, updates};
	}
	
	public static boolean isRenderExpression(String value) {
		String[] result = splitRenderFragmentAndUpdates(value);
		return StringUtils.isNotBlank(result[0]) && StringUtils.isNotBlank(result[1]);
	}
	
	public static List<String> extractRenderExpressions(String value) {
		List<String> renderExpressions = new ArrayList<String>();
		
		if (StringUtils.isNotBlank(value)) {
			Matcher matcher = RENDER_EXPRESSION_PATTERN.matcher(value);
			while (matcher.find()) {
				renderExpressions.add(matcher.group());
			}
		}
		
		return renderExpressions;
	}
	
	public static String removeRenderExpressions(String value) {
		if (StringUtils.isNotBlank(value)) {
			List<String> renderExpressions = extractRenderExpressions(value);
			for (String each : renderExpressions) {
				value = StringUtils.remove(value, each);
			}
		}
		
		return value;
	}
	
}
