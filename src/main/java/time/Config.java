package time;

import java.util.Arrays;
import java.util.List;

public class Config {

    public static final String PARAM_TIMEZONE = "timezone";
    public static final String DEFAULT_TIMEZONE = "UTC";
    protected static final List<String> VALID_TIMEZONES = Arrays.asList(
            "UTC", "UTC 1", "UTC 2", "UTC 3", "UTC 4", "UTC 5", "UTC 6", "UTC 7", "UTC 8", "UTC 9", "UTC 10",
            "UTC 11", "UTC 12", "UTC 13", "UTC-1", "UTC-2", "UTC-3", "UTC-4", "UTC-5", "UTC-6", "UTC-7", "UTC-8",
            "UTC-9", "UTC-10", "UTC-11", "UTC-12"
    );
    protected static final String TEMPLATE_PATH = "C:\\Program Files\\Apache Software Foundation\\webapps\\Homework_12.2\\WEB-INF\\classes\\templates\\";

    private Config() {
    }
}
