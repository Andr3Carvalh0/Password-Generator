package andre.pt.passwordgenerator.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private final List<Option> optionsList;
    private final Context context;
    private final CompoundButton.OnCheckedChangeListener onClick;

    public OptionAdapter(List<Option> optionsList, Context context, CompoundButton.OnCheckedChangeListener onClick) {
        this.optionsList = optionsList;
        this.context = context;
        this.onClick = onClick;
    }

    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        return new OptionViewHolder(view, onClick);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, int position) {
        Option option = optionsList.get(position);

        holder.icon.setImageDrawable(context.getDrawable(option.getIcon()));
        holder.title.setText(context.getString(option.getTitle()));
        holder.stateSwitch.setChecked(option.isActive());
        holder.stateSwitch.setTag(option.getId());
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

        public OptionViewHolder(View itemView, CompoundButton.OnCheckedChangeListener onClick) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            stateSwitch.setOnCheckedChangeListener(onClick);
        }
    }

}
