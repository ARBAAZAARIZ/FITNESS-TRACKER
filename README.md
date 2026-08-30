# FITNESS-TRACKER
This Application Tracks your activity and give awesome recommendation. 

----------------------------------------------
To start the rabit mq run this command but u must have docker on your sysytem --   
    [docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management]


------------------------------------------------
eureka server is running on localhost:8761


-----------------------------------
gemini api request body 

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeminiRequestDTO {

    private List<Content> contents;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
        private String text;
    }
}
String prompt = "Give me a fitness recommendation";

GeminiRequestDTO.Part part =
new GeminiRequestDTO.Part(prompt);

GeminiRequestDTO.Content content =
new GeminiRequestDTO.Content(List.of(part));

GeminiRequestDTO request =
new GeminiRequestDTO(List.of(content));
will become
{
"contents": [
{
"parts": [
{
"text": "Give me a fitness recommendation"
}
]
}
]
}
------------------------------------------

gemini api response body 

package com.fitness.aiService.dto.gemini;

import lombok.Data;

import java.util.List;

@Data
public class GeminiResponseDTO {

    private List<Candidate> candidates;

    @Data
    public static class Candidate {
        private Content content;
    }

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}

{
"candidates": [
{
"content": {
"parts": [
{
"text": "Your recommendation..."
}
]
}
}
]
}


------------------------------------------------------
response of AI 

{
"analysis": {
"overall": "The activity demonstrates a high-intensity cardiovascular effort, although the reported calorie burn and heart rate metrics suggest a potential discrepancy in data tracking or physiological strain.",
"pace": "Your average speed of 3.2 mph is a moderate, consistent walking pace, which is standard for a 30-minute brisk walk.",
"heartRate": "A maximum heart rate of 190 bpm is exceptionally high for a walking activity. This may indicate an inaccurate sensor reading or suggest that you are pushing your cardiovascular system into a near-maximal zone, which is atypical for standard walking.",
"caloriesBurned": "500 calories in 30 minutes of walking is statistically improbable for most individuals. This figure is significantly higher than the average burn rate for this activity, suggesting a potential calibration error with your tracking device."
},
"improvements": [
{
"area": "Heart Rate Monitoring",
"recommendation": "Verify the accuracy of your heart rate monitor. If using a wrist-based device, ensure it is snug and positioned correctly to prevent cadence lock, which often causes falsely high heart rate readings."
},
{
"area": "Calorie Tracking Calibration",
"recommendation": "Check the personal settings on your fitness app or device. Ensure your age, weight, and height are input correctly, as these metrics are vital for calculating realistic caloric expenditure."
}
],
"suggestions": [
{
"workout": "Interval Power Walking",
"description": "Incorporate 1-minute bursts of 'power walking' where you pump your arms and increase your speed, followed by 2 minutes of recovery pace. This will improve cardiovascular endurance without requiring excessive strain."
},
{
"workout": "Incline Walking",
"description": "If you have access to a treadmill or hilly terrain, introduce a 2-3% incline. This increases the intensity and muscle engagement of the workout while maintaining a safe, consistent pace."
}
],
"safety": [
"Consult with a healthcare professional regarding your 190 bpm max heart rate, as this is unusually high for a walking workout.",
"Prioritize a 5-minute warm-up and cool-down to allow your heart rate to transition gradually rather than spiking abruptly.",
"Listen to your body for signs of dizziness, chest pain, or extreme fatigue, and stop the activity immediately if these occur."
]
}