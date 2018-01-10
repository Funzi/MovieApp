package uco_396575.movio2.pv256.fi.muni.cz.movio.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uco_396575.movio2.pv256.fi.muni.cz.movio.R;
import uco_396575.movio2.pv256.fi.muni.cz.movio.api.MovieClientOkHttpImpl;
import uco_396575.movio2.pv256.fi.muni.cz.movio.model.Cast;


public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<Cast> cast;

    public CastAdapter(List<Cast> cast) {
        this.cast = cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast.clear();
        this.cast.addAll(cast);
        this.cast = new ArrayList<>(cast);
        this.notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.item_cast, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(cast.get(position).getName());
        String address = new MovieClientOkHttpImpl().getPicture(cast.get(position).getProfilePic());
        Picasso.with(holder.pic.getContext())
                .load(address)
                .noPlaceholder()
                .into(holder.pic)
        ;
    }

    @Override
    public int getItemCount() {
        return cast.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cast_name)
        TextView name;
        @BindView(R.id.cast_pic)
        ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
