package iview.wsienski.androidbattliefield.adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import iview.wsienski.androidbattliefield.R;

/**
 * Created by Witold Sienski on 10.07.2016.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private String[] names;
    private int[] imageIds;

    public CardAdapter(String[] names, int[] imageIds) {
        this.names = names;
        this.imageIds = imageIds;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card, parent, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        holder.getName().setText(names[position]);
        Drawable drawable = cardView.getResources().getDrawable(imageIds[position]);
        holder.getImageView().setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.info_text)
        TextView name;
        @BindView(R.id.imageView)
        ImageView imageView;

        private CardView cardView;

        public ViewHolder(CardView itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            cardView = itemView;
        }

        public TextView getName() {
            return name;
        }

        public void setName(TextView name) {
            this.name = name;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setImageView(ImageView imageView) {
            this.imageView = imageView;
        }
    }
}
