package com.cloud.homework;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.homework.dao.DatastoreDao;
import com.cloud.homework.model.Transaction;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@RestController
@RequestMapping("/translate")
public class TranslateController {

	private DatastoreDao datastoreDao = new DatastoreDao();

	@RequestMapping(method = RequestMethod.GET)
	public String greetings() {
		return "You entered the translate api. Provide a string in the url";
	}

	@RequestMapping(value = "/{string}", method = RequestMethod.GET)
	public String translate(@PathVariable("string") String inputText) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		// Translates some text into Russian
		Translation translation = translate.translate(inputText, TranslateOption.sourceLanguage("en"),
				TranslateOption.targetLanguage("ro"));

		insert(inputText, translation.getTranslatedText());

		return translation.getTranslatedText();
	}

	@RequestMapping(value = "/audio/{string}", method = RequestMethod.GET)
	public String getAudioTranslation(@PathVariable("string") String string) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		// Translates some text into Russian
		Translation translation = null;
		try {
			translation = translate.translate(SpeechController.syncRecognizeFile(string),
					TranslateOption.sourceLanguage("en"), TranslateOption.targetLanguage("ro"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return translation.getTranslatedText();
	}

	private void insert(String input, String output) {
		Transaction transaction = new Transaction();

		transaction.setRequest(input);
		transaction.setResponse(output);

		long id = datastoreDao.createTransaction(transaction);
		
		System.out.println(id);

	}
}
