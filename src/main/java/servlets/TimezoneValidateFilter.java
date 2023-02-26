package servlets;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String queryZone = req.getParameter("timezone");

        if (queryZone == null || !TimeZone
                .getTimeZone(queryZone.replace("UTC", "GMT").replace(' ', '+'))
                .getID().equals("GMT")) {
            chain.doFilter(req, res);
        } else {
            try {
                ZoneId.of(queryZone);
                chain.doFilter(req, res);
            } catch (DateTimeException ex) {
                System.out.println(ex.getMessage());

                res.setStatus(400);
                res.setContentType("text/html; charset=utf-8");
                String html =
                        "<div style=\"width: 100vw; height: 100vh; display: flex; justify-content: center;align-items: center\">" +
                                "<div style=\"border: 2px solid red; border-radius: 5px; padding: 20px; text-align: center\">" +
                                "<h2 style=\"color: red\">Invalid timezone</h2>" +
                                "<p>" + ex.getMessage() + "</p></h2></div></div>";
                res.getWriter().write(html);
                res.getWriter().close();
            }
        }
    }
}
