package net.jankenpoi.sudokuki.ui.swing;

import java.util.Locale;

import net.jankenpoi.i18n.LocaleListener;
import net.jankenpoi.sudokuki.ui.L10nComponent;
import static net.jankenpoi.i18n.I18n._;

class LocaleListenerImpl implements LocaleListener {

	final private L10nComponent l10nComp;

	LocaleListenerImpl(L10nComponent menu) {
		l10nComp = menu;
	}
	
	@Override
	public void onLocaleChanged(Locale locale) {
		String languageCode = _("DETECTED_LANGUAGE");
		l10nComp.setL10nMessages(locale, languageCode);
	}
	
}
