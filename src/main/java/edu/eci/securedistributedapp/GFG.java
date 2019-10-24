package edu.eci.securedistributedapp;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// Algoritmo de SHA que genera código hash 
public class GFG {
    /**
     * Genera código hash con algoritmo SHA
     * @param input Cadena a convertir
     * @return Arreglo de buytes con código hash
     * @throws NoSuchAlgorithmException Excepcion al usar el algoritmo
     */
    public byte[] getSHA(String input) throws NoSuchAlgorithmException {
        //Se llama al método getInstance estático con hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //método digest() llamado
        //para calcular el resumen del mensaje de una entrada
        //y devuelve una matriz de bytes
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * toHexString Convierte un array de bytes a hexadecimal
     * @param hash Arreglo de bytes a convertir
     * @return Cadena con la conversión en hexadecimal
     */
    public String toHexString(byte[] hash) {
        // Convert byte array into signum representation  
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value  
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros 
        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     * convert Convierte una cadena en un código hash
     * @param password Cadena a  convertir
     * @return Cadena con código hash
     */
    public String convert(String password) {
        String passwordHash = "";
        try {
            System.out.println("Código Hash Generado por SHA-256 para ");
            passwordHash = toHexString(getSHA(passwordHash));
            System.out.println("\n" + passwordHash + " : " + passwordHash);

        } // For specifying wrong message digest algorithms  
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception thrown for incorrect algorithm: " + e);
        }
        return passwordHash;
    }
}
