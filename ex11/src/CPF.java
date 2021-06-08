public class CPF {
    public boolean validarCPF(String cpf) {
        boolean valido = true;
        if (cpf.length() != 11)
            valido = false;
        return valido;
    }
}