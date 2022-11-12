package ca.cmpt276.iteration1.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ca.cmpt276.iteration1.R;

/*
 * Code inspired by blog post on 29 Oct, 2022 from https://thumbb13555.pixnet.net/blog/post/311803031-%E7%A2%BC%E8%BE%B2%E6%97%A5%E5%B8%B8-%E3%80%8Eandroid-studio%E3%80%8F%E5%9F%BA%E6%9C%ACrecyclerview%E7%94%A8%E6%B3%95
 * Citation: https://www.youtube.com/watch?v=7GPUpvcU1FE
 * */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;

    GameManager gameManager;
    GameType gameType;
    ArrayList<PlayedGame> playedGames;
    Context context;

    public RecyclerViewAdapter(Context context, RecyclerViewInterface recyclerViewInterface, String gameTypeString){
        this.gameManager = GameManager.getInstance();
        this.gameType = gameManager.getGameTypeFromString(gameTypeString);
        this.playedGames = gameManager.getSpecificPlayedGames(gameTypeString);
        this.context = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dScore, dNoOfPlayer, dAchievement;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            dScore = itemView.findViewById(R.id.tvDisplayScore);
            dNoOfPlayer = itemView.findViewById(R.id.tvDisplayNoOfPlayer);
            dAchievement = itemView.findViewById(R.id.tvDisplayAchievement);

            itemView.setOnLongClickListener(view -> {
                Log.i("Tag", "something has been long clicked");
                if (recyclerViewInterface != null){
                    int position = getAbsoluteAdapterPosition();

                    if (position != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onItemLongClick(position);
                        return true;
                    }
                }
                return true;
            });
        }
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_history_list_layout, parent, false);
        return new RecyclerViewAdapter.ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.dScore.setText(String.valueOf(playedGames.get(position).getScore()));
        holder.dNoOfPlayer.setText(String.valueOf(playedGames.get(position).getNumberOfPlayers()));
        holder.dAchievement.setText(playedGames.get(position).getAchievement());
    }

    @Override
    public int getItemCount() {
        return playedGames.size();
    }

}