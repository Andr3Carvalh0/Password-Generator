package andre.pt.passwordgenerator.Data;

public class Option {
    private final String id;
    private final int icon;
    private final int title;
    private boolean active;

    public Option(String id, int icon, int title, boolean active) {
        this.id = id;
        this.icon = icon;
        this.title = title;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public int getIcon() {
        return icon;
    }

    public int getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
