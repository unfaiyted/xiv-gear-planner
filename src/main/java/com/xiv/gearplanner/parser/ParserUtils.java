package com.xiv.gearplanner.parser;

import com.xiv.gearplanner.exceptions.UnexpectedHtmlStructureException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserUtils {
	
	public static int extractNumeric(String string) {
		return Integer.parseInt(string.replaceAll("[^\\d.]", ""));
	}
	
	public static void checkElementsSize(Elements elements, int expectedSize, String exceptionMessage ) throws UnexpectedHtmlStructureException {
		if (elements.size() != expectedSize) {
			throw new UnexpectedHtmlStructureException(exceptionMessage + " [actual number of elts: " + elements.size() + "]");
		}
	}

	public static void checkElementClass(Element element, String expectedClass, String exceptionMessage ) throws UnexpectedHtmlStructureException {
		if (!element.hasClass(expectedClass)) {
			throw new UnexpectedHtmlStructureException(exceptionMessage + " [actual number of classes: " + element.classNames() + "]");
		}
	}
}
