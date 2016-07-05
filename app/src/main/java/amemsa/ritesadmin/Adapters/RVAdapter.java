package amemsa.ritesadmin.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

import amemsa.ritesadmin.R;

/**
 * Created by ahmed on 3/12/2016.
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {


    private Context context;

    public RVAdapter() {
        items = new ArrayList<>();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView fcv;
        TextView name;


        PersonViewHolder(View itemView) {
            super(itemView);
            fcv = (CardView) itemView.findViewById(R.id.friends_card_view);
            name = (TextView) itemView.findViewById(R.id.friends_name_tv);
        }


    }

    ArrayList<Requests> items;

    public RVAdapter(ArrayList<Requests> items) {
        this.items = items;
    }

    public ArrayList<Requests> getItems() {
        return items;
    }

    public void setItems(ArrayList<Requests> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }


    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.rv_item, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.name.setText(items.get(position).getId()+"");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
        this.notifyDataSetChanged();
    }

    public Requests getItem(int position) {
        return items.get(position);
    }


}