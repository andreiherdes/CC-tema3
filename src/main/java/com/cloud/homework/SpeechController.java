package com.cloud.homework;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.speech.v1p1beta1.RecognitionAudio;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig;
import com.google.cloud.speech.v1p1beta1.RecognitionConfig.AudioEncoding;
import com.google.cloud.speech.v1p1beta1.RecognizeResponse;
import com.google.cloud.speech.v1p1beta1.SpeechClient;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1p1beta1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

@RestController
@RequestMapping("/speech")
public class SpeechController {

	@RequestMapping(method = RequestMethod.GET)
	public String speech() {
		return "You entered the Speech API.";
	}
	
	public static String syncRecognizeFile(String fileName) throws Exception {
		String respons = "ds";
		try (SpeechClient speech = SpeechClient.create()) {
			
			fileName = "./" + fileName + ".raw";
			System.out.println(fileName);
		    Path path = Paths.get(fileName);
		    byte[] data = Files.readAllBytes(path);
		    ByteString audioBytes = ByteString.copyFrom(data);

		    // Configure request with local raw PCM audio
		    RecognitionConfig config = RecognitionConfig.newBuilder()
		        .setEncoding(AudioEncoding.LINEAR16)
		        .setLanguageCode("en-US")
		        .setSampleRateHertz(16000)
		        .build();
		    RecognitionAudio audio = RecognitionAudio.newBuilder()
		        .setContent(audioBytes)
		        .build();

		    // Use blocking call to get audio transcript
		    RecognizeResponse response = speech.recognize(config, audio);
		    List<SpeechRecognitionResult> results = response.getResultsList();

		    for (SpeechRecognitionResult result : results) {
		      // There can be several alternative transcripts for a given chunk of speech. Just use the
		      // first (most likely) one here.
		      SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
		      respons = alternative.getTranscript();
		      //System.out.printf("Transcription: %s%n", alternative.getTranscript());
		    }
		  }
		return respons;
	}
}
