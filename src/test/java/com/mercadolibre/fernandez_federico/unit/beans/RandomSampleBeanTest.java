package com.mercadolibre.fernandez_federico.unit.beans;

import com.mercadolibre.fernandez_federico.dtos.SampleDTO;
import com.mercadolibre.fernandez_federico.beans.RandomSampleBean;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSampleBeanTest {

	@Test
	public void randomPositiveTestOK() {
		RandomSampleBean randomSample = new RandomSampleBean();

		SampleDTO sample = randomSample.random();
		
		assertTrue(sample.getRandom() >= 0);
	}
}
