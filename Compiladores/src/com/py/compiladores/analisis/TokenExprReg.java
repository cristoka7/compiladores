package com.py.compiladores.analisis;



public enum TokenExprReg {
    /**
     * Paréntesis derecho, ")".
     */
    PAREN_DERECHO,
    
    /**
     * Paréntesis izquierdo, "(".
     */
    PAREN_IZQUIERDO,
    
    /**
     * Operador de unión, "|".
     */
    UNION,
    
    /**
     * Operador de cerradura de Kleene, "*".
     */
    CERRADURA_KLEENE,
    
    /**
     * Operador de cerradura positiva, "+".
     */
    CERRADURA_POSITIVA,
    
    /**
     * Operador de opción, "?".
     */
    OPCION,
    
    /**
     * Operador de concatenación (no tiene dibujo).
     */
    CONCATENACION,
    
    /**
     * Un símbolo del alfabeto.
     */
    ALFABETO,
    
    /**
     * Finalizador de una expresión regular (EOF).
     */
    FINAL,
    
    /**
     * Token desconocido (inválido).
     */
    DESCONOCIDO
}
