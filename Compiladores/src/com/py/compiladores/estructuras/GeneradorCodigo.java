package com.py.compiladores.estructuras;


import java.io.FileWriter;
import java.io.PrintWriter;


/**
 *
 * @author Cristian Aceval
 * @author Victor Franco
 */
public class GeneradorCodigo {
    private String pInicial;
    private String pMedia1;
    private String pMedia2;
    private String pFinal;

    public GeneradorCodigo(AFD afd1) {
        this.pInicial = GeneradorCodigo.inicializarPInicial();
        this.pMedia1 = GeneradorCodigo.inicializarM1(afd1);
        this.pMedia2 = GeneradorCodigo.inicializarM2(afd1);
        this.pFinal = GeneradorCodigo. inicializarPFinal();
    }

    public String getpInicial() {
        return pInicial;
    }

    public void setpInicial(String pInicial) {
        this.pInicial = pInicial;
    }
    public String getpMedia1() {
        return pMedia1;
    }

    public void setpMedia1(String pMedia1) {
        this.pMedia1 = pMedia1;
    }

    public String getpMedia2() {
        return pMedia2;
    }

    public void setpMedia2(String pMedia2) {
        this.pMedia2 = pMedia2;
    }

    public String getpFinal() {
        return pFinal;
    }

    public void setpFinal(String pFinal) {
        this.pFinal = pFinal;
    }
    
    private static  String inicializarPInicial(){
        return 
                "import java.io.BufferedReader;\n" +
                "import java.io.IOException;\n" +
                "import java.io.InputStreamReader;\n" +
                "\n" +
                "/**\n" +
                " *\n" +
                " * @author cristian,victor \n" +
                " */\n" +
                "public class CodigoGenerado {\n" +
                "\n" +
                "    /**\n" +
                "     * @param args the command line arguments\n" +
                "     */\n" +
                "    public static void main(String[] args) throws IOException {\n" +
                "        // TODO code application logic here\n" +
                "        System.out.println(\"Ingrese el valor a ser evaluado\");"+
                "        StringBuffer bufferEntrada = new StringBuffer(new BufferedReader(new InputStreamReader(System.in)).readLine());\n" +
                "        String entradaIni = bufferEntrada.toString();\n" +
                "        int estadoInicial = 0;\n" +
                "        StringBuffer entradaActual = bufferEntrada;\n" +
                "        int estadoActual = estadoInicial;\n";
    }

    
    private static String inicializarM1(AFD afd1){
        
        String al = "        String alfabeto = \""+ afd1.getAlfabeto().getSimbolo(0)+"\";\n";
        al += "        String erpRegular = \""+ afd1.getExprReg()+"\";\n";
        al += "        int cantidadEstado = "+afd1.getEstados().cantidad()+";\n";
        al += "        int [] eFinales = {";
        int auxi = 0;
        for (Estado e :afd1.getEstados()){
            if (e.getEsFinal()){
                if(auxi != 0) al+= "," + e.getIdentificador();
                else{
                    al+= e.getIdentificador();
                    auxi = 1;
                }
            }
        }
        //al+= al.substring(0, al.length()-1);
        al +=  "};\n";
        return al+="        if(bufferEntrada.length()>0){\n" +
                    "            while(true){\n";
    }
    private static String inicializarM2(AFD afd1){
                String gcode = "";
                            gcode +="                switch(estadoActual){\n";
        for(Estado e : afd1.getEstados()){
                            gcode += "                    case "+ e.getIdentificador()+":\n";
                            gcode += "                        switch(entradaActual.charAt(0)){\n";
                    for(Transicion t: e.getTransiciones()){
                            gcode += "                            case '"+ t.getSimbolo()+"' :\n";
                            gcode += "                                entradaActual.deleteCharAt(0);\n";
                            gcode += "                                estadoActual = " + t.getEstado().getIdentificador() + ";\n";
                            gcode += "                                break;\n";
                    }
                            gcode += "                        default:\n" +
                                     "                                estadoActual = cantidadEstado +1;\n" +
                                     "                                break;" +
                                     "                        \n" +
                                     "                        }\n";
                        
                            gcode += "                      break;\n\n";
        }
        
        gcode += "                    default:\n" +
"                        break;   \n" +
"                }\n";
        return gcode;
    }
    private static String inicializarPFinal(){
        return "                if(estadoActual>cantidadEstado || entradaActual.length() == 0){\n" +
                "                    int aux = 0;\n" +
                "    //                System.out.println(estadoActual);\n" +
                "                    for(int i = 0; i< eFinales.length; i++ )\n" +
                "                        if(eFinales[i] == estadoActual) aux = 1;\n" +
                "\n" +
                "                    if(aux == 1)System.out.println(\"El Lenguaje: L( \"+erpRegular+\" )\"+\n" +
                "                                                    \" \\nEl Alfabeto:\" +alfabeto+\n" +
                "                                                    \"\\nACEPTA LA CADENA DE ENTRADA: \"+entradaIni);\n" +
                "                    else  System.out.println(\"El Lenguaje: L\\\"( \"+erpRegular+\" )\"+\n" +
                "                                                    \" \\nEl Alfabeto:\" +alfabeto+\n" +
                "                                                    \"\\nNO ACEPTA LA CADENA DE ENTRADA: \"+entradaIni+\n" +
                "                                                    \"\\nEntrada no consumida: \"+entradaActual.toString());\n" +
                "\n" +
                "                    break;\n" +
                "                }\n" +
                "\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "}";
    }
    
    public String generaCodigo(){
    	FileWriter fichero = null;
		PrintWriter pw = null;

		try {
			fichero = new FileWriter("C:\\workspace\\Prueba\\src\\CodigoGenerado.java");
			pw = new PrintWriter(fichero);

			pw.println(getpInicial()+getpMedia1()+getpMedia2()+getpFinal());
		

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero)
					fichero.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return (getpInicial()+getpMedia1()+getpMedia2()+getpFinal());
    }
}
