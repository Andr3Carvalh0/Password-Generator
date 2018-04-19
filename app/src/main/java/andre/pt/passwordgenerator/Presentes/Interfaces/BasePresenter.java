package andre.pt.passwordgenerator.Presentes.Interfaces;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;

import java.util.HashMap;
import java.util.List;

import andre.pt.passwordgenerator.Data.Option;

public abstract class BasePresenter {

    protected List<Option> optionList;
    protected HashMap<String, Consumer<Boolean>> onClickRouter;

    protected Option getOptionWithID(String id){
        return Stream.of(optionList)
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .get();
    }

    protected boolean handleClick(String id, boolean active) {
        if(id == null || !onClickRouter.containsKey(id))
            return false;

        onClickRouter.get(id).accept(active);
        return true;
    }

}
