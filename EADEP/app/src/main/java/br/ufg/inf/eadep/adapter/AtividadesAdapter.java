package br.ufg.inf.eadep.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.activities.AtividadesActivity;
import br.ufg.inf.eadep.activities.DetalhesAtividadeActivity;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Atividade;

public class AtividadesAdapter extends BaseAdapter{

    private List<Atividade> atividades;
    private Context ctx;
    private LayoutInflater inflater;

    public AtividadesAdapter(Context ctx, List<Atividade> atividades){
        this.ctx = ctx;
        this.atividades = atividades;
    }

    @Override
    public int getCount() {
        return atividades.size();
    }

    @Override
    public Object getItem(int position) {
        return atividades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View container = convertView;
        if(convertView == null){
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            container = inflater.inflate(R.layout.atividade_item, null);
        }

        TextView title = container.findViewById(R.id.activity_item_title);
        TextView description = container.findViewById(R.id.activity_item_description);

        final String activityTitle = atividades.get(position).getTitulo();

        System.out.println("A ATIVIDADE È A SEGUINTE: "+activityTitle);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ctx, DetalhesAtividadeActivity.class);

                Preferences sharedPref = new Preferences(ctx);
                sharedPref.saveActivityTitle(activityTitle);

                System.out.println("A ATIVIDADE SALVA È A SEGUINTE: " + activityTitle);
                ctx.startActivity(it);
            }
        });

        title.setText(atividades.get(position).getTitulo());
        description.setText(atividades.get(position).getDescricao().substring(0, 75) + "...");
        return container;
    }
}
