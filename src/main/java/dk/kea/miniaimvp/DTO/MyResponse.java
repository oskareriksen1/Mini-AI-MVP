package dk.kea.miniaimvp.DTO;

public class MyResponse {

    private String content;

    // Constructor
    public MyResponse(String content) {
        this.content = content;
    }

    // Getter
    public String getContent() {
        return content;
    }

    // Setter
    public void setContent(String content) {
        this.content = content;
    }
}

