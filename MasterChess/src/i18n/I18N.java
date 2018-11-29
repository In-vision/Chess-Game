package i18n;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;

/**
 * I18N utility class..
 *
 */
public final class I18N {
	private static String resourceBundlePath;
	private static List<Locale> supportedLocales = new ArrayList<Locale>();


	/** The current selected Locale. */
	private static ObjectProperty<Locale> locale;

	private static void initializeI18n() {
		locale = new SimpleObjectProperty<>(getDefaultLocale());
		locale.addListener((ov, oldValue, newValue) -> Locale.setDefault(newValue));
	}

	/**
	 * Set the resource bundle path.
	 */
	public static void setResourceBundlePath(final String resBundlePath) {
		resourceBundlePath = resBundlePath;
	}

	/**
	 * Get the supported Locale's.
	 */
	public static List<Locale> getSupportedLocales() {
		return supportedLocales;
	}

	/**
	 * Set the supported Locale's.
	 */
	public static void setSupportedLocales(Locale... locales) {
		supportedLocales = Arrays.asList(locales);
		initializeI18n();
	}

	/**
	 * Get the default Locale. This is the systems default if contained in the
	 * supported Locale's, English otherwise.
	 */
	public static Locale getDefaultLocale() {
		Locale sysDefault = Locale.getDefault();
		return getSupportedLocales().contains(sysDefault) ? sysDefault : Locale.ENGLISH;
	}

	public static Locale getLocale() {
		return locale.get();
	}

	public static void setLocale(Locale locale) {
		localeProperty().set(locale);
//		Locale.setDefault(locale);
	}

	public static ObjectProperty<Locale> localeProperty() {
		return locale;
	}

	/**
	 * Gets the string with the given key from the resource bundle for the
	 * current locale and uses it as first argument to MessageFormat.format,
	 * passing in the optional args and returning the result.
	 */
	public static String getMessage(String key, Object... args) {
		ResourceBundle bundle = ResourceBundle.getBundle(resourceBundlePath, getLocale());
		return MessageFormat.format(bundle.getString(key), args);
	}

	/**
	 * Creates a String binding to a localized String for the given message
	 */
	public static StringBinding createStringBinding(String key, Object... args) {
		return Bindings.createStringBinding(() -> getMessage(key, args), locale);
	}

	/**
	 * Creates a String Binding to a localized String that is computed by
	 * calling the given func
	 */
	public static StringBinding createStringBinding(Callable<String> func) {
		return Bindings.createStringBinding(func, locale);
	}

	/**
	 * Creates a bound Label whose value is computed on language change.
	 */
	public static Label labelForKey(String key, Object... values) {
		return (Label) labeledNodeForKey(Label::new, key, values);
	}

	/**
	 * Creates a bound Button for the given ResourceBundle key
	 */
	public static Button buttonForKey(String key, Object... args) {
		return (Button) labeledNodeForKey(Button::new, key, args);
	}
	
	public static Labeled labeledNodeForKey(Supplier<? extends Labeled> constructor, String key, Object... args) {
		Labeled labeledNode = constructor.get();
		labeledNode.textProperty().bind(createStringBinding(key, args));
		return labeledNode;
	}
}
