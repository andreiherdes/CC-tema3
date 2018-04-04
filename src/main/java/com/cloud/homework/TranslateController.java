package com.cloud.homework;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translate.TranslateOption;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

@RestController
@RequestMapping("/translate")
public class TranslateController {

	@RequestMapping(method = RequestMethod.GET)
	public String translateGreetings() {
		return "You entered the translate api. Provide a string in the url";
	}

	@RequestMapping(value = "/{string}", method = RequestMethod.GET)
	public String getUser(@PathVariable("string") String string) {
		Translate translate = TranslateOptions.getDefaultInstance().getService();

		// Translates some text into Russian
		Translation translation = translate.translate(string, TranslateOption.sourceLanguage("en"),
				TranslateOption.targetLanguage("ru"));
		
		return translation.getTranslatedText();

	}
}
