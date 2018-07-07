package br.ufg.inf.eadep.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.model.Comentario;

public class ComentariosAdapter extends BaseAdapter {

    private List<Comentario> comentarios;
    private Context ctx;
    private LayoutInflater inflater;

    public ComentariosAdapter(Context ctx, List<Comentario> comentarios){
        this.ctx = ctx;
        this.comentarios = comentarios;
    }

    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
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

            container = inflater.inflate(R.layout.comentario_item, null);
        }

        TextView content = container.findViewById(R.id.comment_content);
        TextView author = container.findViewById(R.id.comment_author);

        content.setText(comentarios.get(position).getConteudo());
        author.setText(comentarios.get(position).getAutor());

        return container;
    }
}
