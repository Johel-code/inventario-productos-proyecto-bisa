package com.proyecto.servicio_producto.commons.utils;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import java.security.MessageDigest;

public class GeneradorCodigoProducto {

    public static String generateCodigo(String nombreProducto) {
        String prefijo = obtenerPrefijo(nombreProducto);

        String hashCorto = generarHashCorto(nombreProducto);

        return prefijo + "-" + hashCorto;
    }

    private static String generarHashCorto(String nombreProducto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] hash = digest.digest(nombreProducto.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.substring(0, 4).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash", e);
        }
    }

    private static String obtenerPrefijo(String nombreProducto) {
        StringBuilder prefijo = new StringBuilder();
        String[] palabras = nombreProducto.split(" ");
        if(palabras.length == 1) {
            prefijo = new StringBuilder(palabras[0].substring(0, Math.min(palabras[0].length(), 3)));
        }else{
            for (int i = 0; i < palabras.length && i < 3; i++) {
                prefijo.append(palabras[i].charAt(0));
            }
            if(palabras.length < 3) {
                prefijo = new StringBuilder(prefijo.append('X'));
            }
        }
        return prefijo.toString().toUpperCase();
    }
}
