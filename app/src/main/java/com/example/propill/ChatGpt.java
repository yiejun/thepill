package com.example.propill;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONObject;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatGpt extends AppCompatActivity {
/*
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/engines/davinci-codex/completions";
*/
    RecyclerView recyclerView;
    TextView welcomeTextView;
    EditText messageEditText;
    ImageButton sendButton;
    List<Message> messageList;
    MessageAdapter messageAdapter;
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gpt2);
        messageList = new ArrayList<>();


        recyclerView = findViewById(R.id.recycler_view);
        welcomeTextView = findViewById(R.id.welcome_text);
        messageEditText = findViewById(R.id.message_edit_text);
        sendButton = findViewById(R.id.send_btn);

        messageAdapter = new MessageAdapter(messageList);
        recyclerView.setAdapter(messageAdapter);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);


        sendButton.setOnClickListener((v) -> {
            String question = messageEditText.getText().toString().trim();
            addToChat(question,Message.SENT_BY_ME);
            messageEditText.setText("");
            callAPI(question);
            welcomeTextView.setVisibility(View.GONE);

        });
        String text = getIntent().getStringExtra("text");
        messageEditText.setText(text);


    }
    void addToChat(String message,String sentBy){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messageList.add(new Message(message,sentBy));
                messageAdapter.notifyDataSetChanged();
                recyclerView.smoothScrollToPosition(messageAdapter.getItemCount());
            }
        });
    }
    void addResponse(String response){
        messageList.remove(messageList.size()-1);
        addToChat(response,Message.SENT_BY_BOT);
    }

    void callAPI(String question){
        messageList.add(new Message("Typing... ",Message.SENT_BY_BOT));

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model","text-davinci-003");
            jsonBody.put("prompt",question);
            jsonBody.put("max_tokens",4000);
            jsonBody.put("temperature",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .header("Authorization","Bearer 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")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    JSONObject  jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("choices");
                        String result = jsonArray.getJSONObject(0).getString("text");
                        addResponse(result.trim());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else{
                    addResponse("Failed to load response due to "+response.body().toString());
                }
            }
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                addResponse("Failed to load response due to "+e.getMessage());
            }
        });
    }



    }


/*    public class CompletionRequest {

       @SerializedName("prompt")
       private String prompt;

       @SerializedName("max_tokens")
       private int maxTokens;

       @SerializedName("model")
       private String model;

       public CompletionRequest(String prompt, int maxTokens, String model) {
           this.prompt = prompt;
           this.maxTokens = maxTokens;
           this.model = model;
       }
   }

   public class CompletionResponse {

       @SerializedName("choices")
       private List<Choice> choices;

       public List<Choice> getChoices() {
           return choices;
       }

       public class Choice {
           @SerializedName("text")
           private String text;

           public String getText() {
               return text;
           }
       }
   }

   public interface OpenAIInterface {

       @Headers({"Content-Type: application/json", "Authorization: Bearer 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"})
       @POST("completions")
       Call<CompletionResponse> getCompletion(@Body CompletionRequest request);
   }

   // Find the EditText and Button views by ID
   EditText editText = findViewById(R.id.prompt_edit_text);
   Button button = findViewById(R.id.generate_button);

   // Set an onClickListener for the generateButton to generate text when clicked
       generateButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view){
       String prompt = promptEditText.getText().toString();
       generateText(prompt);
   }
   });

   // Define the generateText() method to generate text based on a given prompt
   private void generateText(String prompt) {
       // Create a new instance of the OpenAIInterface interface
       OpenAIInterface openAIInterface = ApiClient.getClient().create(OpenAIInterface.class);

       // Create a new instance of the CompletionRequest class with the given prompt, max tokens, and model
       CompletionRequest completionRequest = new CompletionRequest(prompt, 50, "davinci");

       // Call the getCompletion() method on the openAIInterface object and enqueue the request
       Call<CompletionResponse> call = openAIInterface.getCompletion(completionRequest);
       call.enqueue(new Callback<CompletionResponse>() {
           @Override
           public void onResponse(Call<CompletionResponse> call, Response<CompletionResponse> response) {
               if (response.isSuccessful()) {
                   // Get the generated text from the response and set it as the text of the TextView
                   List<CompletionResponse.Choice> choices = response.body().getChoices();
                   String generatedText = choices.get(0).getText();
                   TextView generatedTextView = findViewById(R.id.generate_button);
                   generatedTextView.setText(generatedText);
               } else {
                   // Display a toast message if the API request is unsuccessful
                   Toast.makeText(ChatGpt.this, "API request unsuccessful", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<CompletionResponse> call, Throwable t) {
               // Display a toast message if the API request fails
               Toast.makeText(ChatGpt.this, "API request failed", Toast.LENGTH_SHORT).show();
           }
       });
   }

   public class ApiClient {

       private static final String BASE_URL = "https://api.openai.com/v1/";

       private Retrofit retrofit = null;

       public Retrofit getClient() {
           if (retrofit == null) {
               retrofit = new Retrofit.Builder()
                       .baseUrl(BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();
           }
           return retrofit;
       }
   }*/

/*    public interface OpenAIInterface {
       @Headers("Content-Type: application/json")
       @POST("completions")
       Call<CompletionResponse> generateText(@Body CompletionRequest request);
   }

   Retrofit retrofit = new Retrofit.Builder()
           .baseUrl("https://api.openai.com/v1/")
           .addConverterFactory(GsonConverterFactory.create())
           .build();
   OpenAIInterface openAIInterface = retrofit.create(OpenAIInterface.class);

   public class CompletionRequest {
       @SerializedName("prompt")
       @Expose
       private String prompt;

       @SerializedName("max_tokens")
       @Expose
       private int maxTokens;

       @SerializedName("model")
       @Expose
       private String model;

       public CompletionRequest(String prompt, int maxTokens, String model) {
           this.prompt = prompt;
           this.maxTokens = maxTokens;
           this.model = model;
       }
   }
   public class CompletionResponse {
       @SerializedName("choices")
       @Expose
       private List<Choice> choices;

       public List<Choice> getChoices() {
           return choices;
       }

       public void setChoices(List<Choice> choices) {
           this.choices = choices;
       }

       public class Choice {
           @SerializedName("text")
           @Expose
           private String text;

           @SerializedName("index")
           @Expose
           private int index;

           public String getText() {
               return text;
           }

           public void setText(String text) {
               this.text = text;
           }

           public int getIndex() {
               return index;
           }

           public void setIndex(int index) {
               this.index = index;
           }
       }
   }

   EditText promptEditText = findViewById(R.id.prompt_edit_text);
   Button generateButton = findViewById(R.id.generate_button);

// Set an onClickListener for the generateButton to generate text when clicked
generateButton.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           String prompt = promptEditText.getText().toString();
           generateText(prompt);
       }
   });

   public void generateText(String prompt) {
       OpenAIInterface apiInterface = ApiClient.getClient().create(OpenAIInterface.class);
       CompletionRequest request = new CompletionRequest(prompt, 100, "davinci");
       Call<CompletionResponse> call = openAIInterface.generateText(request);
       call.enqueue(new Callback<CompletionResponse>() {
           @Override
           public void onResponse(Call<CompletionResponse> call, Response<CompletionResponse> response) {
               if (response.isSuccessful()) {
                   CompletionResponse completionResponse = response.body();
                   String generatedText = completionResponse.getChoices().get(0).getText();
                   // Do something with the generated text, such as displaying it in a TextView
               } else {
                   // Handle API error
               }
           }

           @Override
           public void onFailure(Call<CompletionResponse> call, Throwable t) {
               // Handle API failure
           }
       });
   }
   public class ApiClient {

       private static final String BASE_URL = "https://api.openai.com/v1/";

       private static Retrofit retrofit = null;

       public static Retrofit getClient() {
           if (retrofit == null) {
               retrofit = new Retrofit.Builder()
                       .baseUrl(BASE_URL)
                       .addConverterFactory(GsonConverterFactory.create())
                       .build();
           }
           return retrofit;
       }
   }*/

