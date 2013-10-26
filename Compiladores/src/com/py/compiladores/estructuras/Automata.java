package com.py.compiladores.estructuras;

import com.py.compiladores.analisis.Alfabeto;

/**
 * Clase que representa la abstraccion para un Automata Finito,
 * sea Deterministico (AFD) o No deterministico (AFN).<br><br>
 * Inicialmente, un AFN es contruido a partir de una expresion
 * regular a traves de las construcciones de Thompson.<br><br>
 * Un AFD es generado a partir de un AFN a traves del algoritmo
 * de Subconjuntos.<br><br>
 * Adicionalmente, un AFD puede ser reducido (en cuanto a
 * cantidad de estados se refiere) a traves del algoritmo
 * de Minimizacion de Estados.
 * @author Cristian Aceval
 * @author Victor Franco
 */
public abstract class Automata {
   
    /**
     * Conjunto de estados del automata.
     */
    protected Conjunto<Estado> estados;
   
    /**
     * Expresion regular para este automata.
     */
    protected String exprReg;
   
    /**
     * Alfabeto para este automata.
     */
    protected Alfabeto alfabeto;
   
    /**
     * Alfabeto para este automata.
     */
    protected String LogProceso;
   
    /**
     * Constructor por defecto.
     */
    protected Automata() {
       this(null, "");
    }
   
    /**
     * Construye un <code>Automata</code> con un determinado <code>Alfabeto</code>
     * y una determinada expresion regular.
     * @param alfabeto El <code>Alfabeto</code> de este <code>Automata</code>.
     * @param exprReg La expresion regular para este <code>Automata</code>.
     */
    protected Automata(Alfabeto alfabeto, String exprReg) {
        estados = new Conjunto<Estado>();
        setAlfabeto(alfabeto);
        setExprReg(exprReg);
    }
   
    /**
     * Obtiene el Log de procesos realizados para obtener este <code>Automata</code>.
     * @return El <code>LogProceso</code> de este <code>Automata</code>.
     */    
    public String getLogProceso() {
        return LogProceso;
    }

    /**
     * Establece el Log de procesos realizados para obtener este <code>Automata</code>.
     * @param LogProceso El nuevo <code>LogProceso</code> para este <code>Automata</code>.
     */
    public void setLogProceso(String LogProceso) {
        this.LogProceso = LogProceso;
    }
   
    /**
     * Obtiene el <code>Alfabeto</code> de este <code>Automata</code>.
     * @return El <code>Alfabeto</code> de este <code>Automata</code>.
     */
    public Alfabeto getAlfabeto() {
        return alfabeto;
    }

    /**
     * Establece el <code>Alfabeto</code> de este <code>Automata</code>.
     * @param alfabeto El nuevo <code>Alfabeto</code> para este <code>Automata</code>.
     */
    public void setAlfabeto(Alfabeto alfabeto) {
        this.alfabeto = alfabeto;
    }

    /**
     * Obtiene la expresion regular para este <code>Automata</code>.
     * @return La expresion regular para este <code>Automata</code>.
     */
    public String getExprReg() {
        return exprReg;
    }

    /**
     * Establece la expresion regular para este <code>Automata</code>.
     * @param exprReg La nueva expresion regular para este <code>Automata</code>.
     */
    public void setExprReg(String exprReg) {
        this.exprReg = exprReg;
    }
   
    /**
     * Obtiene el <code>Estado</code> inicial del <code>Automata</code>.
     * @return El <code>Estado</code> inicial del <code>Automata</code>.
     */
    public Estado getEstadoInicial() {
        return estados.obtenerPrimero();
    }
   
    /**
     * Obtiene los <code>Estado</code>s finales del <code>Automata</code>.
     * @return El conjunto de <code>Estado</code>s finales del <code>Automata</code>.
     */
    public Conjunto<Estado> getEstadosFinales() {
        Conjunto<Estado> finales = new Conjunto<Estado>();
       
        for (Estado tmp : estados)
            if (tmp.getEsFinal())
                finales.agregar(tmp);
       
        return finales;
    }
   
    /**
     * Obtiene los <code>Estado</code>s no finales del <code>Automata</code>.
     * @return El conjunto de <code>Estado</code>s no finales del <code>Automata</code>.
     */
    public Conjunto<Estado> getEstadosNoFinales() {
        Conjunto<Estado> noFinales = new Conjunto<Estado>();
       
        for (Estado tmp : estados)
            if (!tmp.getEsFinal())
                noFinales.agregar(tmp);
       
        return noFinales;
    }
   
    /**
     * Agrega un <code>Estado</code> al <code>Automata</code>.
     * @param estado Nuevo <code>Estado</code> para el <code>Automata</code>.
     */
    public void agregarEstado(Estado estado) {
        estados.agregar(estado);
    }
   
    /**
     * Recupera un determinado <code>Estado</code> del <code>Automata</code>.
     * @param pos El identificador del <code>Estado</code> a recuperar.
     * @return El <code>Estado</code> recuperado.
     */
    public Estado getEstado(int pos) {
        return estados.obtener(pos);
    }
   
    /**
     * Recupera el conjunto de <code>Estado</code>s del <code>Automata</code>.
     * @return El conjunto de <code>Estado</code>s el <code>Automata</code>.
     */
    public Conjunto<Estado> getEstados() {
        return estados;
    }
   
    /**
     * Recupera la cantidad de estados del <code>Automata</code>.
     * @return Cantidad de estados del <code>Automata</code>.
     */
    public int cantidadEstados() {
        return estados.cantidad();
    }
   
    /**
     * Establece a <code>false</code> el estado de visitado de todos los
     * <code>Estado</code>s de este <code>Automata</code>. Útil para
     * iniciar un recorrido nuevo sobre los <code>Estado</code>s de este
     * <code>Automata</code>.
     */
    public void iniciarRecorrido() {
        for (Estado tmp : estados)
            tmp.setVisitado(false);
    }
   
    /**
     * Retorna la tabla de transicion de estados.
     * @return La tabla de transicion de estados.
     */
    public abstract TablaTransicion getTablaTransicion();
   
    /**
     * Genera y carga la tabla de transicion de estados del Automata.
     * @param cantFil Cantidad de filas de la tabla.
     * @param cantCol Cantidad de columnas de la tabla.
     * @param colDesde Columna desde la cual debe rellenarse con los datos del Automata.
     * @return Tabla de transicion de estados del Automata.
     */
    @SuppressWarnings("unchecked")
	protected TablaTransicion cargarTablaTransicion(int cantFil, int cantCol, int colDesde) {
        /* Cabeceras de las columnas de la tabla de transiciones */
        String[] cabecera = new String[cantCol];
       
        /* Datos de la tabla de transiciones */
        Object[][] datos = new Object[cantFil][cantCol];
       
        /* Titulo para los Estados */
        cabecera[colDesde] = "Estados";
       
        /* Cargamos la cabecera con simbolos del Alfabeto */
        for (int i=colDesde + 1; i < cantCol; i++)
            cabecera[i] = getAlfabeto().getSimbolo(i - colDesde - 1);
       
        /* Cargamos la primera columna de datos con Estados */
        for (int i=0; i < cantFil; i++)
            datos[i][colDesde] = getEstado(i);
       
        /* Cargamos las transiciones */
        for (Estado e : getEstados()) {
            int fil = e.getIdentificador();
           
            for (Transicion t : e.getTransiciones()) {
                int col = getAlfabeto().obtenerPosicion(t.getSimbolo());
               
                if (datos[fil][col + colDesde + 1] == null)
                    datos[fil][col + colDesde + 1] = new Conjunto<Integer>();
               
                int id = t.getEstado().getIdentificador();
                ((Conjunto<Integer>) datos[fil][col + colDesde + 1]).agregar(id);
            }
        }
       
        /* Cambiamos las celdas "null" por cadenas vacias */
        String vacio = "";
        for (int i=0; i < cantFil; i++) {
            for (int j=colDesde + 1; j < cantCol; j++) {
                if (datos[i][j] == null)
                    datos[i][j] = vacio;
                else {
                    @SuppressWarnings("rawtypes")
					Conjunto c = (Conjunto) datos[i][j];
                    if (c.cantidad() == 1)
                        datos[i][j] = c.obtenerPrimero();
                }
            }
        }
       
        return new TablaTransicion(cabecera, datos);
    }
   
    @Override
    public String toString() {
        String str = "";
       
        for (Estado tmp : getEstados()) {
            str += tmp.toString();
           
            for (Transicion trans : tmp.getTransiciones())
                str += " --> " + trans.getEstado() + "{" + trans.getSimbolo() + "}";
           
            str += "\n";
        }
       
        return str;
    }
   
    /**
     * Copia los estados de un automata a otro.
     * @param afOrigen Automata desde el cual copiar estados.
     * @param afDestino Automata hacia el cual copiar estados.
     * @param incremento Cantidad en la cual deben incrementarse los identificadores
     * de los estados finales de las transiciones.
     */
    public static void copiarEstados(Automata afOrigen, Automata afDestino, int incremento) {
        copiarEstados(afOrigen, afDestino, incremento, 0);
    }
   
    /**
     * Copia los estados de un automata a otro, omitiendo una cantidad
     * determinada del automata de origen.
     * @param afOrigen Automata desde el cual copiar estados.
     * @param afDestino Automata hacia el cual copiar estados.
     * @param incrementoTrans Cantidad en la cual deben incrementarse los identificadores
     * de los estados finales de las transiciones.
     * @param omitidos Cantidad de estados de <code>origen</code> que deben ser omitidos.
     */
    public static void copiarEstados(Automata afOrigen, Automata afDestino,
                    int incrementoTrans, int omitidos) {
       
        /*
         * Cantidad que hay que incrementar al identificador
         * de un estado de afnOrigen para convertirlo en el
         * correspondiente estado de afnDestino.
         */
        int incrementoEst = incrementoTrans; // TODO:
                                             // Teoricamente, aqui deberia asignarse
                                             // afnDestino.cantidadEstados(), pero
                                             // el valor asignado actualmente tambien
                                             // funciona correctamente, por lo que se
                                             // se deja asi ya que no se pudo analizar
                                             // el impacto del cambio.
       
        /* Agregamos los nuevos estados para afnDestino */
        for (int i=omitidos; i < afOrigen.cantidadEstados(); i++)
            afDestino.agregarEstado(new Estado(afDestino.cantidadEstados()));
       
        /* Contador de omitidos */
        int contador = 0;
       
        /* Agregamos las transiciones de cada estado */
        for (Estado tmp : afOrigen.getEstados()) {
           
            if (omitidos > contador++)
                continue;
           
            /* Estado de afnDestino al cual se agregaran las transiciones */
            Estado objetivo = afDestino.getEstado(tmp.getIdentificador() + incrementoEst);
           
            /* Para cada estado, agregamos las transiciones */
            copiarTransiciones(afDestino, tmp.getTransiciones(), objetivo, incrementoTrans);
        }
    }
   
    /**
     * Dado un <code>Conjunto</code> de <code>Transicion</code>es, las aplica a un
     * <code>Estado</code> objetivo de un <code>Automata</code> destino. El incremento
     * es utilizado para seleccionar el <code>Estado</code> adecuado del <code>Automata</code>
     * destino.
     * @param afDestino El <code>Automata</code> al cual agregar <code>Transicion</code>es.
     * @param transiciones Las <code>Transicion</code>es que deben ser copiadas.
     * @param objetivo El <code>Estado</code> al cual deben ser agregadas las <code>Transicion</code>es.
     * @param incrementoTrans El incremento, respecto al <code>Estado</code> de una <code>Transicion</code>
     * dada, que debe ser aplicado para seleccionar adecuadamente el <code>Estado</code> del
     * <code>Automata</code> destino.
     */
    public static void copiarTransiciones(Automata afDestino, Conjunto<Transicion> transiciones,
                        Estado objetivo, int incrementoTrans) {
       
        for (Transicion trans : transiciones) {
            int idDestino = trans.getEstado().getIdentificador();
            String simbolo = trans.getSimbolo();

            Estado estadoDestino = afDestino.getEstado(idDestino + incrementoTrans);
            Transicion nuevaTrans = new Transicion(estadoDestino, simbolo);

            objetivo.getTransiciones().agregar(nuevaTrans);
        }
    }
}
