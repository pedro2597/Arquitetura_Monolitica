package controller;

import java.util.List;

public interface Command {
    String execute(List<String> parametros);
}
