package me.kerdo.shootr.utils.asymmetric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class GenerateKeys {
	private KeyPairGenerator keyGen;
	private KeyPair keyPair;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	
	public GenerateKeys(int length) throws NoSuchAlgorithmException {
		keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(length);
	}
	
	public void createKeys() {
		keyPair = keyGen.generateKeyPair();
		privateKey = keyPair.getPrivate();
		publicKey = keyPair.getPublic();
	}
	
	public void writeToFile(String path, byte[] key) throws IOException {
		File f = new File(path);
		f.getParentFile().mkdirs();

		FileOutputStream fos = new FileOutputStream(f);
		fos.write(key);
		fos.flush();
		fos.close();
	}	
	
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}
}