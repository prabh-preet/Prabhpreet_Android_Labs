package algonquin.cst2335.prabhpreetsandroidlabs.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
//import android.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;
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

/*
 * This class is designed to delete a message and get it back if we deleted it by mistake
 * by using Alert dialog box, Executor and Snack bar.
 */
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
        setSupportActionBar(binding.myToolbar);
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);

        /*
         * We made the object to store the messages in a database
         */
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "Messages").build();
        mDAO = db.cmDAO();

        // Get the value of the messages from the model
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            if(messages == null)
            {
                chatModel.messages.setValue(messages = new ArrayList<>());

                /*
                 * Used executor to get all the messages which will be stored in the database
                 */
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

            /*
             * Used executor to insert a message in the database while sending it
             */
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

            /*
             * Used executor to insert a message in the database while receiving it
             */
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


            /*
             * Added the listener for the working of the view when we click on the messages
             */
            /* itemView.setOnClickListener( clk -> {


                        Snackbar.make(messageText, "You deleted message #" + position, Snackbar.LENGTH_LONG)
                                .setAction("Undo", clc -> {
                                    messages.add(position, removedMessage);
                                    myAdapter.notifyItemInserted(position);
                                })
                                .show();
                    }))
                    .create().show();
            });*/

            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );

        if(item.getItemId() == R.id.item_1) {

            int position = messages.size() - 1;
            ChatMessage m = messages.get(position);

            builder.setMessage("Do you want to delete the message: " + m.getMessage())
                    .setTitle("Question: ")
                    .setNegativeButton("No", ((dialog, cl) -> {
                    }))
                    .setPositiveButton("Yes", ((dialog, cl) -> {

                        Executor thread = Executors.newSingleThreadExecutor();
                        thread.execute(() ->
                        {
                            mDAO.deleteMessage(m);
                        });

                        messages.remove(position);
                        myAdapter.notifyItemRemoved(position);
                    }));
            builder.create().show();

        }
        else if (item.getItemId() == R.id.item_2) {

            Toast toast = Toast.makeText(ChatRoom.this, "Version 1.0, created by Prabhpreet Kaur", Toast.LENGTH_LONG);
            View v = toast.getView();
            v.getBackground().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
            TextView text = v.findViewById(android.R.id.message);
            text.setTextColor(Color.WHITE);
            toast.show();
        }
        return true;
    }
}