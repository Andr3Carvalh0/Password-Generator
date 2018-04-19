package andre.pt.passwordgenerator.Modal;

import andre.pt.passwordgenerator.Modal.Interfaces.IPasswordGenerator;

import static andre.pt.passwordgenerator.Constants.DEFAULT_LENGHT;
import static andre.pt.passwordgenerator.Constants.LOWER_CASE_LETTERS;
import static andre.pt.passwordgenerator.Constants.NUMBERS_LETTERS;
import static andre.pt.passwordgenerator.Constants.UPPER_CASE_LETTERS;

public class PasswordGenerator implements IPasswordGenerator {

    private boolean useUppercase;
    private boolean useLowercase;
    private boolean useNumbers;
    private int length;

    public PasswordGenerator() {
        this(true, true, true);
    }

    public PasswordGenerator(boolean useUppercase, boolean useLowercase, boolean useNumbers) {
        this.useUppercase = useUppercase;
        this.useLowercase = useLowercase;
        this.useNumbers = useNumbers;
    }

    @Override
    public String generatePassword() {
        String keys = prepareStringKeys();
        StringBuilder result = new StringBuilder();

        for(int i = 1; i <= length; i++) {
            long pos = Math.round(Math.random() * (keys.length() - 1));
            result.append(keys.charAt((int) pos));
        }

        return result.toString();
    }

    private String prepareStringKeys(){
        String keys = "";

        if(useLowercase)
            keys += LOWER_CASE_LETTERS;

        if(useUppercase)
            keys += UPPER_CASE_LETTERS;

        if(useNumbers)
            keys += NUMBERS_LETTERS;

        return keys;
    }

    @Override
    public void addUpperCase() {
        useUppercase = true;
    }

    @Override
    public void removeUpperCase() {
        useUppercase = false;
    }

    @Override
    public void addLowerCase() {
        useLowercase = true;
    }

    @Override
    public void removeLowerCase() {
        useLowercase = false;
    }

    @Override
    public void addNumbers() {
        useNumbers = true;
    }

    @Override
    public void removeNumbers() {
        useNumbers = false;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void setDefaultLength() {
        this.length = DEFAULT_LENGHT;
    }

}
