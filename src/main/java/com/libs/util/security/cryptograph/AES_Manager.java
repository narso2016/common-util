package com.libs.util.security.cryptograph;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.engines.AESEngine;
public class AES_Manager {

	public byte[] encryptAES256(byte[] input, KeyParameter key)
			throws InvalidCipherTextException {
		CipherParameters cipherParameters = null;
		BufferedBlockCipher bufferedBlockCipher = null;
		// if (key.length == 32) // 32 bytes == 256 bits
		// {
		cipherParameters = key;

		BlockCipher blockCipher = new AESEngine();

		BlockCipherPadding blockCipherPadding = new ZeroBytePadding();

		bufferedBlockCipher = new PaddedBufferedBlockCipher(blockCipher,
				blockCipherPadding);
		// }
		return encrypt(input, bufferedBlockCipher, cipherParameters);
	}

	public byte[] encryptAES256(byte[] input, byte[] key)
			throws InvalidCipherTextException {
		CipherParameters cipherParameters = null;
		BufferedBlockCipher bufferedBlockCipher = null;
		// if (key.length == 32) // 32 bytes == 256 bits
		// {
		cipherParameters = new KeyParameter(key);

		BlockCipher blockCipher = new AESEngine();

		BlockCipherPadding blockCipherPadding = new ZeroBytePadding();

		bufferedBlockCipher = new PaddedBufferedBlockCipher(blockCipher,
				blockCipherPadding);
		// }
		return encrypt(input, bufferedBlockCipher, cipherParameters);
	}

	public byte[] DecryptAES256(byte[] input, byte[] key)
			throws InvalidCipherTextException {
		CipherParameters cipherParameters = null;
		BufferedBlockCipher bufferedBlockCipher = null;
		if (key.length == 32) // 32 bytes == 256 bits
		{
			cipherParameters = new KeyParameter(key);

			BlockCipher blockCipher = new AESEngine();

			BlockCipherPadding blockCipherPadding = new ZeroBytePadding();

			bufferedBlockCipher = new PaddedBufferedBlockCipher(blockCipher,
					blockCipherPadding);
		}
		return decrypt(input, bufferedBlockCipher, cipherParameters);
	}

	public byte[] DecryptAES256(byte[] input, KeyParameter key)
			throws InvalidCipherTextException {
		CipherParameters cipherParameters = null;
		BufferedBlockCipher bufferedBlockCipher = null;

		cipherParameters = key;

		BlockCipher blockCipher = new AESEngine();

		BlockCipherPadding blockCipherPadding = new ZeroBytePadding();

		bufferedBlockCipher = new PaddedBufferedBlockCipher(blockCipher,
				blockCipherPadding);

		return decrypt(input, bufferedBlockCipher, cipherParameters);
	}

	public byte[] encrypt(byte[] input,
			BufferedBlockCipher bufferedBlockCipher,
			CipherParameters cipherParameters)
			throws InvalidCipherTextException {
		boolean forEncryption = true;
		return process(input, bufferedBlockCipher, cipherParameters,
				forEncryption);
	}

	public byte[] decrypt(byte[] input,
			BufferedBlockCipher bufferedBlockCipher,
			CipherParameters cipherParameters)
			throws InvalidCipherTextException {
		boolean forEncryption = false;
		return process(input, bufferedBlockCipher, cipherParameters,
				forEncryption);
	}

	public byte[] process(byte[] input,
			BufferedBlockCipher bufferedBlockCipher,
			CipherParameters cipherParameters, boolean forEncryption)
			throws InvalidCipherTextException {
		bufferedBlockCipher.init(forEncryption, cipherParameters);
//		int a = bufferedBlockCipher.getBlockSize();
		int inputOffset = 0;
		int inputLength = input.length;

		int maximumOutputLength = bufferedBlockCipher
				.getOutputSize(inputLength);
		byte[] output = new byte[maximumOutputLength];
		int outputOffset = 0;
		int outputLength = 0;

		int bytesProcessed;

		bytesProcessed = bufferedBlockCipher.processBytes(input, inputOffset,
				inputLength, output, outputOffset);
		outputOffset += bytesProcessed;
		outputLength += bytesProcessed;

		bytesProcessed = bufferedBlockCipher.doFinal(output, outputOffset);
		outputOffset += bytesProcessed;
		outputLength += bytesProcessed;

		if (outputLength == output.length) {
			return output;
		} else {
			byte[] truncatedOutput = new byte[outputLength];
			System.arraycopy(output, 0, truncatedOutput, 0, outputLength);
			return truncatedOutput;
		}
	}
}