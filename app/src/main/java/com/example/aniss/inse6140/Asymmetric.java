package com.example.aniss.inse6140;

/**
 * Created by aniss on 04/03/16.
 */


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
public class Asymmetric {


    /**
     * Generate key pair.
     *
     * @return the key pair
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    public KeyPair generateKeyPair() throws NoSuchAlgorithmException {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

        keyPairGenerator.initialize(4096);

        KeyPair keyPair = keyPairGenerator.genKeyPair();


        return keyPair;



    }


    /**
     * Store key pair to files.
     *
     * @param keyPair the key pair
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException the invalid key spec exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void storeKeyPairToFiles(KeyPair keyPair) throws NoSuchAlgorithmException, InvalidKeySpecException,
            IOException {

        Key publicKey = keyPair.getPublic();
        Key privateKey = keyPair.getPrivate();

        KeyFactory factory = KeyFactory.getInstance("RSA");

        RSAPublicKeySpec rsaPublicKeySpec = factory.getKeySpec(publicKey, RSAPublicKeySpec.class);
        RSAPrivateKeySpec rsaPrivateKeySpec = factory.getKeySpec(privateKey, RSAPrivateKeySpec.class);

        saveToFile("public.key", rsaPublicKeySpec.getModulus(), rsaPublicKeySpec.getPublicExponent());
        saveToFile("private.key", rsaPrivateKeySpec.getModulus(), rsaPrivateKeySpec.getPrivateExponent());


    }

    /**
     * Save to file.
     *
     * @param filename the filename
     * @param modulus the modulus
     * @param publicExponent the public exponent
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void saveToFile(String filename, BigInteger modulus, BigInteger publicExponent) throws IOException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                filename)));



        try{
            objectOutputStream.writeObject(modulus);
            objectOutputStream.writeObject(publicExponent);


        }catch(Exception e){
            throw new IOException("Unexpected error", e);

        }finally{
            objectOutputStream.close();
        }

    }


    /**
     * Read public key from file.
     *
     * @param filename the filename
     * @return the public key
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException the invalid key spec exception
     */
    public PublicKey readPublicKeyFromFile(String filename) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException{
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));
        BigInteger m = (BigInteger) objectInputStream.readObject();
        BigInteger e = (BigInteger) objectInputStream.readObject();
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = factory.generatePublic(keySpec);

        return publicKey;

    }


    /**
     * Read private key from file.
     *
     * @param filename the filename
     * @return the private key
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws ClassNotFoundException the class not found exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException the invalid key spec exception
     */
    public PrivateKey readPrivateKeyFromFile(String filename) throws IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException{
        FileInputStream fileInputStream = new FileInputStream(filename);
        ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(fileInputStream));
        BigInteger m = (BigInteger) objectInputStream.readObject();
        BigInteger e = (BigInteger) objectInputStream.readObject();
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(keySpec);

        return privateKey;


    }



    /**
     * Rsa encrypt key.
     *
     * @param data the data
     * @return the byte[]
     * @throws ClassNotFoundException the class not found exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException the invalid key spec exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws NoSuchPaddingException the no such padding exception
     * @throws InvalidKeyException the invalid key exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException the bad padding exception
     */
    public byte[] rsaEncryptKey(byte[] data) throws ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        PublicKey publicKey = readPublicKeyFromFile("public.key");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] cipherData = cipher.doFinal(data);
        return cipherData;


    }



    /**
     * Rsa decrypt key.
     *
     * @param cipherData the cipher data
     * @return the byte[]
     * @throws ClassNotFoundException the class not found exception
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws InvalidKeySpecException the invalid key spec exception
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws NoSuchPaddingException the no such padding exception
     * @throws InvalidKeyException the invalid key exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException the bad padding exception
     */
    public byte[] rsaDecryptKey(byte[] cipherData) throws ClassNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        PrivateKey privateKey = readPrivateKeyFromFile("private.key");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plaintext = cipher.doFinal(cipherData);
        return plaintext;

    }




    /**
     * Generate signature.
     *
     * @param input the input
     * @param privateKey the private key
     * @return the byte[]
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IOException
     * @throws SignatureException
     */
    public byte[] generateSignature(File input, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Signature signature = Signature.getInstance("SHA512withRSA");
        signature.initSign(privateKey);
        byte[] buffer = new byte[1024];
        InputStream is = new FileInputStream(input);
        int n;

        while((n = is.read(buffer)) >= 0){
            signature.update(buffer, 0, n);
        }
        is.close();

        return signature.sign();
    }


    public boolean validateSignature(byte[] sig, File input, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, IOException, SignatureException{

        Signature signature = Signature.getInstance("SHA512withRSA");
        signature.initVerify(publicKey);

        byte[] buffer = new byte[1024];
        InputStream is = new FileInputStream(input);
        int n;

        while((n = is.read(buffer)) >= 0){

            signature.update(buffer, 0, n);



        }

        is.close();

        return signature.verify(sig);


    }




}
