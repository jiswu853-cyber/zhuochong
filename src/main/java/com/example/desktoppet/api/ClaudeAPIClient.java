import okhttp3.*;
import java.io.IOException;

public class ClaudeAPIClient {
    private final OkHttpClient client;
    private final String apiKey;

    public ClaudeAPIClient(String apiKey) {
        this.client = new OkHttpClient();
        this.apiKey = apiKey;
    }

    public String sendRequest(String input) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), input);
        Request request = new Request.Builder()
                .url("https://claude.ai/api/v1/sendRequest")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }
}