package com.nozama.api.domain.vo;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.nozama.api.domain.enums.Currency;

@Embeddable
public class Price {
	
	@NotNull
	@Min(value = 1, message = "The amount must be at least $1.00")
	private BigDecimal price;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Currency currency;

	public Price() {}
	public Price(BigDecimal amount, Currency currency) {
		this.price = amount;
		this.currency = currency;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Currency getCurrency() {
		return currency;
	}

	@Override
	public int hashCode() {
		return Objects.hash(price, currency);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Price other = (Price) obj;
		return Objects.equals(price, other.price) && currency == other.currency;
	}	
}