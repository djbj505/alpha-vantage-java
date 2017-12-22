package com.avj.utils.operations;

public enum AlphaDatatypes {
	CSV,
	JSON;
	
	@Override
	public String toString() {
		return this.name().toLowerCase();
	}
}
