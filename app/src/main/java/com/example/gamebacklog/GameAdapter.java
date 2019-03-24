package com.example.gamebacklog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {
    private List<Game> games = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_item, parent, false);
        return new GameHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GameHolder holder, int position) {
        Game currentGame = games.get(position);
        holder.textViewTitle.setText(currentGame.getTitle());
        holder.textViewPlatform.setText(currentGame.getPlatform());
        holder.textViewStatus.setText(currentGame.getStatus());
        holder.textViewDate.setText(currentGame.getDate());

    }

    public void setGames(List<Game> games) {
        this.games = games;
        notifyDataSetChanged();
    }

    public Game getGameAt(int position) {
        return games.get(position);
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    class GameHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewPlatform;
        private TextView textViewStatus;
        private TextView textViewDate;

        public GameHolder(@NonNull final View itemView) {
            super(itemView);

            textViewPlatform = itemView.findViewById(R.id.text_view_platform);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewStatus = itemView.findViewById(R.id.text_view_status);
            textViewDate = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(games.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Game game);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
