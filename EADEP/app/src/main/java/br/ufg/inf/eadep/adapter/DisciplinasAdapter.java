package br.ufg.inf.eadep.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.activities.AtividadesActivity;
import br.ufg.inf.eadep.activities.DisciplinasActivity;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Disciplina;

public class DisciplinasAdapter extends BaseAdapter{

    private List<Disciplina> disciplinas;
    private int[] icons;
    private Context ctx;
    private LayoutInflater inflater;

    public DisciplinasAdapter(Context ctx, int[] icons, List<Disciplina> disciplinas){
        this.ctx = ctx;
        this.icons = icons;
        this.disciplinas = disciplinas;
    }

    @Override
    public int getCount() {
        return disciplinas.size();
    }

    @Override
    public Object getItem(int position) {
        return disciplinas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View gridView = convertView;
        System.out.println("EU QUERO UM SOUT AQUI");
        if(convertView == null){
            inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            gridView = inflater.inflate(R.layout.disciplina_item, null);
        }

        ImageView icon = gridView.findViewById(R.id.disciplina_icon);
        TextView title = gridView.findViewById(R.id.disciplina_title);

        final String subjectName = disciplinas.get(position).getNome();

        icon.setImageResource(R.drawable.ic_launcher_background);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ctx, AtividadesActivity.class);
                ctx.startActivity(it);
                Preferences sharedPref = new Preferences(ctx);

                sharedPref.saveSubject(subjectName);
            }
        });

        title.setText(disciplinas.get(position).getNome());
        return gridView;
    }
}
