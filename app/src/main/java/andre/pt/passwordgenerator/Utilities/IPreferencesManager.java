package andre.pt.passwordgenerator.Utilities;

import andre.pt.passwordgenerator.Data.Option;

public interface IPreferencesManager {
    boolean pullValue(String key);
    void pushValues(Option... options);

}
