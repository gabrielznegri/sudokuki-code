package net.jankenpoi.i18n;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * HOW TO USE THIS CLASS: import static
 * net.jankenpoi.i18n.I18n._;
 * 
 */
public class I18n {

	private static String PROGRAM_NAME = "autotoolsexample";

	private static ResourceBundle catalog;

	private static Locale currentLocale;

	private static Object lock = new Object();

	private final static ArrayList<LocaleListener> listeners = new ArrayList<LocaleListener>();

	static {
		reset("");
	}

	public static void reset(String localeString) {
		synchronized (lock) {
			try {
				if (localeString != "")
					currentLocale = new Locale(localeString);
				if (currentLocale == null)
					currentLocale = new Locale(System.getenv("LANG"));
				ResourceBundle rb = ResourceBundle.getBundle(I18n.class
						.getName().replace("I18n", PROGRAM_NAME), currentLocale);
				catalog = rb;
				for (int i = 0; i < listeners.size(); i++) {
					listeners.get(i).onLocaleChanged(currentLocale);
				}
			} catch (Exception e1) {
				try {
					if (catalog == null) {
						System.out
								.println("I18n unable to find translations for (LANG) locale "
										+ System.getenv("LANG"));
						catalog = ResourceBundle.getBundle(I18n.class.getName()
								.replace("I18n", PROGRAM_NAME), Locale
								.getDefault());
						currentLocale = Locale.getDefault();
					}
				} catch (Exception e2) {
					System.out
							.println("I18n unable to find translations for (JVM) locale "
									+ Locale.getDefault());
					catalog = null;
				}
			}
			System.out.println("I18n using locale: " + currentLocale);
		}
	}

	public static void addLocaleListener(LocaleListener listener) {
		synchronized (listeners) {
			if (listener != null) {
				listeners.add(listener);
			}
		}
	}

	public static void removeLocaleListener(LocaleListener listener) {
		synchronized (listeners) {
			if (listener != null) {
				listeners.remove(listener);
			}
		}
	}

	public static String _(String msgid) {
		synchronized (lock) {
			if (catalog != null) {
				return gnu.gettext.GettextResource.gettext(catalog, msgid);
			} else {
				return msgid;
			}
		}
	}
}
