package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String queryZone = req.getParameter("timezone");
        TimeZone tz;
        if (queryZone == null) {
            tz = TimeZone.getTimeZone("UTC");
        } else {
            String fixedTimezone = queryZone.replace("UTC", "GMT").replace(' ', '+');
            tz = TimeZone.getTimeZone(fixedTimezone);
        }

        ZonedDateTime nowZoned = ZonedDateTime.now(tz.toZoneId());
        DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss z");
        String dateTime = nowZoned.format(dtFormatter);

        res.setContentType("text/html; charset=utf-8");
        String html =
                "<div style=\"width: 100vw; height: 100vh; display: flex; justify-content: center;align-items: center;\">" +
                        "<div style=\"border: 2px solid red; border-radius: 5px; padding: 20px; text-align: center;\">" +
                        "<h2 style=\"color: blue; margin-bottom: 30px;\" >Current date/time for chosen zone is:</h2>" +
                        "<h1 style=\"color: green;\">" + dateTime + "</h1></div></div>";
        res.getWriter().write(html);
        res.getWriter().close();
    }
}
