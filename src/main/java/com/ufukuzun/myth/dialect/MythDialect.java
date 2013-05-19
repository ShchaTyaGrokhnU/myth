package com.ufukuzun.myth.dialect;

import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.ufukuzun.myth.dialect.processor.MythEventAttrProcessor;
import com.ufukuzun.myth.dialect.processor.MythProcessAttrProcessor;
import com.ufukuzun.myth.dialect.processor.MythUpdateAttrProcessor;
import com.ufukuzun.myth.dialect.processor.MythUrlAttrProcessor;

public class MythDialect extends AbstractDialect {
	
	public static final String DIALECT_PREFIX = "myth";
	
	@Override
	public String getPrefix() {
		return DIALECT_PREFIX;
	}

	@Override
	public boolean isLenient() {
		return false;
	}
	
	@Override
	public Set<IProcessor> getProcessors() {
		Set<IProcessor> processors = new HashSet<IProcessor>();
		processors.add(new MythUpdateAttrProcessor());
		processors.add(new MythEventAttrProcessor());
		processors.add(new MythUrlAttrProcessor());
		processors.add(new MythProcessAttrProcessor());
		
		return processors;
	}
	
}
