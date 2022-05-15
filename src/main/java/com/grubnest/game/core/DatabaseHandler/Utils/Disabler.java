package com.grubnest.game.core.DatabaseHandler.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Disabler instance to auto disable any used instance of sql
 * @author tamilpp25
 * @version 1.0 at 15-5-2022
 */
public class Disabler {
	private static Disabler instance = null;
	private final List<Deactivated> toDisable = new ArrayList<>();

	public static Disabler getInstance() {
		if (instance != null) return instance;
		return instance = new Disabler();
	}

	public void disableAll() {
		for (Deactivated toDeactivated : toDisable)
			toDeactivated.onDisable();
	}

	public void disable(Deactivated sqlinstance) {
		sqlinstance.onDisable();
	}

	public void registerDeactivated(Deactivated pDeactivated) {
		toDisable.add(pDeactivated);
	}

}