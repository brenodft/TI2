package service;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import model.Jogo;
import dao.JogoDAO;
import spark.Request;
import spark.Response;

public class JogoService {

    private JogoDAO jogoDAO = new JogoDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_GENERO = 2;
    private final int FORM_ORDERBY_PLATAFORMA = 3;

    public JogoService() {
        makeForm();
    }

    public void makeForm() {
        makeForm(FORM_INSERT, new Jogo(), FORM_ORDERBY_GENERO);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Jogo(), orderBy);
    }

    public void makeForm(int tipo, Jogo jogo, int orderBy) {
        String nomeArquivo = "form.html";
        form = "";
        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String umJogo = "";
        if (tipo != FORM_INSERT) {
            umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/jogo/list/1\">Novo jogo</a></b></font></td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t</table>";
            umJogo += "\t<br>";
        } // end if

        if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {

            String action = "/jogo/";
            String name, nome, genero, plataforma, buttonLabel;
            if (tipo == FORM_INSERT) {
                action += "insert";
                name = "Inserir Jogo";
                nome = "nome";
                genero = "genero";
                plataforma = "plataforma";
                buttonLabel = "Inserir";
            } else {
                action += "update/" + jogo.getId();
                name = "Atualizar Jogo (Código " + jogo.getId() + ")";
                nome = jogo.getNome();
                genero = jogo.getGenero();
                plataforma = jogo.getPlataforma();
                buttonLabel = "Atualizar";
            } // end if
            umJogo += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td colspan=\"4\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td colspan=\"4\" align=\"left\">&nbsp;</td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\"" + nome + "\"></td>";
            umJogo += "\t\t\t<td>Gênero: <input class=\"input--register\" type=\"text\" name=\"genero\" value=\"" + genero + "\"></td>";
            umJogo += "\t\t\t<td>Plataforma: <input class=\"input--register\" type=\"text\" name=\"plataforma\" value=\"" + plataforma + "\"></td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t</table>";
            umJogo += "\t</form>";
        } else if (tipo == FORM_DETAIL) {
            umJogo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td colspan=\"4\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhes do Jogo (Código " + jogo.getId() + ")</b></font></td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td colspan=\"4\" align=\"left\">&nbsp;</td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td>&nbsp;ID: " + jogo.getId() + "</td>";
            umJogo += "\t\t\t<td>Nome: " + jogo.getNome() + "</td>";
            umJogo += "\t\t\t<td>Gênero: " + jogo.getGenero() + "</td>";
            umJogo += "\t\t\t<td>Plataforma: " + jogo.getPlataforma() + "</td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t\t<tr>";
            umJogo += "\t\t\t<td>&nbsp;</td>";
            umJogo += "\t\t</tr>";
            umJogo += "\t</table>";
        } else {
            System.out.println("ERRO: Tipo não identificado " + tipo);
        } // end if
        form = form.replaceFirst("<UM-JOGO>", umJogo);

        String listaJogos = "<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">";
        listaJogos += "\n<tr><td colspan=\"5\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Jogos</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"5\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_GENERO + "\"><b>Gênero</b></a></td>\n" +
                "\t<td><a href=\"/jogo/list/" + FORM_ORDERBY_PLATAFORMA + "\"><b>Plataforma</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
                "</tr>\n";

        List<Jogo> jogos;
        if (orderBy == FORM_ORDERBY_ID) {
            jogos = jogoDAO.getOrderByid();
        } else if (orderBy == FORM_ORDERBY_GENERO) {
            jogos = jogoDAO.getOrderByGenero();
        } else if (orderBy == FORM_ORDERBY_PLATAFORMA) {
            jogos = jogoDAO.getOrderByPlataforma();
        } else {
            jogos = jogoDAO.get();
        }

        int i = 0;
        String bgcolor = "";
        for (Jogo j : jogos) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            listaJogos += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
                    "\t<td>" + j.getId() + "</td>\n" +
                    "\t<td>" + j.getGenero() + "</td>\n" +
                    "\t<td>" + j.getPlataforma() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/jogo/" + j.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/jogo/update/" + j.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmDeleteJogo('" + j.getId() + "', '" + j.getNome() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        listaJogos += "</table>";

        form = form.replaceFirst("<LISTAR-JOGO>", listaJogos);
    }

    public Object insert(Request request, Response response) {
        String nome = request.queryParams("nome");
        String genero = request.queryParams("genero");
        String plataforma = request.queryParams("plataforma");

        Jogo jogo = new Jogo(1, nome, genero, plataforma);
        String resp;

        if (jogoDAO.insert(jogo)) {
            resp = "Jogo (" + nome + ") inserido!";
            response.status(201); // 201 Created
        } else {
            resp = "Jogo (" + nome + ") não inserido!";
            response.status(404); // 404 Not Found
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object getById(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = jogoDAO.get(id);

        if (jogo != null) {
            response.status(200); // 200 OK
            makeForm(FORM_DETAIL, jogo, FORM_ORDERBY_GENERO);
        } else {
            response.status(404); // 404 Not Found
            String resp = "Jogo " + id + " não encontrado.";
            makeForm();
            form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = jogoDAO.get(id);

        if (jogo != null) {
            response.status(200); // 200 OK
            makeForm(FORM_UPDATE, jogo, FORM_ORDERBY_GENERO);
        } else {
            response.status(404); // 404 Not Found
            String resp = "Jogo " + id + " não encontrado.";
            makeForm();
            form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = jogoDAO.get(id);
        String resp;

        if (jogo != null) {
            jogo.setNome(request.queryParams("nome"));
            jogo.setGenero(request.queryParams("genero"));
            jogo.setPlataforma(request.queryParams("plataforma"));
            jogoDAO.update(jogo);
            response.status(200); // 200 OK
            resp = "Jogo (ID " + jogo.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not Found
            resp = "Jogo (ID " + id + ") não encontrado!";
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Jogo jogo = jogoDAO.get(id);
        String resp;

        if (jogo != null) {
            jogoDAO.delete(id);
            response.status(200); // 200 OK
            resp = "Jogo (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not Found
            resp = "Jogo (" + id + ") não encontrado!";
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}
