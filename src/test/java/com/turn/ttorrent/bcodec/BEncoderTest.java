package com.turn.ttorrent.bcodec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BEncoderTest {
	private static final Logger LOG = LoggerFactory.getLogger(BEncoderTest.class);
	
	@BeforeClass public static void onlyOnce() {
		BasicConfigurator.configure();
	}

	@Test
	public void testBencodeBytes() throws IOException {
		byte[] bs = new byte[] {1, 2, 3, 4, 5};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BEncoder.bencode(bs, out);
		byte[] outBs = out.toByteArray();
		LOG.info("out = {}", outBs);  // ['5', ':', 1, 2, 3, 4, 5]
		Assert.assertArrayEquals(new byte[] {53, 58, 1, 2, 3, 4, 5}, outBs);
	}

	private void expectBencode(Object o, byte[] expecteds) throws IllegalArgumentException, IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BEncoder.bencode(o, out);
		byte[] actuals = out.toByteArray();
		LOG.info("out = {}", actuals);
		Assert.assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testBencodeNumber() throws IOException {
		// ['i', '1', '2', '3', 'e']
		expectBencode(new Integer(123), new byte[]{105, 49, 50, 51, 101});
		expectBencode(new Double(1.23), new byte[]{105, 49, 46, 50, 51, 101});
		expectBencode("i123e", new byte[]{53, 58, 105, 49, 50, 51, 101});
	}

	@Test
	public void testBencodeString() throws IOException {
		expectBencode("i123e", new byte[]{53, 58, 105, 49, 50, 51, 101});  // "5:i123e"
	}
}
