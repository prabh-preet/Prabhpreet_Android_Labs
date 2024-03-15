package algonquin.cst2335.prabhpreetsandroidlabs.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.prabhpreetsandroidlabs.data.ChatMessage;
import algonquin.cst2335.prabhpreetsandroidlabs.data.ChatMessageDAO;
import algonquin.cst2335.prabhpreetsandroidlabs.data.ChatRoomViewModel;
import algonquin.cst2335.prabhpreetsandroidlabs.R;
import algonquin.cst2335.prabhpreetsandroidlabs.data.MessageDatabase;
import algonquin.cst2335.prabhpreetsandroidlabs.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.prabhpreetsandroidlabs.databinding.ReceivedMessageBinding;
import algonquin.cst2335.prabhpreetsandroidlabs.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    ChatRoomViewModel chatModel;

    ArrayList<ChatMessage> messages = new ArrayList<>();
    RecyclerView.Adapter<MyRowHolder> myAdapter;

    ChatMessageDAO mDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);


        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "Messages").build();
        mDAO = db.cmDAO();

        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            if(messages == null)
            {
                chatModel.messages.setValue(messages = new ArrayList<>());
                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                    runOnUiThread( () -> binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
                });
            }
        }

        binding.sendButton.setOnClickListener(click -> {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage msg = new ChatMessage (binding.textInput.getText().toString(), currentDateandTime, true);
            messages.add(msg);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(msg);
            });

            myAdapter.notifyItemInserted(messages.size()-1);

            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click -> {

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());

            ChatMessage msg = new ChatMessage (binding.textInput.getText().toString(), currentDateandTime, false);
            messages.add(msg);

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                mDAO.insertMessage(msg);
            });

            //  myAdapter.notifyItemInserted(messages.size()-1);
            myAdapter.notifyDataSetChanged();

            binding.textInput.setText("");
        });


        binding.recycleView.setAdapter(myAdapter =new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                if(viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
                else {
                    ReceivedMessageBinding binding = ReceivedMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage obj = messages.get(position);
                holder.messageText.setText(obj.getMessage());
                holder.timeText.setText(obj.getTime());
            }

            @Override
            public int getItemCount() {
                return messages.size();
            }
            public int getItemViewType(int position) {
                ChatMessage object = messages.get(position);
                if(object.getButton() ){
                    return 0;
                }
                else{
                    return 1;
                }
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

            itemView.setOnClickListener( clk -> {
                        int position = getAbsoluteAdapterPosition();
                        builder.setMessage("Do you want to delete the message: " + messageText.getText());
                        builder.setTitle("Question: ");
                        builder.setNegativeButton("No", ((dialog, cl) -> {
                        }));
                        builder.setPositiveButton("Yes", ((dialog, cl) -> {
                            /*ChatMessage m = messages.get(position);
                            mDAO.deleteMessage(m);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);*/
                        }));
                        builder.create().show();
            });
                /*
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                    .setTitle("Question: ")
                    .setNegativeButton("No", ((dialog, cl) -> { }))
                    .setPositiveButton("Yes", ((dialog, cl) -> {
                        ChatMessage m = messages.get(position);
                        mDAO.deleteMessage(m);
                        messages.remove(position);
                        myAdapter.notifyItemRemoved(position);
                    }))
                    .create().show();
            });*/

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }
}