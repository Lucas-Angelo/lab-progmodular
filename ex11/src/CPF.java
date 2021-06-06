public class CPF {
    public boolean validarCPF(String cpf) {
        boolean valido = true;
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");
        if(cpf.length()==11) {
            try {
                Long.parseLong(cpf);
            } catch (NumberFormatException e) {
                System.out.println("aq");
                valido = false;
            }
        } else {
            valido = false;
        }
        return valido;
    }
}