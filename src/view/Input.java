package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Input {

    public static EntradaObj ler() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return decodificador(input);
    }

    private static EntradaObj decodificador(String input) {
        String[] partes = input.split(" ");
        String comando = partes[0];
        List<String> parametros = Arrays.stream(partes)
                .skip(1)
                .collect(Collectors.toList());
        return new EntradaObj(comando, parametros);
    }

}

