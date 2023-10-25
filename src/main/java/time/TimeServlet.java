package time;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        super.init();
        FileTemplateResolver templateResolver = new FileTemplateResolver();
        templateResolver.setPrefix(Config.TEMPLATE_PATH);
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        String timezone = request.getParameter(Config.PARAM_TIMEZONE);

        if (timezone == null || timezone.isEmpty()) {
            timezone = getLastTimezoneFromCookie(request);
            if (timezone == null) {
                timezone = Config.DEFAULT_TIMEZONE;
            }
        } else {
            saveLastTimezoneInCookie(response, timezone);
        }

        LocalDateTime currentTime = LocalDateTime.now(ZoneId.of(timezone));
        ZonedDateTime currentZonedDateTime = ZonedDateTime.of(currentTime, ZoneId.of(timezone));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedTime = currentZonedDateTime.format(formatter);

        Context ctx = new Context(request.getLocale());
        ctx.setVariable("formattedTime", formattedTime);
        ctx.setVariable("timezone", timezone);

        templateEngine.process("time", ctx, response.getWriter());
    }

    private String getLastTimezoneFromCookie(HttpServletRequest request) {
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                if (cookie.getName().equals("lastTimezone")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void saveLastTimezoneInCookie(HttpServletResponse response, String timezone) {
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("lastTimezone", timezone);
        cookie.setMaxAge(86400);
        response.addCookie(cookie);
    }
}

