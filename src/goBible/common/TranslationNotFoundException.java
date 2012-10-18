package goBible.common;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Typed exception for detecting missing translation folder
 * @author NIM
 */
public class TranslationNotFoundException extends Exception {
    public TranslationNotFoundException(String message) {
        super(message);
    }
}
