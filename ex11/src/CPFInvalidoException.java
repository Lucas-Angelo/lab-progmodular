public class CPFInvalidoException extends Exception {
    public CPFInvalidoException(String CPF) {
        super("O CPF " + CPF + " é inválido!");
    }
}
