public class GeneradorCodigoFacade {
    private GeneradorCodigo generador;

    public GeneradorCodigoFacade(AFD afd1) {
        this.generador = GeneradorCodigo.crearGeneradorCodigo(afd1);
    }

    public String generarCodigo() {
        return generador.generaCodigo();
    }
}
