//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import view.EntradaObj;
import view.Input;
import view.Sistema;


public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("Digite um comando");
                EntradaObj entradaObj = Input.ler();
                String res = Sistema.getInstance().executar(entradaObj.getComando(), entradaObj.getParametros());
                if(res == null){
                    System.out.println("Saindo");
                    break;
                }
                System.out.println(res);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}