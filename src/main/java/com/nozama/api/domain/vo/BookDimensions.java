package com.nozama.api.domain.vo;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Embeddable
public class BookDimensions {
	
	@NotNull
	@Positive
	private Double height;
	
	@NotNull
	@Positive
	private Double width;
	
	@NotNull
	@Positive
	private Double depth;

	public BookDimensions(){}
	public BookDimensions(@NotNull @Positive Double height, @NotNull @Positive Double width,
			@NotNull @Positive Double depth) {
		this.height = height;
		this.width = width;
		this.depth = depth;
	}
	
	public Double getHeight() {
		return height;
	}
	public void setHeight(Double height) {
		this.height = height;
	}
	public Double getWidth() {
		return width;
	}
	public void setWidth(Double width) {
		this.width = width;
	}
	public Double getDepth() {
		return depth;
	}
	public void setDepth(Double depth) {
		this.depth = depth;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(depth, height, width);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDimensions other = (BookDimensions) obj;
		return Objects.equals(depth, other.depth) && Objects.equals(height, other.height)
				&& Objects.equals(width, other.width);
	}

	
	
}