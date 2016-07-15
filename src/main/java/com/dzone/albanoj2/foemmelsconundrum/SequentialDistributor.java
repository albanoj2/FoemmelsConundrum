package com.dzone.albanoj2.foemmelsconundrum;
import java.util.List;

public class SequentialDistributor implements RemainderDistributor {

	@Override
	public void distribute(List<Money> values, long remainder) {
		
		for (int i = 0; i < remainder; i++) {
			// Iterate through each penny of the remainder
			values.get(i).add(1);
		}
	}
}
