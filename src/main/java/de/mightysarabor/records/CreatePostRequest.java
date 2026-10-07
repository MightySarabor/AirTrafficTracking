package de.mightysarabor.records;

public record CreatePostRequest(
        String title,
        String body,
        int userId
) {
}
