package time;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter("/time")
public class TimezoneValidateFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String timezoneParam = httpRequest.getParameter(Config.PARAM_TIMEZONE);

        if (isValidTimezone(timezoneParam)) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid timezone");
        }
    }

    public void destroy() {
    }

    private boolean isValidTimezone(String timezoneParam) {
        return Config.VALID_TIMEZONES.contains(timezoneParam) || timezoneParam == null;
    }
}


