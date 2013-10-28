package com.py.compiladores.analisis;

import com.py.compiladores.algoritmos.Thompson;
import com.py.compiladores.analisis.Alfabeto;
import com.py.compiladores.analisis.AnalizadorLexico;
import com.py.compiladores.analisis.Token;
import com.py.compiladores.analisis.TokenExprReg;
import com.py.compiladores.estructuras.AFN;
import com.py.compiladores.estructuras.Log;

/**
 * Clase que implementa un analizador sintactico predictivo
 * para una expresion regular, realizando una traduccion de
 * la misma a su correspondiente AFN.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class AnalizadorSintactico {
   
    /**
     * Log del analisis sintactico.
     */
    private Log log;
   
    /**
     * El analizador lexico para este analizador
     * sintactico. Se hara uso del mismo para
     * obtener tokens.
     */
    private AnalizadorLexico analizadorLexico;
   
    /**
     * Variable para el token actual.
     */
    private Token preanalisis;
   
    /**
     * Contador de tokens recibidos. Util para
     * indicar donde ocurren los errores.
     */
    private int contadorTokens;
   
    /**
     * Constructor de la clase.
     * @param alfabeto El alfabeto sobre el cual esta definido <code>exprReg</code>.
     * @param exprReg La expresion regular a evaluar.
     */
    public AnalizadorSintactico(Alfabeto alfabeto, String exprReg) {
        analizadorLexico = new AnalizadorLexico(alfabeto, exprReg);
        contadorTokens = 0;
        log = new Log();
    }

    /**
     * Inicia el analisis sintactico (traduccion).
     * @return Un AFN que representa a la expresion regular de entrada.
     * @throws java.lang.Exception En caso de encontrar algun error
     * de sintaxis en la expresion regular de entrada.
     */
    public AFN analizar() throws Exception {
        preanalisis = obtenerToken();
       
        if (preanalisis.getIdentificador() == TokenExprReg.FINAL)
            error("Expresion regular vacia");
       
        // Logging
        log.vaciar();
        //log.agregar("Derivaciones realizadas por el parser".toUpperCase()).nuevaLinea();
        log.agregar("-------------------------------------").nuevaLinea().nuevaLinea();
       
        AFN afn = ExprReg();
        afn.setAlfabeto(analizadorLexico.getAlfabeto());
        afn.setExprReg(analizadorLexico.getExpresionRegular());
       
        if (preanalisis.getIdentificador() != TokenExprReg.FINAL)
            error("Caracter invalido");
       
        return afn;
    }
   
    /**
     * Metodo que procesa las uniones de la expresion regular.
     * @throws java.lang.Exception Propaga la excepcion de Concat() y R1().
     */
    private AFN ExprReg() throws Exception {
        log.agregar("ExprecionRegular -> Concatenar R1").nuevaLinea();
       
        AFN afn1 = Concat();
        AFN afn2 = R1();
       
        if (afn2 == null)
            return afn1;
        else
            return Thompson.union(afn1, afn2);
    }
   
    /**
     * Metodo que procesa las uniones en forma de lista.
     * @throws java.lang.Exception Propaga la excepcion de ExprReg().
     */
    private AFN R1() throws Exception {
        if (preanalisis.getIdentificador() == TokenExprReg.UNION) {
            match(preanalisis);
           
            log.agregar("R1 -> \"|\" Concatenar R1").nuevaLinea();
            AFN afn1 = Concat();
            AFN afn2 = R1();

            if (afn2 == null)
                return afn1;
            else
                return Thompson.union(afn1, afn2);
        }
        else {
            // Derivar en vacio
            log.agregar("R1 -> " + Alfabeto.VACIO).nuevaLinea();
            return null;
        }
    }

    /**
     * Metodo que procesa una concatenacion en la expresion regular.
     * @throws java.lang.Exception Propaga la excepcion de Grupo() y R2().
     */
    private AFN Concat() throws Exception {
        log.agregar("Concatenar -> Grupo R2").nuevaLinea();
       
        AFN afn1 = Grupo();
        AFN afn2 = R2();
       
        if (afn2 == null)
            return afn1;
        else
            return Thompson.concatenacion(afn1, afn2);
    }
   
    /**
     * Metodo que procesa una concatenacion en forma de lista.
     * @throws java.lang.Exception Propaga la excepcion de Concat().
     */
    private AFN R2() throws Exception {
        switch (preanalisis.getIdentificador()) {
            case PAREN_IZQUIERDO:
            case ALFABETO:
               
                log.agregar("R2 -> Grupo R2").nuevaLinea();
                AFN afn1 = Grupo();
                AFN afn2 = R2();

                if (afn2 == null)
                    return afn1;
                else
                    return Thompson.concatenacion(afn1, afn2);
            default:
                // Derivar en vacio
                log.agregar("R2 -> " + Alfabeto.VACIO).nuevaLinea();
                return null;
        }
    }
   
    /**
     * Metodo que procesa un elemento unitario, posiblemente aplicando un
     * operador unario.
     * @throws java.lang.Exception Propaga las excepciones de Elem() y Oper().
     */
    private AFN Grupo() throws Exception {
        log.agregar("Grupo -> Elemento Operador").nuevaLinea();
       
        AFN afn = Elem();
        TokenExprReg operador = Oper();
       
        switch (operador) {
            case CERRADURA_KLEENE:
                return Thompson.cerraduraKleene(afn);
            case CERRADURA_POSITIVA:
                return Thompson.cerraduraPositiva(afn);
            case OPCION:
                return Thompson.opcion(afn);
            default:
                return afn;
        }
    }
   
    /**
     * Metodo que procesa un operador en la expresion regular.
     * @throws java.lang.Exception Propaga la excepcion de match().
     */
    private TokenExprReg Oper() throws Exception {
        TokenExprReg operador;
       
        switch (preanalisis.getIdentificador()) {
            case CERRADURA_KLEENE:
            case CERRADURA_POSITIVA:
            case OPCION:
                operador = preanalisis.getIdentificador();
               
                log.agregar("Operador -> " + preanalisis.getValor()).nuevaLinea();
                match(preanalisis);
                break;
            default:
                // Derivar en vacio
                log.agregar("Operador -> " + Alfabeto.VACIO).nuevaLinea();
                operador = TokenExprReg.DESCONOCIDO;
        }
       
        return operador;
    }
 
    /**
     * Metodo que procesa un elemento unitario en la expresion
     * regular. Se intenta hacer match con el parentesis de
     * apertura o con algún simbolo del alfabeto. En caso
     * contrario, se produce un error.
     * @throws java.lang.Exception En caso de que no se encuentre un simbolo
     * del alfabeto ni un parentesis de apertura (inicio de una nueva expresion
     * regular).
     */
    private AFN Elem() throws Exception {
        AFN afn = null;
       
        switch (preanalisis.getIdentificador()) {
            case PAREN_IZQUIERDO:
                log.agregar("Elemento -> \"(\" ExprecionRegular \")\"").nuevaLinea();
               
                match(new Token(TokenExprReg.PAREN_IZQUIERDO));
                afn = ExprReg();
                match(new Token(TokenExprReg.PAREN_DERECHO));
                break;
            case ALFABETO:
                log.agregar("Elemento -> SimpleElemento").nuevaLinea();
               
                afn = SimLen();
                break;
            default:
                error("Se espera parentesis de apertura o simbolo de alfabeto. " +
                    "Se encontro \"" + preanalisis.getValor() + "\"");
        }
       
        return afn;
    }
   
    /**
     * Metodo que procesa un simbolo del alfabeto en la expresion regular.
     * @throws java.lang.Exception Si el caracter actual no es un simbolo del alfabeto.
     */
    private AFN SimLen() throws Exception {
        String simbolo = preanalisis.getValor();
       
        if (!analizadorLexico.getAlfabeto().contiene(simbolo)) {
            error("El simbolo \"" + simbolo +
                "\" no pertenece al alfabeto definido.");
        }
       
        log.agregar("SimpleElemento -> " + simbolo).nuevaLinea();
       
        AFN afn = Thompson.basico(simbolo);
        match(preanalisis);
        return afn;
    }

    /**
     * Metodo que se encarga de corroborar que la
     * entrada es la correcta para consumir el siguiente
     * token.
     * @param entrada Token esperado, debe ser igual al token actual.
     * @throws java.lang.Exception En caso de que el token actual no
     * sea igual al esperado.
     */
    private void match(Token entrada) throws Exception {
        if (preanalisis.equals(entrada))
            preanalisis = obtenerToken();
        else if (entrada.getIdentificador() == TokenExprReg.PAREN_DERECHO)
            error("Falta parentesis de cierre");
        else
            error("Caracter invalido");
    }
   
    /**
     * Metodo que se encarga de lanzar excepciones
     * para los distintos casos de error posibles.
     * @param mensaje El mensaje de error.
     * @throws java.lang.Exception Siempre se lanza una excepcion,
     * producto del error ocurrido.
     */
    private void error(String mensaje) throws Exception {
        String mensajeCompleto = "";
       
        mensajeCompleto += "Error de sintaxis\n";
        mensajeCompleto += "Caracter: " + preanalisis.getValor() + "\n";
        mensajeCompleto += "Posicion: " + contadorTokens + "\n";
        mensajeCompleto += "Mensaje : " + mensaje;
       
        throw new Exception(mensajeCompleto);
    }
   
    /**
     * Metodo que obtiene el siguiente token y registra
     * la cantidad de tokens leidos.
     * @return El siguiente token del Analizador Lexico.
     * @throws java.lang.Exception
     */
    private Token obtenerToken() throws Exception {
        ++contadorTokens;
        return analizadorLexico.sgteToken();
    }
   
    /**
     * Obtiene el <code>Log</code> de esta clase.
     * @return El <code>Log</code> correspondiente
     * al proceso de analisis sintactico.
     */
    public Log getLog() {
        return log;
    }
}
