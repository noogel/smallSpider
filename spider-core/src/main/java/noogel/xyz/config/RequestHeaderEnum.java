package noogel.xyz.config;

public enum RequestHeaderEnum {
    PC_CHROME("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36"),
    PC_FIREFOX(""),
    PC_IE(""),
    IOS(""),
    ANDROID(""),
    WECHAT("");
    private String ua;

    RequestHeaderEnum(String ua) {
        this.ua = ua;
    }

    public String getUa() {
        return ua;
    }
}
