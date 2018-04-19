package andre.pt.passwordgenerator.Adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private final List<Option> optionsList;

    public OptionAdapter(List<Option> optionsList) {
        this.optionsList = optionsList;
    }

    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Option option = optionsList.get(position);

        holder.icon.setImageDrawable(option.getIcon());
        holder.title.setText(option.getTitle());
        holder.stateSwitch.setChecked(option.isActive());
    }

    @Override
    public int getItemCount() {
        return optionsList.size();
    }

    static class OptionViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.root) ConstraintLayout root;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.state) SwitchCompat stateSwitch;

        public OptionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}
