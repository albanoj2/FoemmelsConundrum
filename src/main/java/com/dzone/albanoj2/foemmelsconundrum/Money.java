package com.dzone.albanoj2.foemmelsconundrum;

import java.util.ArrayList;
import java.util.List;

public class Money {
	
	private long cents;
	private RemainderDistributor distributor;

	public Money (long cents, RemainderDistributor distributor) {
		this.cents = cents;
		this.distributor = distributor;
	}
	
	public long getCents () {
		return this.cents;
	}
	
	public Money add (long cents) {
		this.cents += cents;
		return this;
	}
	
	public List<Money> allocate (List<Integer> ratios) {
		
		if (ratios.size() == 0) {
			// No ratios were provided
			return new ArrayList<>();
		}
		
		// Obtain the divisor for the ratios (sum of individual ratio values)
		int ratioTotal = ratios.stream().mapToInt(Integer::intValue).sum();
		
		if (ratioTotal == 0 || this.cents == 0) {
			// The ratio totals 0
			List<Money> result = new ArrayList<>();
			
			// Add as many 0 values are there are zero ratios
			ratios.forEach(ratio -> result.add(new Money(0, this.distributor)));
			
			return result;
		}
		
		// Set the remainder to the original total
		long remainder = this.cents;
		
		// Allocate the list of money values to be returned
		List<Money> results = new ArrayList<>();
		
		for (int i = 0; i < ratios.size(); i++) {
			// Compute the value (total * percent)
			// I.e. ratio = {1,3}, then (1 / 3) and (2 / 3) respectively
			long value = (long) (this.cents * ratios.get(i) / (double) ratioTotal);
			
			// Store the result
			results.add(new Money(value, this.distributor));
			
			// Subtract the result amount from the remainder
			remainder -= results.get(i).getCents();
		}
		
		// Delegate to the distributor
		this.distributor.distribute(results, remainder);
		
		return results;
	}
	
	@Override
	public String toString () {
		return String.format("$%.2f", this.cents / 100.0);
	}
	
	@Override
	public boolean equals (Object other) {
		return ((Money) other).getCents() == this.cents;
	}
}
