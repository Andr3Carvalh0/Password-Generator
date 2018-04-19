package andre.pt.passwordgenerator.Modal.Interfaces;

public interface IPasswordGenerator {

    void addUpperCase();
    void removeUpperCase();

    void addLowerCase();
    void removeLowerCase();

    void addNumbers();
    void removeNumbers();

    void setLength(int length);
    void setDefaultLength();

    String generatePassword();

    boolean canGeneratePassword();
}
