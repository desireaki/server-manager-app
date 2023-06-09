package io.getarrays.server.enumeration;

public enum Status {
    SERVER_UP("SERVER UP"),
    SERVER_DOWN("SERVER DOWN");
    private final String  status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

}
