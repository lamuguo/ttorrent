package com.turn.ttorrent.bcodec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BEncoderTest {
	private static final Logger LOG = LoggerFactory.getLogger(BEncoderTest.class);

	@Test
	public void testBencodeBytes() throws IOException {
		byte[] bs = new byte[] {1, 2, 3, 4, 5};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		BEncoder.bencode(bs, out);
		byte[] outBs = out.toByteArray();
		LOG.info("out = {}", outBs);
		Assert.assertArrayEquals(new byte[] {53, 58, 1, 2, 3, 4, 5}, outBs);
	}
}
