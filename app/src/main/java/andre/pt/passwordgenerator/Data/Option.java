package andre.pt.passwordgenerator.Data;

import android.graphics.drawable.Drawable;

public class Option {
    private final Drawable icon;
    private final String title;
    private final boolean active;

    public Option(Drawable icon, String title, boolean active) {
        this.icon = icon;
        this.title = title;
        this.active = active;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getTitle() {
        return title;
    }

    public boolean isActive() {
        return active;
    }
}
