package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/")
public class HomePageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        res.setContentType("text/html; charset=utf-8");
        String html = "<div style=\"display: inline-flex; justify-content: center; align-items: center;" +
                "border: 2px solid green; border-radius: 5px; padding: 20px\">" +
                "<h3 style=\"color: blue;\">Use our api at \"/time\" endpoint</h3></div>";
        res.getWriter().write(html);
        res.getWriter().close();
    }
}
