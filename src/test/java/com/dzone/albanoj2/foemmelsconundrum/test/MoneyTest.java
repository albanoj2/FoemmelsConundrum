package com.dzone.albanoj2.foemmelsconundrum.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.dzone.albanoj2.foemmelsconundrum.Money;
import com.dzone.albanoj2.foemmelsconundrum.SequentialDistributor;

public class MoneyTest {

	@Test
	public void testFoemmelsConundrumEnsureCorrectResult () {
		this.testAllocateResults(5, MoneyTest.createRatioList(30, 70), MoneyTest.createMoneyList(2l, 3l));
	}

	@Test
	public void testReverseFoemmelsConundrumEnsureCorrectResult () {
		this.testAllocateResults(5, MoneyTest.createRatioList(70, 30), MoneyTest.createMoneyList(4l, 1l));
	}
	
	@Test
	public void testAccountScenarioEnsureCorrectResult () {
		this.testAllocateResults(102759, 
			MoneyTest.createRatioList(20, 70, 5, 5), 
			MoneyTest.createMoneyList(20552l, 71932l, 5138l, 5137l)
		);
	}
	
	@Test
	public void testProvideMoreEvenlyDistributedNonZeroRatiosThanTotalCentsEnsureCorrectResults () {
		this.testAllocateResults(5, 
			MoneyTest.createRatioList(1, 1, 1, 1, 1, 1), 
			MoneyTest.createMoneyList(1l, 1l, 1l, 1l, 1l, 0l)
		);
	}
	
	@Test
	public void testProvideMoreNonEvenlyDistributedNonZeroRatiosThanTotalCentsEnsureCorrectResults () {
		this.testAllocateResults(5, MoneyTest.createRatioList(2, 4), MoneyTest.createMoneyList(2l, 3l));
	}
	
	@Test
	public void testProvideReverseMoreNonEvenlyDistributedNonZeroRatiosThanTotalCentsEnsureCorrectResults () {
		this.testAllocateResults(5, MoneyTest.createRatioList(4, 2), MoneyTest.createMoneyList(4l, 1l));
	}
	
	@Test
	public void testProvideZeroRatioValueEnsureCorrectResults () {
		this.testAllocateResults(5, 
			MoneyTest.createRatioList(3, 0, 7), 
			MoneyTest.createMoneyList(2l, 0l, 3l)
		);
	}
	
	@Test
	public void testProvideOnlyOneZeroRatioValueEnsureCorrectResults () {
		this.testAllocateResults(5, MoneyTest.createRatioList(0), MoneyTest.createMoneyList(0l));
	}
	
	@Test
	public void testProvideOnlyTwoZeroRatioValueEnsureCorrectResults () {
		this.testAllocateResults(5, MoneyTest.createRatioList(0, 0), MoneyTest.createMoneyList(0l, 0l));
	}
	
	@Test
	public void testProvideEmptyListOfRatiosEnsureEmptyListReturned () {
		this.testAllocateResults(5, MoneyTest.createRatioList(), MoneyTest.createMoneyList());
	}
	
	@Test
	public void testDivideZeroCentsByAnyRatiosEnsureListOfZeroMoneyReturned () {
		this.testAllocateResults(0, 
			MoneyTest.createRatioList(1, 2, 3), 
			MoneyTest.createMoneyList(0l, 0l, 0l)
		);
	}
	
	public static Money createMoney (long cents) {
		return new Money(cents, new SequentialDistributor());
	}
	
	public static List<Money> createMoneyList (Long... cents) {
		return Stream.of(cents).map(centsValue -> MoneyTest.createMoney(centsValue)).collect(Collectors.toList());
	}
	
	public static List<Integer> createRatioList (Integer... ratios) {
		return Stream.of(ratios).collect(Collectors.toList());
	}
	
	public void testAllocateResults (long totalCents, List<Integer> ratios, List<Money> expectedResults) {
		
		if (ratios.size() != expectedResults.size()) {
			// The number of ratios and the number of expected results do not match
			fail("Number of ratios and expected results do not match");
		}
		else {
			// Obtain the results from the money class
			List<Money> results = MoneyTest.createMoney(totalCents).allocate(ratios);
			
			// Ensure the results match the expected results
			assertThat(results, is(expectedResults));
		}
	}
}
